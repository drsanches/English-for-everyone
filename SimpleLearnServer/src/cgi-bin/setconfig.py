import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()
session_id = form.getvalue("SessionId")
native_language = form.getvalue("NativeLanguage")
foreign_language = form.getvalue("ForeignLanguage")
level = form.getvalue("Level")
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
            query = """UPDATE Users
                    SET
                    NLang = (SELECT LangID FROM Languages WHERE LangName = ?),
                    FLang = (SELECT LangID FROM Languages WHERE LangName = ?)
                    WHERE
                    Users.UserID = (SELECT UserID FROM Sessions WHERE Sessions.SessionID = ?)"""
            cursor.execute(query, (native_language, foreign_language, session_id, ))

        response["Status"] = "Success"
    except:
        response["Status"] = "Failure"


print("Content-type: application/json")
print()
print(json.dumps(response))