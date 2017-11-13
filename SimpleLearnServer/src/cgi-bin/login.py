import cgi
import json

form = cgi.FieldStorage()

username = form.getvalue("Username")
password = form.getvalue("Password")
rememberMe = form.getvalue("RememberMe")

if username is None or password is None or rememberMe is None:
    status = "Failure"
    sessionId = 0
    startTime = 0
    expiryPeriod = 0
else:
    status = "Success"
    sessionId = 123
    startTime = 1
    expiryPeriod = 10


answer = json.dumps({"Status" : status,
                     "SessionId" : sessionId,
                     "StartTime" : startTime,
                     "ExpiryPeriod" : expiryPeriod})

print("Content-type: application/json")
print()
print(answer)