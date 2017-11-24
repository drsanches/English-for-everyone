import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")

response = {}

if session_id is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"
    timers = []
    for i in range(5):
        timer = {}
        timer["Id"] = 10 + i
        timer["Time"] = "10:0" + str(i)
        timer["Period"] = "every week"
        timer["Count"] = i
        timers.append(timer)
    response["Timers"] = timers

print("Content-type: application/json")
print()
print(json.dumps(response))