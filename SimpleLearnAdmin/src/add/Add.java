package add;

import org.json.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Add
{
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
            String stringDictionary = "";
            final BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filePath), "UTF8"));
            String nextString;
            while ((nextString = br.readLine()) != null) {
                stringDictionary += nextString;
            }
            stringDictionary = stringDictionary.substring(1, stringDictionary.length());

            JSONObject jsonDictionary = new JSONObject(stringDictionary);
            String nativeLanguage = jsonDictionary.getString("NativeLanguage");
            String foreignLanguage = jsonDictionary.getString("ForeignLanguage");
            JSONArray words = (JSONArray) jsonDictionary.getJSONArray("Words");

            for (int i = 0; i < words.length(); i++) {
                JSONObject word = words.getJSONObject(i);
                String nativeWord = word.getString("NativeWord");
                String nativeTranscription = word.getString("NativeTranscription");
                String foreignWord = word.getString("ForeignWord");
                String foreignTranscription = word.getString("ForeignTranscription");
            }


            String dbName = "../DB/SimpleLearnBD.db";
            Connection connection = null;
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
                while (resultSet.next()) {
                    String username = resultSet.getString("UserName");
                    System.out.print(username);
                }


                resultSet.close();
                statement.close();
                connection.close();

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }


        }
        catch (Exception e) {
            System.out.println("Error: "  + e.getMessage());
        }

    }
}