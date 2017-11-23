import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
time = form.getvalue("Time")
period = form.getvalue("Period")
count = form.getvalue("Count")

response = {}

if session_id is None or time is None or period is None or count is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"


print("Content-type: application/json")
print()
print(json.dumps(response))