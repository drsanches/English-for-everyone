import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")

if session_id is None or test_id is None:
    status = "Failure"
    answer = json.dumps({"Status": status})
else:
    status = "Success"
    lesson = ""
    answer = json.dumps({"Status": status,
                         "Lesson": lesson})

print("Content-type: application/json")
print()
print(answer)