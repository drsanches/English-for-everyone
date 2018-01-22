package restore;

import dbaddress.DBAddress;

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
            sqlRestore(backupName);
        }
        else if (args.length == 1) {
            String backupName = args[0];
            sqlRestore(backupName);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void sqlRestore(String backupPath) {
        try {
            String dbPath = DBAddress.getAddress();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(backupPath)));
            String restoreSQL = "";
            String line;
            while ((line = reader.readLine()) != null)
                restoreSQL += line;
            reader.close();

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
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