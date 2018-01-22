package restore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class Restore {

    public static void restore(String args[]) {
        if (args.length == 0) {
            System.out.print("Backup pathname: ");
            String backupName = (new Scanner(System.in)).nextLine();
            System.out.print("DB pathname: ");
            String dbName = (new Scanner(System.in)).nextLine();
            sqlRestore(backupName, dbName);
        }
        else if (args.length == 2) {
            String backupName = args[0];
            String dbName = args[1];
            sqlRestore(backupName, dbName);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void sqlRestore(String backupName, String dbName) {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(backupName)));
            String restoreSQL = "";
            String line;
            while ((line = reader.readLine()) != null)
                restoreSQL += line;
            reader.close();

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            Statement statement = connection.createStatement();

            String runnableSQL = "";
            while (restoreSQL.length() != 0) {
                int charIndex = restoreSQL.indexOf(';');
                if (charIndex > 0) {
                    runnableSQL = restoreSQL.substring(0, charIndex + 1);
                    System.out.println(runnableSQL);
                    statement.executeUpdate(runnableSQL);
                    System.out.println("Done");
                }
                restoreSQL = restoreSQL.substring(charIndex + 1, restoreSQL.length() - 1);
            }

//            statement.executeUpdate(restoreSQL);
            statement.close();
            connection.close();

            System.out.println("DB was restored");
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    private static void fileRestore(String backupName, String dbName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File db = new File(dbName);
            File backup = new File(backupName);
            Files.copy(backup.toPath(), db.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}