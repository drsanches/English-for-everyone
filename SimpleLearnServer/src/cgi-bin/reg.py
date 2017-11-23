import cgi
import json

form = cgi.FieldStorage()

username = form.getvalue("Username")
password = form.getvalue("Password")
email = form.getvalue("E-mail")

response = {}

if username is None or password is None or email is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"


print("Content-type: application/json")
print()
print(json.dumps(response))