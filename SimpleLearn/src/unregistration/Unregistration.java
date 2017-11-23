package unregistration;

import java.util.Scanner;

public class Unregistration {

    public static void unregistration(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            serverUnregistration(sessionId);
        }
        else if (args.length == 1) {
            String sessionId = args[0];
            serverUnregistration(sessionId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverUnregistration(String sessionId) {
        //TODO: Write code
    }
}
