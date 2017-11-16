import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")

if session_id is None:
    status = "Failure"
    answer = json.dumps({"Status" : status})
else:
    status = "Success"
    current_language = "ru-RU"
    learning_language = "en-US"
    level = 2
    timers = ""
    answer = json.dumps({"Status": status,
                         "CurrentLanguage": current_language,
                         "LearningLanguage": learning_language,
                         "Level": level,
                         "Timers": timers})

print("Content-type: application/json")
print()
print(answer)