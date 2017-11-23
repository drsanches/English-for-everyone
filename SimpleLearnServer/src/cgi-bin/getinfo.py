import cgi
import json

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")

response = {}

if session_id is None:
    response["Status"] = "Failure"
else:
    response["Status"] = "Success"
    information = {}
    information["Username"] = "fdhfjghr"
    information["NativeLanguage"] = "mfkfdk"
    information["XP"] = 1234
    languages = []
    for i in range(5):
        language = {}
        language["Language"] = "Lang" + str(i)
        language["Level"] = 10 + i
        languages.append(language)
    information["Languages"] = languages
    response["Information"] = information

print("Content-type: application/json")
print()
print(json.dumps(response))