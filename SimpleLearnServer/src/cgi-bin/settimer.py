import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()
session_id = form.getvalue("SessionId")
time = form.getvalue("Time")
days = form.getvalue("Days")
count = form.getvalue("Count")
response = {}

if session_id is None or time is None or days is None or count is None:
    response["Status"] = "Failure"
else:
    try:
        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()

        query = "SELECT * FROM Sessions WHERE Sessions.SessionID = ?"
        cursor.execute(query, (session_id,))
        if len(cursor.fetchall()) != 1:
            raise Exception()

        query = """INSERT INTO Timers (UserID, Count, Time, Days)
                SELECT Sessions.UserID, ?, ?, ?
                FROM Sessions
                WHERE SessionID = ?"""
        cursor.execute(query, (count, time, days, session_id))
        connection.commit()
        connection.close()

        response["Status"] = "Success"
    except:
        response["Status"] = "Failure"


print("Content-type: application/json")
print()
print(json.dumps(response))