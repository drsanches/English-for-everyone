package information;

import java.util.Scanner;

public class Information {

    public static void getInformation(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            serverGetInformation(sessionId);
        }
        else if (args.length == 1) {
            String sessionId = args[0];
            serverGetInformation(sessionId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetInformation(String sessionId)
    {
        //TODO: Write code
    }
}
