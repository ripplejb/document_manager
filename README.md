## Authorization Using custom [SecurityRule](https://micronaut-projects.github.io/micronaut-security/1.2.x/guide/#securityRule).

Here we have implemented a scalable Security Policy by implementing custom [SecurityRule](https://micronaut-projects.github.io/micronaut-security/1.2.x/guide/#securityRule) that Micronauts framework provides.

#### Scopes:
We defined scopes for each user role and security policy. The format of a scope is *Tag*:*Privilege*.

##### Tag
The tag indicates limit of resource that a user can access or perform action on. For example, *document.read* with *executive* tag can read any document from any department. However, *manager* can view document from only the department (s)he belong to.

##### Privilege
The privilege part of scope indicate that if the user can access or perform action on certain resources or not.  

#### User Roles:
Each user will have one role.
Each role will have a list of scopes. 
```yaml
user-roles:
  executive:
    scopes:
      - 'executive:document.read'
      - 'executive:department.create'
  manager:
    scopes:
      - 'manager:document.read'
      - 'manager:document.update'
  creator:
    scopes:
      - 'creator:document.full'
```

#### Security Policies
Each endpoint of the service will have one policy.
Each policy will have list of scopes.
```yaml
authorization-policies:
  document-read:
    scopes:
      - 'executive:document.read'
      - 'manager:document.read'
      - 'creator:document.full'
  document-update:
    scopes:
      - 'manager:document.update'
      - 'creator:document.full'
  document-create:
    scopes:
      - 'creator:document.full'
  employee-create:
    scopes:
      - 'allow-all'
  # Deny All example. it is not used.
  employee-search:
    scopes:
      - 'deny-all'
  department-create:
    scopes:
      - 'executive:department.create'
```

The JWT token of the signed-in user contains a list of scope belongs to the user's role.
One or zero scope match with a scope in policy mentioned in the [SecurityPolicy](https://github.com/ripplejb/document_manager/blob/master/src/main/java/com/example/services/security/authorization/SecurityPolicy.java) annotation. If the match found, we call the [PolicyEnforcer](https://github.com/ripplejb/document_manager/blob/master/src/main/java/com/example/services/security/authorization/enforcers/PolicyEnforcer.java). The [PolicyEnforcer](https://github.com/ripplejb/document_manager/blob/master/src/main/java/com/example/services/security/authorization/enforcers/PolicyEnforcer.java) validates the scope privileges and then calls the [TagEnforcer](https://github.com/ripplejb/document_manager/blob/master/src/main/java/com/example/services/security/authorization/enforcers/tags/TagEnforcer.java). The [TagEnforcer](https://github.com/ripplejb/document_manager/blob/master/src/main/java/com/example/services/security/authorization/enforcers/tags/TagEnforcer.java) validates the tag by checking against the employee or the department id in the request.

#### Authorization UML

![Authorization UML!](https://github.com/ripplejb/document_manager/blob/master/Package%20authorization.jpg)