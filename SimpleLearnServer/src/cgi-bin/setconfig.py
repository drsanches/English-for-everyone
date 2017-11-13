import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
my_language = form.getvalue("MyLanguage")
learning_language = form.getvalue("LearningLanguage")
level = form.getvalue("Level")
timers = form.getvalue("Timers")

if session_id is None or my_language is None or learning_language is None or level is None or timers is None:
    status = "Failure"
else:
    status = "Success"

answer = json.dumps({"Status" : status})

print("Content-type: application/json")
print()
print(answer)