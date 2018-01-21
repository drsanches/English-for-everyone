import cgi
import json
import sqlite3
import db_address

form = cgi.FieldStorage()
session_id = form.getvalue("SessionId")
test_type = form.getvalue("Type")
response = {}

if session_id is None or test_type is None:
    response["Status"] = "Failure"
else:
    # try:
        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()

        query = "SELECT * FROM Sessions WHERE Sessions.SessionID = ?"
        cursor.execute(query, (session_id,))
        if len(cursor.fetchall()) != 1:
            raise Exception()

        query = """SELECT UserID, FLang FROM Users
                WHERE UserID = (SELECT UserID FROM Sessions WHERE SessionID = ?)"""
        cursor.execute(query, (session_id, ))

        for row in cursor:
            user_id = row[0]
            f_lang = row[1]

        if test_type == "1":
            query = """Select ID FROM Pair left join Dictionary 
                    ON Pair.DicID = Dictionary.DictionaryID left join Level 
                    ON Dictionary.LevelID = Level.LevelID WHERE LevelName = ?"""
            cursor.execute(query, ("Beginer", ))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            #     to do: random function

            i = 1
            for element in words_id:
                query = "INSERT INTO Test(TestID, TypeID, PairID) VALUES(?, ?, ?)"
                cursor.execute(query, (str(i), str(test_type), str(element)))
            #     We have test_type or test_type_id?

            query = """Select Spell FROM Test 
                    left join Pair ON Test.PairID = Pair.ID
                    left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID 
                    left join TestType On Test.TestID = TestType.TypeID
                    WHERE TestID = ?"""
            cursor.execute(query, (test_type, ))

            words = []
            i = 0
            rows = cursor.fetchall()
            while i < len(rows):
                word = {}
                word["Word"] = rows[i][0]
                i = i + 1
                word["Transcription"] = rows[i][0]
                i = i + 1
                words.append(word)
            response["Pairs"] = words


            response["DFGHJKL"] = str(rows)

        connection.commit()
        connection.close()
        response["Status"] = "Success"
    # except:
    #     response["Status"] = "Failure"



print("Content-type: application/json")
print()
print(json.dumps(response))