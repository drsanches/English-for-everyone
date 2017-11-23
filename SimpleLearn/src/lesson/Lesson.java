package lesson;

import java.util.Scanner;

public class Lesson {
    
    public static void getLesson(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Test id: ");
            int testId = Integer.parseInt((new Scanner(System.in)).nextLine());
            System.out.print("Type: ");
            String type = (new Scanner(System.in)).nextLine();
            serverGetLesson(sessionId, testId, type);
        }
        else if (args.length == 3) {
            String sessionId = args[0];
            int testId = Integer.parseInt(args[1]);
            String type = args[2];
            serverGetLesson(sessionId, testId, type);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetLesson(String sessionId, int testId, String type) {
        //TODO: Write code
    }
}