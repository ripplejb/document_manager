from signInService import SignInService
import requests
import json
import urllib

class Document(object):
    def __init__(self, id, creatorId, departmentId, title, content, *args, **kwards):
        self.id = id
        self.creatorId = creatorId
        self.departmentId = departmentId
        self.title = title
        self.content = content

base = "http://localhost:8080/"
documentUrl = base + "documents"

class DocumentService(object):
    def getDocumentObject(self, employee, department, documentNum):
        return {
            "creatorId": employee.id,
            "departmentId": department.id,
            "title": "Title " + str(documentNum),
            "content": "Content " + str(documentNum),
        }

    def signInAndCreateDocument(self, document, username, password):
        creatorSignInResponse = SignInService(username, password).signIn()
        header = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + creatorSignInResponse.access_token
            }
        response = requests.post(documentUrl, json=document, headers=header)
        return Document(**json.loads(response.content))

    def createDocuments(self, employees, departments):
        dict = {}
        document = self.getDocumentObject(employees["creator1"], departments["finance"], 1)
        dict["document1"] = self.signInAndCreateDocument(document, "creator1", "creatorpassword")
        document = self.getDocumentObject(employees["creator1"], departments["account"], 2)
        dict["document2"] = self.signInAndCreateDocument(document, "creator1", "creatorpassword")
        document = self.getDocumentObject(employees["creator2"], departments["finance"], 3)
        dict["document3"] = self.signInAndCreateDocument(document, "creator2", "creatorpassword")
        document = self.getDocumentObject(employees["creator2"], departments["account"], 4)
        dict["document4"] = self.signInAndCreateDocument(document, "creator2", "creatorpassword")
        document = self.getDocumentObject(employees["creator3"], departments["finance"], 5)
        dict["document5"] = self.signInAndCreateDocument(document, "creator3", "creatorpassword")
        document = self.getDocumentObject(employees["creator3"], departments["account"], 6)
        dict["document6"] = self.signInAndCreateDocument(document, "creator3", "creatorpassword")
        document = self.getDocumentObject(employees["creator4"], departments["finance"], 7)
        dict["document7"] = self.signInAndCreateDocument(document, "creator4", "creatorpassword")
        document = self.getDocumentObject(employees["creator4"], departments["account"], 8)
        dict["document8"] = self.signInAndCreateDocument(document, "creator4", "creatorpassword")
        return dict
    
    def searchDocument(self, title, creatorId, departmentId, token):
        searchRequest = {}
        if (title == None):
            return "No title"
        searchRequest["title"] = title
        if (creatorId != None):
            searchRequest["creatorId"] = creatorId
        if (departmentId != None):
            searchRequest["departmentId"] = departmentId
        header = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
        response = requests.get(url=documentUrl + "/?" + urllib.parse.urlencode(searchRequest), headers=header)
        
        if (response.status_code < 300):
            print(response.content)
        else:
            print(response.status_code)