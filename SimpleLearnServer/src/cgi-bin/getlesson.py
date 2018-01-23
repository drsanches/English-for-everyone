import cgi
import json
import sqlite3
import db_address
import random

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")

response = {}

if session_id is None:
    response["Status"] = "Failure"
else:
        try:
            connection = sqlite3.connect(db_address.get_db_address())
            cursor = connection.cursor()

            query = "SELECT * FROM Sessions WHERE SessionID = ?"
            cursor.execute(query, (session_id,))
            if len(cursor.fetchall()) != 1:
                raise Exception()

            query = """SELECT ID FROM Pair"""
            cursor.execute(query, ())
            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)
            WordsForLesson = random.sample(words_id, 5)

            query = "SELECT NLang, FLang, Sessions.UserID FROM Users left join Sessions ON Users.UserID = Sessions.UserID WHERE SessionID = ?"
            cursor.execute(query, (session_id,))

            for row in cursor:
                lang = row[0]
                f_lang = row[1]
                user_id = row[2]

            query = """Select ID, Words.LangID, Spell, Phonetic FROM Pair
                        left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID"""
            cursor.execute(query, ())
            rows = cursor.fetchall()

            for element in WordsForLesson:
                query = """INSERT INTO WordsToLearn (UserID, PairID, LangID) VALUES (?, ?, ?)"""
                cursor.execute(query, (user_id, element, f_lang))

            lesson = []
            for i in range(5):
                pair = {}
                for row in rows:
                    if row[0] == WordsForLesson[i-1] and row[1] != int(lang):
                        pair["ForeignWordSpell"] = row[2]
                        pair["ForeignWordTranscription"] = row[3]
                    else:
                        if row[0] == WordsForLesson[i-1] and row[1] == int(lang):
                            pair["NativeWordSpell"] = row[2]
                            pair["NativeWordTranscription"] = row[3]
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