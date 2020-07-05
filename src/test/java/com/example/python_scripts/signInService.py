import requests
import json

class SignInResponse(object):
    def __init__(self, username, roles, access_token, token_type, expires_in, *args, **kwards):
        self.username = username
        self.roles = roles
        self.access_token = access_token
        self.token_type = token_type
        self.expires_in = expires_in

base = "http://localhost:8080/"
loginUrl = base + "login"
signInHeader = {'Content-Type': 'application/json'}

class SignInService(object):
    def __init__(self, username, password):
        self.username=username
        self.password=password
        
    def signIn(self):
        payload = {
            "username": self.username,
            "password": self.password
        }
        response = requests.post(url=loginUrl, json=payload, headers=signInHeader)
        return SignInResponse(**json.loads(response.content))



