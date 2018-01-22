package delete;

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

    }
}