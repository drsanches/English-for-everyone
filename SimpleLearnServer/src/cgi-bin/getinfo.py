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

        query = """SELECT UserName, LangName, XP FROM Users 
                left JOIN Sessions On Users.UserID = Sessions.UserID 
                left JOIN Languages On Users.NLang = Languages.LangID 
                WHERE Sessions.SessionID = ?"""
        cursor.execute(query, (session_id,))

        response["Status"] = "Success"
        users = []

        for row in cursor:
            user = {}
            user["Name"] = row[0]
            user["Native language"] = row[1]
            user["XP"] = row[2]
            users.append(user)

        response["Users"] = users

        query = """SELECT LangName, LevelName FROM UserLevel 
                left JOIN Languages On UserLevel.LangID = Languages.LangID
                left JOIN Level On UserLevel.LevelID = Level.LevelID 
                WHERE UserLevel.UserID = (SELECT UserID FROM Sessions WHERE SessionID = ?)"""
        cursor.execute(query, (session_id,))

        langLevels = []

        for row in cursor:
            langLevel = {}
            langLevel["Foreign language"] = row[0]
            langLevel["Level"] = row[1]
            langLevels.append(langLevel)

        response["LangLevels"] = langLevel
        connection.close()
    except:
        response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))