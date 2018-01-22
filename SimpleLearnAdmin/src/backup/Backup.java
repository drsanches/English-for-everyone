package backup;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Backup {

    public static void backup(String args[]) {
        if (args.length == 0) {
            System.out.print("DB pathname: ");
            String dbName = (new Scanner(System.in)).nextLine();
            System.out.print("Backup pathname: ");
            String backupName = (new Scanner(System.in)).nextLine();
            sqlBackup(dbName, backupName);
        }
        else if (args.length == 2) {
            String dbName = args[0];
            String backupName = args[1];
            sqlBackup(dbName, backupName);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void sqlBackup(String dbName, String backupName) {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM sqlite_master WHERE type = 'table';";
            ResultSet allTablesResultSet = statement.executeQuery(sql);
            FileWriter fileWriter = new FileWriter(backupName, false);

            ArrayList<String> tableNames = new ArrayList<String>();
            while (allTablesResultSet.next()) {
                if (!allTablesResultSet.getString("tbl_name").equals("sqlite_sequence")) {
                    String restoreQuery = allTablesResultSet.getString("sql") + ";";
                    fileWriter.write(restoreQuery + "\n\n");
                    String tableName = allTablesResultSet.getString("tbl_name");
                    tableNames.add(tableName);
                    System.out.println("Backup of structure of " + tableName + " was done");
                }
            }

            for (int i = 0; i < tableNames.size(); i++) {
                String tableName = tableNames.get(i);

                if (!tableName.equals("sqlite_sequence")) {
                    Statement st = connection.createStatement();
                    sql = "pragma table_info(" + tableName + ");";
                    ResultSet fieldsResultSet = st.executeQuery(sql);
                    String fieldNames = "";
                    int fieldsCount = 0;

                    while (fieldsResultSet.next()) {
                        String fieldName = fieldsResultSet.getString("name");
                        fieldNames += "\"" + fieldName + "\", ";
                        fieldsCount++;
                    }
                    fieldNames = fieldNames.substring(0, fieldNames.length() - 2);
                    fieldsResultSet.close();

                    sql = "SELECT * FROM " + tableName + ";";
                    ResultSet valuesResultSet = st.executeQuery(sql);

                    while (valuesResultSet.next()) {
                        String values = "";

                        for (int j = 1; j <= fieldsCount; j++) {
                            values += "\"" + valuesResultSet.getString(j) + "\", ";
                        }
                        values = values.substring(0, values.length() - 2);

                        String restoreQuery = "INSERT INTO " + tableName + "(" + fieldNames + ") VALUES(" + values + ");";
                        fileWriter.write(restoreQuery + "\n\n");
                    }
                    System.out.println("Backup of " + tableName + " was done");
                    valuesResultSet.close();
                }
            }

            allTablesResultSet.close();
            fileWriter.flush();
            System.out.println("Backup was done");
            statement.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    private static void fileBackup(String dbName, String backupName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File db = new File(dbName);
            File backup = new File(backupName);
            Files.copy(db.toPath(), backup.toPath());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}