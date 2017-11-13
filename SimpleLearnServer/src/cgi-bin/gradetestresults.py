import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")
answers = form.getvalue("Answers")

if session_id is None or test_id is None or answers is None:
    status = "Failure"
    right_answers = ""
else:
    status = "Success"
    right_answers = ""

answer = json.dumps({"Status" : status,
                     "RightAnswers" : right_answers})

print("Content-type: application/json")
print()
print(answer)