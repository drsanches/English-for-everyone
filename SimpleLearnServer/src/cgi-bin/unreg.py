import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")

if session_id is None:
    status = "Failure"
else:
    status = "Success"

answer = json.dumps({"Status" : status})

print("Content-type: application/json")
print()
print(answer)