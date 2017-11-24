import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")
lesson_type = form.getvalue("Type")

response = {}

if session_id is None or test_id is None or lesson_type is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"
    lesson = []
    for i in range(5):
        pair = {}
        pair["NativeWord"] = "Nativeword" + str(i)
        pair["ForeignWord"] = "Foreignword" + str(i)
        lesson.append(pair)
    response["Lesson"] = lesson


print("Content-type: application/json")
print()
print(json.dumps(response))