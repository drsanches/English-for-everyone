package logout;

import java.util.Scanner;

public class Logout {

    public static void logout(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            serverLogout(sessionId);
        }
        else if (args.length == 1) {
            String sessionId = args[0];
            serverLogout(sessionId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverLogout(String sessionId) {
        //TODO: Write code
    }
}
