import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")
answers = []
i = 1
while not (form.getvalue("Answer-" + str(i)) is None):
    answers.append(form.getvalue("Answer-" + str(i)))
    i = i + 1


response = {}

if session_id is None or test_id is None or answers is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"
    right_answers = []
    for i in range(5):
        right_answer = "rightanswer" + str(i)
        right_answers.append(right_answer)
    response["RightAnswers"] = right_answers


print("Content-type: application/json")
print()
print(json.dumps(response))