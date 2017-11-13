import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_type = form.getvalue("Type")

if session_id is None or test_type is None:
    status = "Failure"
    test_id = 0
    questions = ""
else:
    status = "Success"
    test_id = 12
    questions = ""

answer = json.dumps({"Status" : status,
                     "TestID" : test_id,
                     "Questions" : questions})

print("Content-type: application/json")
print()
print(answer)