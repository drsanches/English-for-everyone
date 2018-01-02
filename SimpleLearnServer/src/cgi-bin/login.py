import cgi
import json
import sqlite3
import db_address
import uuid
import datetime

form = cgi.FieldStorage()
username = form.getvalue("Username")
password = form.getvalue("Password")
remember_me = form.getvalue("RememberMe") in ['true', '1', 't', 'y', 'yes', 'True', 'TRUE']
response = {}

if username is None or password is None:
    response["Status"] = "Failure"
else:
    session_id = uuid.uuid4()
    start_time = datetime.datetime.now()
    if remember_me:
        expiry_period = datetime.timedelta(10000) #10000 days
    else:
        expiry_period = datetime.timedelta(1) #1 day

    try:
        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()
        query = """INSERT INTO Sessions(SessionID, UserID, Started, Expires)
                SELECT ?, Users.UserID, ?, ?
                FROM Users
                WHERE Users.UserName = ? AND Users.UserPassword = ?"""
        cursor.execute(query, (str(session_id), str(start_time), str(expiry_period), username, password))
        connection.commit()
        connection.close()

        response["Status"] = "Success"
        response["SessionId"] = str(session_id)
        response["StartTime"] = str(start_time)
        response["ExpiryPeriod"] = str(expiry_period)
    except:
        response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))