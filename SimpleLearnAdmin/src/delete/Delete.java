package delete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Delete {
    
    public static void delete(String[] args) {
        if (args.length == 0) {
            System.out.print("Deleting word: ");
            String deletingWord = (new Scanner(System.in)).nextLine();
            System.out.print("Path to the DB: ");
            String dbPath = (new Scanner(System.in)).nextLine();
            deleteWord(deletingWord, dbPath);
        } else if (args.length == 2) {
            String deletingWord = args[0];
            String dbPath = args[1];
            deleteWord(deletingWord, dbPath);
        } else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    public static void deleteWord(String deletingWord, String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            PreparedStatement pstmt = null;

            String sql = "Select ID FROM  Pair\n" +
                    "left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID \n" +
                    "WHERE Spell = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, deletingWord);
            ResultSet allPairsResultSet = pstmt.executeQuery();

            System.out.println("Words: ");

            while (allPairsResultSet.next()) {
                String pairId = allPairsResultSet.getString(1);

                sql = "Select ID, Spell, Phonetic FROM  Pair\n" +
                        "left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID \n" +
                        "WHERE ID = ?;";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, pairId);
                ResultSet pairResultSet = pstmt.executeQuery();
                String word = pairResultSet.getString(2);
                String transcription = pairResultSet.getString(3);
                System.out.println(pairId + ": " + word + " - " + transcription);
            }

            System.out.print("Select the number of the word to be deleted: ");
            int deletingWordNumber = Integer.parseInt((new Scanner(System.in)).nextLine());

            sql = "DELETE FROM WordToLearn WHERE PairID = ?;\n" +
                    "DELETE FROM WordToRepeat WHERE PairID = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, deletingWordNumber);
            pstmt.setInt(2, deletingWordNumber);
            pstmt.executeUpdate();

            sql = "Select Word1ID, Word2ID FROM  Pair\n" +
                    "left join Words On Pair.Word1ID = Words.WordID OR Pair.Word2ID = Words.WordID \n" +
                    "WHERE ID = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, deletingWordNumber);
            ResultSet pairResultSet = pstmt.executeQuery();
            String word1Id = pairResultSet.getString(1);
            String word2Id = pairResultSet.getString(2);

            sql = "DELETE FROM Words WHERE WordID = ?;\n" +
                    "DELETE FROM Words WHERE WordID = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, word1Id);
            pstmt.setString(2, word2Id);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
}