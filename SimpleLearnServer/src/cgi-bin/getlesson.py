import cgi
import json
import sqlite3
import db_address
import random

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")
lesson_type = form.getvalue("Type")

response = {}

if session_id is None or lesson_type is None or test_id is None:
    response["Status"] = "Failure"
else:
        try:
            connection = sqlite3.connect(db_address.get_db_address())
            cursor = connection.cursor()

            query = "SELECT * FROM Sessions WHERE SessionID = ?"
            cursor.execute(query, (session_id,))
            if len(cursor.fetchall()) != 1:
                raise Exception()

            query = """SELECT PairID FROM WordsToLearn
                    left join Sessions ON WordsToLearn.UserID = Sessions.UserID
                    WHERE SessionID = ?"""
            cursor.execute(query, (session_id,))
            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)
            WordsForLesson = random.sample(words_id, 5)

            query = "SELECT NLang FROM Users left join Sessions ON Users.UserID = Sessions.UserID WHERE SessionID = ?"
            cursor.execute(query, (session_id,))
            lang = cursor.fetchone()[0]

            query = """Select PairID, Words.LangID, Spell, Phonetic FROM WordsToLearn left join Pair ON WordsToLearn.PairID = Pair.ID
                        left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                        left join Sessions ON Sessions.UserID = WordsToLearn.UserID
                        WHERE SessionID = ?"""
            cursor.execute(query, (session_id,))
            rows = cursor.fetchall()

            lesson = []
            for i in range(5):
                pair = {}
                for row in rows:
                    if row[0] == WordsForLesson[i-1] and row[1] != int(lang):
                        pair["ForeignWord Spell"] = row[2]
                        pair["ForeignWord Phonetic"] = row[3]
                    else:
                        if row[0] == WordsForLesson[i-1] and row[1] == int(lang):
                            pair["NativeWord Spell"] = row[2]
                            pair["NativeWord Phonetic"] = row[3]
                lesson.append(pair)
            response["Lesson"] = lesson

            connection.commit()
            connection.close()

            response["Status"] = "Success"
        except:
            response["Status"] = "Failure"

print("Content-type: application/json")
print()
print(json.dumps(response))