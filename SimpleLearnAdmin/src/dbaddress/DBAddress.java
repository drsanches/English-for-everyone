package dbaddress;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DBAddress {
    public static String getAddress() throws Exception
    {
        final String filename = "DBAddress.txt";
        String path = Files.readAllLines(Paths.get(filename)).get(0);
        return path;
    }
}
