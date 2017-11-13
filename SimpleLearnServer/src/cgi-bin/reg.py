import cgi

form = cgi.FieldStorage()

username = form.getvalue("Username")
password = form.getvalue("Password")
email = form.getvalue("E-mail")

if username is None or password is None or email is None:
    answer = "{\"Status\": \"Failure\"}"
else:
    answer = "{\"Status\": \"Success\"}"

print("Content-type: application/json")
print()
print(answer)