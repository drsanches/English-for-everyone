import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")
lesson_type = form.getvalue("Type")

response = {}

if session_id is None or lesson_type is None:
    response["Status"] = "Failure"
else:
    if  test_id is None:
        response["Status"] = "Success"
        try:
            connection = sqlite3.connect(db_address.get_db_address())
            cursor = connection.cursor()

            query = "SELECT UserID FROM Sessions WHERE SessionID = ?"
            cursor.execute(query, (session_id,))
            if len(cursor.fetchall()) != 1:
                raise Exception()



            connection.commit()
            connection.close()

            response["Status"] = "Success"
        except:
            response["Status"] = "Failure"
        lesson = []
        for i in range(5):
            pair = {}
            pair["NativeWord"] = "Nativeword" + str(i)
            pair["ForeignWord"] = "Foreignword" + str(i)
            lesson.append(pair)
        response["Lesson"] = lesson
    else:
        response["Status"] = "Success"
        lesson = []
        for i in range(5):
            pair = {}
            pair["NativeWord"] = "Nativeword" + str(i)
            pair["ForeignWord"] = "Foreignword" + str(i)
            lesson.append(pair)
        response["Lesson"] = lesson


print("Content-type: application/json")
print()
print(json.dumps(response))