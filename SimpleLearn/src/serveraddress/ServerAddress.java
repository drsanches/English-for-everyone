package serveraddress;

import java.nio.file.*;


public class ServerAddress {
    public static String getAddress(String scriptName) throws Exception
    {
        final String filename = "serverAddress.txt";
        String url = Files.readAllLines(Paths.get(filename)).get(0) + scriptName;
        return url;
    }
}
