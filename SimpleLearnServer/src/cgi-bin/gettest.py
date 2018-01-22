import cgi
import json
import sqlite3
import db_address
import random
import datetime

form = cgi.FieldStorage()
session_id = form.getvalue("SessionId")
test_type = form.getvalue("Type")
response = {}

if session_id is None or test_type is None:
    response["Status"] = "Failure"
else:
    # try:
        # Add languages. Which word is word and which is translation?

        connection = sqlite3.connect(db_address.get_db_address())
        cursor = connection.cursor()
        response["Status"] = "Success"

        query = "SELECT * FROM Sessions WHERE Sessions.SessionID = ?"
        cursor.execute(query, (session_id,))
        if len(cursor.fetchall()) != 1:
            raise Exception()

        query = """SELECT UserID, FLang, NLang FROM Users
                WHERE UserID = (SELECT UserID FROM Sessions WHERE SessionID = ?)"""
        cursor.execute(query, (session_id, ))

        for row in cursor:
            user_id = row[0]
            f_lang = row[1]
            n_lang = row[2]

        query = "SELECT TypeID FROM TestType WHERE TypeName = ?"
        cursor.execute(query, (test_type, ))
        test_type_id = cursor.fetchone()[0]

        query = "SELECT * FROM Test"
        cursor.execute(query, ())
        rows = cursor.fetchall()
        if len(rows) == 0:
            ID = 1
        else:
            row = rows[len(rows)-1]
            ID = row[0] + 1

        query = """SELECT LevelID FROM UserLevel
                WHERE UserID = ? AND LangID = ? """
        cursor.execute(query, (user_id, f_lang))
        if len(cursor.fetchall()) == 0:
            test_type_id = 1

        if test_type_id == 1:
            N = 10
            query = """SELECT DictionaryID FROM Dictionary
                    left join Pair ON Dictionary.DictionaryID = Pair.DicID
                    left join Words ON Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                    WHERE LangID = ?"""

            query = """Select ID FROM Pair left join Dictionary
                    ON Pair.DicID = Dictionary.DictionaryID left join Level
                    ON Dictionary.LevelID = Level.LevelID WHERE LevelName = ?"""
            cursor.execute(query, ("Elementary", ))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            WordsForTest = random.sample(words_id, N)

            query = """Select ID FROM Pair left join Dictionary
                    ON Pair.DicID = Dictionary.DictionaryID left join Level
                    ON Dictionary.LevelID = Level.LevelID WHERE LevelName = ?"""
            cursor.execute(query, ("Intermediate", ))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            WordsForTest.extend(random.sample(words_id, N))

            query = """Select ID FROM Pair left join Dictionary
                    ON Pair.DicID = Dictionary.DictionaryID left join Level
                    ON Dictionary.LevelID = Level.LevelID WHERE LevelName = ?"""
            cursor.execute(query, ("Advanced", ))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            WordsForTest.extend(random.sample(words_id, N))

            for element in WordsForTest:
                query = "INSERT INTO Test(TestID, TypeID, PairID) VALUES(?, ?, ?)"
                cursor.execute(query, (str(ID), str(test_type_id), str(element)))

            query = """Select Spell, LangID FROM Test
                    left join Pair ON Test.PairID = Pair.ID
                    left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                    left join TestType On Test.TestID = TestType.TypeID
                    WHERE TestID = ?"""
            cursor.execute(query, (ID, ))

            rows = cursor.fetchall()

            query = "SELECT Spell FROM Words WHERE Words.LangID = ?"
            cursor.execute(query, (n_lang, ))
            all_variants = []
            for row in cursor:
                variant = row[0]
                all_variants.append(variant)

            words = []
            i = 0
            if int(f_lang) == int(rows[i][1]):
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i][0] + "?"
                    i = i + 1
                    variants = random.sample(all_variants, 3)
                    var = rows[i][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)
            else:
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i+1][0] + "?"
                    i = i + 1
                    variants = random.sample(all_variants, 3)
                    var = rows[i-1][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

            response["TestId"] = ID
            response["Questions"] = words

        if test_type_id == 2:
            N = 5

            query = """SELECT PairID FROM
                    WordsToLearn left join Sessions
                    ON WordsToLearn.UserID = Sessions.UserID
                    WHERE Sessions.SessionID = ?"""
            cursor.execute(query, (session_id, ))

            words_id = []
            for row in cursor:
                id = row[0]
                words_id.append(id)

            WordsForTest = random.sample(words_id, N)

            for element in WordsForTest:
                query = "INSERT INTO Test(TestID, TypeID, PairID) VALUES(?, ?, ?)"
                cursor.execute(query, (str(ID), str(test_type_id), str(element)))

                query = "SELECT Spell FROM Words WHERE Words.LangID = ?"
            cursor.execute(query, (n_lang, ))
            all_native_variants = []
            for row in cursor:
                variant = row[0]
                all_native_variants.append(variant)

            query = "SELECT Spell FROM Words WHERE Words.LangID = ?"
            cursor.execute(query, (f_lang, ))
            all_foreign_variants = []
            for row in cursor:
                variant = row[0]
                all_foreign_variants.append(variant)

            query = """Select Spell, LangID FROM Test
                    left join Pair ON Test.PairID = Pair.ID
                    left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                    left join TestType On Test.TestID = TestType.TypeID
                    WHERE TestID = ?"""
            cursor.execute(query, (ID, ))

            words = []
            i = 0
            rows = cursor.fetchall()
            if int(f_lang) == int(rows[i][1]):
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i][0] + "?"
                    i = i + 1
                    variants = random.sample(all_native_variants, 3)
                    var = rows[i][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i+1][0] + "?"
                    i = i + 1
                    variants = random.sample(all_foreign_variants, 3)
                    var = rows[i-1][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Напишите перевод слова/выражения " + rows[i+1][0]
                    i = i + 2
                    words.append(word)
            else:
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i+1][0] + "?"
                    i = i + 1
                    variants = random.sample(all_native_variants, 3)
                    var = rows[i-1][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i][0] + "?"
                    i = i + 1
                    variants = random.sample(all_foreign_variants, 3)
                    var = rows[i][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Напишите перевод слова/выражения " + rows[i][0]
                    i = i + 2
                    words.append(word)

            response["TestId"] = ID
            response["Questions"] = words

        if test_type_id == 3:
            N = 5

            query = """SELECT PairID, Time FROM WordToRepeat 
                    left join Sessions ON WordToRepeat.UserID = Sessions.UserID 
                    WHERE Sessions.SessionID = ?"""
            cursor.execute(query, (session_id, ))

            words_id = []
            time = []
            for row in cursor:
                word = row[0]
                t = row[1]
                t = t.split()
                words_id.append(word)
                time.append(t)

            i = 0
            now = datetime.datetime.now()
            id_needed_to_remove = []
            for element in time:
                if int(time[i][2]) > now.year:
                    id_needed_to_remove.append(i)
                else:
                    if int(time[i][2]) == now.year:
                        if int(time[i][1]) > now.month:
                            id_needed_to_remove.append(i)
                        else:
                            if int(time[i][1]) == now.month:
                                if int(time[i][0]) > now.day:
                                    id_needed_to_remove.append(i)
                i = i + 1
            id_needed_to_remove.sort()
            id_needed_to_remove.reverse()

            i = 0
            for element in id_needed_to_remove:
                words_id.pop(id_needed_to_remove[i])
                i = i + 1

            if len(words_id) <= N:
                WordsForTest = words_id
            else:
                WordsForTest = random.sample(words_id, N)

            for element in WordsForTest:
                query = "INSERT INTO Test(TestID, TypeID, PairID) VALUES(?, ?, ?)"
                cursor.execute(query, (str(ID), str(test_type_id), str(element)))

            query = "SELECT Spell FROM Words WHERE Words.LangID = ?"
            cursor.execute(query, (n_lang, ))
            all_native_variants = []
            for row in cursor:
                variant = row[0]
                all_native_variants.append(variant)

            query = "SELECT Spell FROM Words WHERE Words.LangID = ?"
            cursor.execute(query, (f_lang, ))
            all_foreign_variants = []
            for row in cursor:
                variant = row[0]
                all_foreign_variants.append(variant)

            query = """Select Spell, LangID FROM Test
                    left join Pair ON Test.PairID = Pair.ID
                    left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID
                    left join TestType On Test.TestID = TestType.TypeID
                    WHERE TestID = ?"""
            cursor.execute(query, (ID, ))

            words = []
            i = 0
            rows = cursor.fetchall()

            if int(f_lang) == int(rows[i][1]):
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i][0] + "?"
                    i = i + 1
                    variants = random.sample(all_native_variants, 3)
                    var = rows[i][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i+1][0] + "?"
                    i = i + 1
                    variants = random.sample(all_foreign_variants, 3)
                    var = rows[i-1][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Напишите перевод слова/выражения " + rows[i+1][0]
                    i = i + 2
                    words.append(word)
            else:
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i+1][0] + "?"
                    i = i + 1
                    variants = random.sample(all_native_variants, 3)
                    var = rows[i-1][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Как переводится слово/выражение " + rows[i][0] + "?"
                    i = i + 1
                    variants = random.sample(all_foreign_variants, 3)
                    va5r = rows[i][0]
                    variants.append(var)
                    random.shuffle(variants)
                    word["Answers"] = variants
                    i = i + 1
                    words.append(word)

                i = 0
                while i < len(rows):
                    word = {}
                    word["Question"] = "Напишите перевод слова/выражения " + rows[i][0]
                    i = i + 2
                    words.append(word)

            response["TestId"] = ID
            response["Questions"] = words

        connection.commit()
        connection.close()
    # except:
    #     response["Status"] = "Failure"



print("Content-type: application/json")
print()
print(json.dumps(response))