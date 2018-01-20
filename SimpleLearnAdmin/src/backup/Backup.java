package backup;


import java.io.*;

public class Backup {

    public static void backup(String args[]) {

    }

    private static void doBackup(String dbName, String backupName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File db = new File(dbName);
            File backup = new File(backupName);
            inputStream = new FileInputStream(db);
            outputStream = new FileOutputStream(backup);
            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            while (inputStream.read(buffer)> 0)
                outputStream.write(buffer, 0, length);
            inputStream.close();
            outputStream.close();
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}