import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_type = form.getvalue("Type")

response = {}

if session_id is None or test_type is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"
    response["TestId"] = 12543
    questions = []
    for i in range(2):
        question = {}
        question["Question"] = "Question" + str(i)
        answers = []
        for j in range(3):
            answer = "answer" + str(i) + str(j)
            answers.append(answer)
        question["Answers"] = answers
        questions.append(question)
    response["Questions"] = questions


print("Content-type: application/json")
print()
print(json.dumps(response))