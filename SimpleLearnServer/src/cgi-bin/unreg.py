import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")


response = {}

if session_id is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"


print("Content-type: application/json")
print()
print(json.dumps(response))