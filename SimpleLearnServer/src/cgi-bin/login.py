import cgi
import json

form = cgi.FieldStorage()

username = form.getvalue("Username")
password = form.getvalue("Password")
remember_me = form.getvalue("RememberMe")

response = {}

if username is None or password is None or remember_me is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"
    response["SessionId"] = 123
    response["StartTime"] = 1
    response["ExpiryPeriod"] = 10

print("Content-type: application/json")
print()
print(json.dumps(response))