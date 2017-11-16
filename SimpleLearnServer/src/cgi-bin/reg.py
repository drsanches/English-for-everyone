import cgi
import json

form = cgi.FieldStorage()

username = form.getvalue("Username")
password = form.getvalue("Password")
email = form.getvalue("E-mail")

if username is None or password is None or email is None:
    status = "Failure"
else:
    status = "Success"

answer = json.dumps({"Status": status})

print("Content-type: application/json")
print()
print(answer)