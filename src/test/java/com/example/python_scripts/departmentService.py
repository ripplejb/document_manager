import requests
import json

class Department(object):
    def __init__(self, id, managerId, name, *args, **kwards):
        self.id = id
        self.managerId = managerId
        self.name = name

base = "http://localhost:8080/"
newDepartmentUrl = base + "departments"

class DepartmentService(object):
    def __init__(self):
        super().__init__()
    
    def create(self, name, manager, token):
        newDepartmentHeader = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
            }
        
        payload = {
            "name": "Finance",
            "managerId": manager.id
        }

        response = requests.post(url=newDepartmentUrl, json=payload, headers=newDepartmentHeader)
        return Department(**json.loads(response.content))
