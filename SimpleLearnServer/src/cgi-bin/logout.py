import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()
session_id = form.getvalue("SessionId")

response = {}

if session_id is None:
    response["Status"] = "Failure"
else:
    try:
        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()

        query = "SELECT * FROM Sessions WHERE Sessions.SessionID = ?"
        cursor.execute(query, (session_id,))
        if len(cursor.fetchall()) != 1:
            raise Exception()

        query = "DELETE FROM Sessions WHERE SessionID = ?"
        cursor.execute(query, (session_id,))

        connection.commit()
        connection.close()

        response["Status"] = "Success"
    except:
        response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))