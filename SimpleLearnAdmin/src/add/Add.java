package add;

import dbaddress.DBAddress;
import org.json.*;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Add {
    public static void add(String[] args) {
        if (args.length == 0) {
            System.out.print("Path to the dictionary: ");
            String filePath = (new Scanner(System.in)).nextLine();
            addDictionaryFile(filePath);
        } else if (args.length == 1) {
            String filePath = args[0];
            addDictionaryFile(filePath);
        } else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void addDictionaryFile(String filePath) {
        try {
            String dbPath = DBAddress.getAddress();

            JSONObject jsonDictionary = readJsonFromFile(filePath);
            String theme = jsonDictionary.getString("Theme");
            String level = jsonDictionary.getString("Level");
            String nativeLanguage = jsonDictionary.getString("NativeLanguage");
            String foreignLanguage = jsonDictionary.getString("ForeignLanguage");

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            PreparedStatement pstmt = null;

            //Inserting of dictionary
            String sql = "INSERT INTO Dictionary(LevelID, Theme)\n" +
                    "SELECT LevelID, ?\n" +
                    "FROM Level\n" +
                    "WHERE LevelName = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, theme);
            pstmt.setString(2, level);
            pstmt.executeUpdate();

            //Taking id of dictionary
            sql = "SELECT DictionaryID FROM Dictionary " +
                    "LEFT JOIN Level ON Dictionary.LevelID = Level.LevelID " +
                    "WHERE Theme = ? AND LevelName = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, theme);
            pstmt.setString(2, level);
            ResultSet resultSet = pstmt.executeQuery();
            String dictionaryId = resultSet.getString(1);

            JSONArray words = (JSONArray) jsonDictionary.getJSONArray("Words");

            for (int i = 0; i < words.length(); i++) {
                JSONObject word = words.getJSONObject(i);
                String nativeWord = word.getString("NativeWord");
                String nativeTranscription = word.getString("NativeTranscription");
                String foreignWord = word.getString("ForeignWord");
                String foreignTranscription = word.getString("ForeignTranscription");

                //Checking for doubles for native word
                sql = "SELECT WordID FROM Words WHERE Spell = ? AND Phonetic = ?;";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, nativeWord);
                pstmt.setString(2, nativeTranscription);
                resultSet = pstmt.executeQuery();
                if (!resultSet.next()) {
                    sql = "INSERT INTO Words(LangID, Spell, Phonetic)\n" +
                            "SELECT LangID, ?, ?\n" +
                            "FROM Languages\n" +
                            "WHERE LangName = ?;";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, nativeWord);
                    pstmt.setString(2, nativeTranscription);
                    pstmt.setString(3, nativeLanguage);
                    pstmt.executeUpdate();
                }

                //Checking for doubles for foreign word
                sql = "SELECT WordID FROM Words WHERE Spell = ? AND Phonetic = ?;";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, foreignWord);
                pstmt.setString(2, foreignTranscription);
                resultSet = pstmt.executeQuery();
                if (!resultSet.next()) {
                    sql = "INSERT INTO Words(LangID, Spell, Phonetic)\n" +
                            "SELECT LangID, ?, ?\n" +
                            "FROM Languages\n" +
                            "WHERE LangName = ?;";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, foreignWord);
                    pstmt.setString(2, foreignTranscription);
                    pstmt.setString(3, foreignLanguage);
                    pstmt.executeUpdate();
                }

                //Taking id of native word
                sql = "SELECT WordID FROM Words WHERE Spell = ? AND Phonetic = ?;";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, nativeWord);
                pstmt.setString(2, nativeTranscription);
                resultSet = pstmt.executeQuery();
                String nativeWordId = resultSet.getString(1);

                //Taking id of foreign word
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, foreignWord);
                pstmt.setString(2, foreignTranscription);
                resultSet = pstmt.executeQuery();
                String foreignWordId = resultSet.getString(1);

                //Checking for doubles for pair
                sql = "SELECT ID FROM Pair WHERE DicID = ? AND Word1ID = ? AND Word2ID = ?;";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, dictionaryId);
                pstmt.setString(2, nativeWordId);
                pstmt.setString(2, foreignWordId);
                resultSet = pstmt.executeQuery();
                if (!resultSet.next()) {
                    sql = "INSERT INTO Pair(DicID, Word1ID, Word2ID) VALUES(?, ?, ?);";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, dictionaryId);
                    pstmt.setString(2, nativeWordId);
                    pstmt.setString(3, foreignWordId);
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();
            connection.close();
            System.out.println("The dictionary was added.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private static JSONObject readJsonFromFile(String filePath) throws Exception{
        String stringDictionary = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
        String nextString;
        while ((nextString = reader.readLine()) != null) {
            stringDictionary += nextString;
        }
        stringDictionary = stringDictionary.substring(1, stringDictionary.length());
        reader.close();

        JSONObject jsonDictionary = new JSONObject(stringDictionary);
        return jsonDictionary;
    }
}