import requests
import json
from employeeService import EmployeeService
from signInService import SignInService
from departmentService import DepartmentService
from documentService import DocumentService

employees = EmployeeService().getEmployees()

# Sign In executive and create department
executiveSignInResponse = SignInService("executive1", "execpassword").signIn()
departments = {}
departments["finance"] = DepartmentService().create("Finance", employees["manager1"], executiveSignInResponse.access_token)
departments["account"] = DepartmentService().create("Account", employees["manager2"], executiveSignInResponse.access_token)

# Create eight documents. 
# Four in each departments. 
# Each creator creats two documents.
documents = DocumentService().createDocuments(employees, departments)

# When manager try to access other manager's document
# Manager 1 (Finance Department) has all the odd numbered documents.
# Manager 2 (Finance Department) has all the even numbered documents.
manager1SignedIn = SignInService("manager1","manpassword").signIn()
print("Invalid search where manager is searching all documents")
DocumentService().searchDocument("title", None, None, manager1SignedIn.access_token)

print("Invalid search where manager is searching other manager's documents.")
DocumentService().searchDocument("title", None, departments["account"].id, manager1SignedIn.access_token)

print("valid search where a manager is searching their own department's documents.")
DocumentService().searchDocument("title", None, departments["finance"].id, manager1SignedIn.access_token)
