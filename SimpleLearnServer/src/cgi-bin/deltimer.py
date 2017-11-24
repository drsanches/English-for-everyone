import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
timer_id = form.getvalue("TimerId")

response = {}


if session_id is None or timer_id is None:
    response["Status"] = "Failure"
else:
    timer_id = int(timer_id)
    response["Status"] = "Success"


print("Content-type: application/json")
print()
print(json.dumps(response))