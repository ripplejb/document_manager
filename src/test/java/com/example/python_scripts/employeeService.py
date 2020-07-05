import requests
import json

executiveEmployee1 = {
    'name':'Exec Employee', 
    'designation': 'executive', 
    'username':'executive1', 
    'password':'execpassword'
    }
managerEmployee1 = {
    'name':'Manager Employee 1', 
    'designation': 'manager', 
    'username':'manager1', 
    'password':'manpassword'
    }
managerEmployee2 = {
    'name':'Manager Employee 2', 
    'designation': 'manager', 
    'username':'manager2', 
    'password':'manpassword'
    }

creatorEmployee1 = {
    'name':'Creator Employee 1', 
    'designation': 'creator', 
    'username':'creator1', 
    'password':'creatorpassword'
    }
creatorEmployee2 = {
    'name':'Creator Employee 2', 
    'designation': 'creator', 
    'username':'creator2', 
    'password':'creatorpassword'
    }
creatorEmployee3 = {
    'name':'Creator Employee 3', 
    'designation': 'creator', 
    'username':'creator3', 
    'password':'creatorpassword'
    }
creatorEmployee4 = {
    'name':'Creator Employee 4', 
    'designation': 'creator', 
    'username':'creator4', 
    'password':'creatorpassword'
    }

class Employee(object):
    def __init__(self, id, name, designation, *args, **kwards):
        self.id = id
        self.name = name
        self.designation = designation

base = "http://localhost:8080/"
newEmployeeUrl = base + "employees"
newEmployeeHeader = {'Content-Type': 'application/json'}

class EmployeeService(object):
    def __init__(self):
        super().__init__()
    
    def create(self, employee):
        response = requests.post(newEmployeeUrl, json=employee, headers=newEmployeeHeader)
        if (response.status_code < 300):
            return Employee(**json.loads(response.content))
        else:
            print("Error creating executive")
            return None

    def getEmployees(self):
        dict = {}
        
        empl = self.create(executiveEmployee1)
        if (empl != None):
            dict["exec1"] = empl

        empl = self.create(managerEmployee1)
        if (empl != None):
            dict["manager1"] = empl

        empl = self.create(managerEmployee2)
        if (empl != None):
            dict["manager2"] = empl

        empl = self.create(creatorEmployee1)
        if (empl != None):
            dict["creator1"] = empl
        empl = self.create(creatorEmployee2)
        if (empl != None):
            dict["creator2"] = empl
        empl = self.create(creatorEmployee3)
        if (empl != None):
            dict["creator3"] = empl
        empl = self.create(creatorEmployee4)
        if (empl != None):
            dict["creator4"] = empl
        return dict
