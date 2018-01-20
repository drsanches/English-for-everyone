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

        query = "SELECT UserID FROM Sessions WHERE Sessions.SessionID = ?"
        cursor.execute(query, (session_id,))
        if len(cursor.rowcount()) != 1:
           raise Exception()
        else:
            user_id = cursor.fetchone()[0]

            query = "DELETE FROM Timers WHERE UserID = ?"
            cursor.execute(query, (user_id,))
            query = "DELETE FROM WordToLearn WHERE UserID = ?"
            cursor.execute(query, (user_id,))
            query = "DELETE FROM Progress WHERE UserID = ?"
            cursor.execute(query, (user_id,))
            query = "DELETE FROM WordToRepeat WHERE UserID = ?"
            cursor.execute(query, (user_id,))

            query = "SELECT TimeRep FROM W_To_Repeat WHERE Users.UserID = ?"
            cursor.execute(query, (user_id,))
            if len(cursor.rowcount()) != 0:
                timerepeat_id = cursor.fetchone()[0]

            query = "DELETE FROM TimeRepeat  WHERE TimeRepeatID = ?"
            cursor.execute(query, (timerepeat_id,))
            query = "DELETE FROM UserLevel WHERE UserID = ?"
            cursor.execute(query, (user_id,))
            query = "DELETE FROM Sessions WHERE UserID = ?"
            cursor.execute(query, (user_id,))
            query = "DELETE FROM Users WHERE UserID = ?"
            cursor.execute(query, (user_id,))

        connection.commit()
        connection.close()

        response["Status"] = "Success"
    except:
        response["Status"] = "Failure2"


print("Content-type: application/json")
print()
print(json.dumps(response))