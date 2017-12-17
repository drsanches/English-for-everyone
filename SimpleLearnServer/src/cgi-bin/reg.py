import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()

username = form.getvalue("Username")
password = form.getvalue("Password")
email = form.getvalue("E-mail")

response = {}

if username is None or password is None or email is None:
    response["Status"] = "Failure"
else:
    try:
        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()
        query = "INSERT INTO Users(UserName, UserPassword, Email, XP) VALUES(?, ?, ?, 0)"
        cursor.execute(query, (username, password, email))
        connection.commit()
        connection.close()
        response["Status"] = "Success"
    except:
        response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))