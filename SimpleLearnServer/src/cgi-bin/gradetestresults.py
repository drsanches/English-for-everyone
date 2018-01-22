import cgi
import json
import sqlite3
import db_address
import datetime
from datetime import timedelta

form = cgi.FieldStorage()

session_id = form.getvalue("SessionId")
test_id = form.getvalue("TestId")
answers = []
i = 1
while not (form.getvalue("Answer-" + str(i)) is None):
    answers.append(form.getvalue("Answer-" + str(i)))
    i = i + 1


response = {}

if session_id is None or test_id is None or answers is None:
    response["Status"] = "Failure"
else:
    # try:
        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()

        response["Status"] = "Success"
        query = "SELECT * FROM Sessions WHERE Sessions.SessionID = ?"
        cursor.execute(query, (session_id,))
        if len(cursor.fetchall()) != 1:
            raise Exception()

        query = "SELECT TypeID FROM Test WHERE TestID = ?"
        cursor.execute(query, (test_id, ))
        test_type_id = cursor.fetchone()[0]

        query = """SELECT UserID, FLang, NLang, XP FROM Users
                WHERE UserID = (SELECT UserID FROM Sessions WHERE SessionID = ?)"""
        cursor.execute(query, (session_id, ))

        for row in cursor:
            user_id = row[0]
            f_lang = row[1]
            n_lang = row[2]
            xp = row[3]

        query = "SELECT LevelID FROM UserLevel WHERE UserID = ? AND LangID = ?"
        cursor.execute(query, (user_id, f_lang))
        level_id = cursor.fetchone()[0]

        if test_type_id == 1:
            query = "SELECT PairID FROM Test WHERE TestID = ? AND TypeID = ?"
            cursor.execute(query, (test_id, test_type_id))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            query = """Select Spell, LangID FROM Test
                    left join Pair ON Test.PairID = Pair.ID
                    left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                    left join TestType On Test.TestID = TestType.TypeID
                    WHERE TestID = ?"""
            cursor.execute(query, (test_id, ))
            rows = cursor.fetchall()

            query = "DELETE FROM Test WHERE TestID = ?"
            cursor.execute(query, (test_id, ))

            foreign_words = []
            native_words = []
            i = 0
            if int(n_lang) == int(rows[i][1]):
                while i < len(rows):
                    native_words.append(rows[i][0])
                    foreign_words.append(rows[i+1][0])
                    i = i + 2
            else:
                while i < len(rows):
                    native_words.append(rows[i+1][0])
                    foreign_words.append(rows[i][0])
                    i = i + 2

            mark = 0
            i = 0
            while i < len(answers):
                if str(answers[i]) == str(native_words[i]):
                    mark = mark + 1
                i = i + 1

            if mark >= 0 and mark < 5:
                level_id = 1
            if mark >= 5 and mark < 10:
                level_id = 2
            if mark >= 10 and mark < 15:
                level_id = 3
            if mark >= 15 and mark < 20:
                level_id = 4
            if mark >= 20 and mark < 25:
                level_id = 5
            if mark >= 25 and mark < 30:
                level_id = 6
            if mark == 30:
                level_id = 7

            query = """INSERT INTO UserLevel
                    SELECT Users.UserID, FLang, ?
                    FROM Users left join Sessions On Sessions.UserID = Users.UserID
                    WHERE SessionID = ?"""
            cursor.execute(query, (level_id, session_id))

            RightAnswers = []
            i = 0
            for element in native_words:
                RightAnswers.append(str(foreign_words[i] + " - " + native_words[i]))
                i = i + 1

            response["RightAnswers"] = RightAnswers

        if test_type_id == 2:
            N = 5
            query = "SELECT PairID FROM Test WHERE TestID = ? AND TypeID = ?"
            cursor.execute(query, (test_id, test_type_id))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            query = """Select Spell, LangID FROM Test
                    left join Pair ON Test.PairID = Pair.ID
                    left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                    left join TestType On Test.TestID = TestType.TypeID
                    WHERE TestID = ?"""
            cursor.execute(query, (test_id, ))
            rows = cursor.fetchall()

            query = "DELETE FROM Test WHERE TestID = ?"
            cursor.execute(query, (test_id, ))

            foreign_words = []
            native_words = []
            i = 0
            if int(n_lang) == int(rows[i][1]):
                while i < len(rows):
                    native_words.append(rows[i][0])
                    foreign_words.append(rows[i+1][0])
                    i = i + 2

            else:
                while i < len(rows):
                    native_words.append(rows[i+1][0])
                    foreign_words.append(rows[i][0])
                    i = i + 2

            right_id = []
            wrong_id = []
            i = 0
            for element in words_id:
                if str(answers[i]) != str(native_words[i]) or str(answers[i+5]) != str(foreign_words[i]) or str(answers[i+10]) != str(foreign_words[i]):
                    wrong_id.append(element)
                else:
                    right_id.append(element)
                i = i + 1

            now = datetime.datetime.now()
            for element in right_id:
                query = "SELECT Time FROM TimeRepeat WHERE TimeRepeatID = ?"
                cursor.execute(query, (1, ))
                deltaT = timedelta(int(cursor.fetchone()[0]))
                time = now + deltaT
                time = str(time).split()
                date = time[0]
                date = date.split("-")
                date.reverse()
                d = date[0] + " " + date[1] + " " + date[2]

                query = """INSERT INTO WordToRepeat 
                        (UserID, PairID, TimeToRepeatID, Time, LangID)
                        VALUES (?, ?, ?, ?, ?)"""
                cursor.execute(query, (user_id, element, 1, d, f_lang))

                query = "DELETE FROM WordsToLearn WHERE PairID = ? AND UserID = ?"
                cursor.execute(query, (element, user_id))

            query = """UPDATE Users
                    SET XP = ? WHERE UserID = ?"""
            cursor.execute(query, (xp + len(right_id), user_id))

            if level_id != 7:
                query = "SELECT NeededWords FROM Level WHERE LevelID = ?"
                cursor.execute(query, (level_id + 1, ))
                needed_words = cursor.fetchone()[0]

                query = "SELECT * FROM WordToRepeat WHERE UserID = ?"
                cursor.execute(query, (user_id, ))
                learned_words = len(cursor.fetchall())

                if learned_words >= needed_words:
                    query = """UPDATE UserLevel
                            SET LevelID = ?
                            WHERE UserID = ? AND LangID = ?"""
                    cursor.execute(query, (level_id + 1, user_id, f_lang))

            RightAnswers = []
            i = 0
            for element in native_words:
                RightAnswers.append(str(foreign_words[i] + " - " + native_words[i]))
                i = i + 1

            response["RightAnswers"] = RightAnswers

        if test_type_id == 3:
            N = 5
            query = "SELECT PairID FROM Test WHERE TestID = ? AND TypeID = ?"
            cursor.execute(query, (test_id, test_type_id))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            query = """Select Spell, LangID FROM Test
                        left join Pair ON Test.PairID = Pair.ID
                        left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                        left join TestType On Test.TestID = TestType.TypeID
                        WHERE TestID = ?"""
            cursor.execute(query, (test_id, ))
            rows = cursor.fetchall()

            query = "DELETE FROM Test WHERE TestID = ?"
            cursor.execute(query, (test_id, ))

            foreign_words = []
            native_words = []
            i = 0
            if int(n_lang) == int(rows[i][1]):
                while i < len(rows):
                    native_words.append(rows[i][0])
                    foreign_words.append(rows[i+1][0])
                    i = i + 2

            else:
                while i < len(rows):
                    native_words.append(rows[i+1][0])
                    foreign_words.append(rows[i][0])
                    i = i + 2

            right_id = []
            wrong_id = []
            i = 0
            for element in words_id:
                if str(answers[i]) != str(native_words[i]) or str(answers[i+5]) != str(foreign_words[i]) or str(answers[i+10]) != str(foreign_words[i]):
                    wrong_id.append(element)
                else:
                    right_id.append(element)
                i = i + 1

            now = datetime.datetime.now()
            for element in right_id:
                query = "SELECT TimeToRepeatID FROM WordToRepeat WHERE PairID = ?"
                cursor.execute(query, (element, ))
                time_id = cursor.fetchone()[0]

                if time_id == 3:
                    query = "SELECT Time FROM TimeRepeat WHERE TimeRepeatID = ?"
                    cursor.execute(query, (time_id, ))
                    deltaT = timedelta(int(cursor.fetchone()[0]))
                    time = now + deltaT
                    time = str(time).split()
                    date = time[0]
                    date = date.split("-")
                    date.reverse()
                    d = date[0] + " " + date[1] + " " + date[2]
                else:
                    time_id = time_id + 1
                    query = "SELECT Time FROM TimeRepeat WHERE TimeRepeatID = ?"
                    cursor.execute(query, (time_id, ))
                    deltaT = timedelta(int(cursor.fetchone()[0]))
                    time = now + deltaT
                    time = str(time).split()
                    date = time[0]
                    date = date.split("-")
                    date.reverse()
                    d = date[0] + " " + date[1] + " " + date[2]

                    query = """UPDATE WordToRepeat SET TimeToRepeatID = ?, Time = ? WHERE PairID = ?"""
                    cursor.execute(query, (time_id, d, element))

            for element in wrong_id:
                query = "INSERT INTO WordsToLearn (UserID, PairID, LangID) VALUES (?, ?, ?)"
                cursor.execute(query, (user_id, element, f_lang))

                query = "DELETE FROM WordToRepeat WHERE UserID = ? AND PairID = ?"
                cursor.execute(query, (user_id, element))

            query = """UPDATE Users
                        SET XP = ? WHERE UserID = ?"""
            cursor.execute(query, (xp + len(right_id), user_id))

            if level_id != 1:
                query = "SELECT NeededWords FROM Level WHERE LevelID = ?"
                cursor.execute(query, (level_id, ))
                needed_words = cursor.fetchone()[0]

                query = "SELECT * FROM WordToRepeat WHERE UserID = ?"
                cursor.execute(query, (user_id, ))
                learned_words = len(cursor.fetchall())

                if learned_words <= needed_words:
                    query = """UPDATE UserLevel
                                SET LevelID = ?
                                WHERE UserID = ? AND LangID = ?"""
                    cursor.execute(query, (level_id - 1, user_id, f_lang))

            RightAnswers = []
            i = 0
            for element in native_words:
                RightAnswers.append(str(foreign_words[i] + " - " + native_words[i]))
                i = i + 1

            response["RightAnswers"] = RightAnswers

        connection.commit()
        connection.close()
    # except:
    #     response["Status"] = "Failure"



print("Content-type: application/json")
print()
print(json.dumps(response))