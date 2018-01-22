import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()
session_id = form.getvalue("SessionId")
native_language = form.getvalue("NativeLanguage")
foreign_language = form.getvalue("ForeignLanguage")
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

        if native_language is not None and foreign_language is not None:
            query = """SELECT NLang, FLang FROM Users
                    left join Sessions ON Users.UserID = Sessions.UserID
                    WHERE SessionID = ?"""
            cursor.execute(query, (session_id,))
            user_language = cursor.fetchall()
            if native_language != user_language[0][0]:
                query = "SELECT UserID FROM Sessions WHERE Sessions.SessionID = ?"
                cursor.execute(query, (session_id,))
                temp = cursor.fetchall()
                if len(temp) != 1:
                    raise Exception()
                else:
                    user_id = temp[0][0];
                    query = "DELETE FROM WordsToLearn WHERE UserID = ?"
                    cursor.execute(query, (user_id,))
                    query = "DELETE FROM WordToRepeat WHERE UserID = ?"
                    cursor.execute(query, (int(user_id),))
                    query = "DELETE FROM UserLevel WHERE UserID = ?"
                    cursor.execute(query, (int(user_id),))
            query = """UPDATE Users
                    SET
                    NLang = (SELECT LangID FROM Languages WHERE LangName = ?),
                    FLang = (SELECT LangID FROM Languages WHERE LangName = ?)
                    WHERE
                    Users.UserID = (SELECT UserID FROM Sessions WHERE Sessions.SessionID = ?)"""
            cursor.execute(query, (native_language, foreign_language, session_id, ))
        connection.commit()
        connection.close()
        response["Status"] = "Success"
    except:
        response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))