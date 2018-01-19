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

        query = """SELECT TimersID, Count, Time, Days 
                FROM Timers left JOIN Sessions 
                ON Timers.UserID = Sessions.UserID 
                WHERE SessionID = ?"""
        cursor.execute(query, (session_id,))

        response["Status"] = "Success"
        timers = []

        for row in cursor:
            timer = {}
            timer["Id"] = row[0]
            timer["Count"] = row[1]
            timer["Time"] = row[2]
            timer["Days"] = row[3]
            timers.append(timer)

        response["Timers"] = timers
        connection.close()
    except:
        response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))