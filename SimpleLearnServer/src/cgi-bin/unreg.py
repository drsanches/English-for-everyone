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
        temp = cursor.fetchall()
        if len(temp) != 1:
                raise Exception()
        else:
            user_id = temp[0][0];

            query = "DELETE FROM Timers WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))
            query = "DELETE FROM WordsToLearn WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))
            query = "DELETE FROM WordToRepeat WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))

            query = "SELECT TimeToRepeat FROM WordToRepeat WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))

            temp = cursor.fetchall()
            if len(temp) != 0:
                for time_repeat in temp:
                    query = "DELETE FROM TimeRepeat  WHERE TimeToRepeat = ?"
                    cursor.execute(query, (time_repeat[0],))

            query = "DELETE FROM UserLevel WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))
            query = "DELETE FROM Sessions WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))
            query = "DELETE FROM Users WHERE UserID = ?"
            cursor.execute(query, (str(user_id),))

        connection.commit()
        connection.close()

        response["Status"] = "Success"
    except:
        response["Status"] = "Failure2"


print("Content-type: application/json")
print()
print(json.dumps(response))