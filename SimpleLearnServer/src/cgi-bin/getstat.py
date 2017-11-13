import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")

if session_id is None:
    status = "Failure"
    statistics = ""
else:
    status = "Success"
    statistics = ""

answer = json.dumps({"Status" : status,
                     "Statistics" : statistics})

print("Content-type: application/json")
print()
print(answer)