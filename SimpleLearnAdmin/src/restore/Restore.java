package restore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Restore {
    final static  String DBNAME = "../DB/SimpleLearnBD.db";

    public static void restore(String args[]) {
        if (args.length == 0) {
            System.out.print("Backup pathname: ");
            String backupName = (new Scanner(System.in)).nextLine();
            doRestore(backupName);
        }
        else if (args.length == 1) {
            String backupName = args[0];
            doRestore(backupName);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void doRestore(String backupName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File db = new File(DBNAME);
            File backup = new File(backupName);
            Files.copy(backup.toPath(), db.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}