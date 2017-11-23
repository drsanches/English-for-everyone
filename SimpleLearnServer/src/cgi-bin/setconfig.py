import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
native_language = form.getvalue("NativeLanguage")
foreign_language = form.getvalue("ForeignLanguage")
level = form.getvalue("Level")

response = {}

if session_id is None or native_language is None or foreign_language is None or level is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"


print("Content-type: application/json")
print()
print(json.dumps(response))