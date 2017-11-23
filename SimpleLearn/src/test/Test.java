package test;

import java.util.Scanner;

public class Test {

    public static void getTest(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Type: ");
            String type = (new Scanner(System.in)).nextLine();
            serverGetTest(sessionId, type);
        } else if (args.length == 2) {
            String sessionId = args[0];
            String type = args[1];
            serverGetTest(sessionId, type);
        } else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetTest(String sessionId, String type) {
        //TODO: Write code
    }

    public static void gradeTestResults(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Count of answers: ");
            int countOfAnswers = Integer.parseInt((new Scanner(System.in)).nextLine());
            String[] answers = new String[countOfAnswers];

            for (int i = 0; i < countOfAnswers; i++) {
                System.out.print("Answer " + Integer.toString(i + 1) + ": ");
                answers[i] = (new Scanner(System.in)).nextLine();
            }

            serverGradeTestResults(sessionId, answers);
        } else if (args.length > 1) {
            String sessionId = args[0];
            String[] answers = new String[args.length - 1];

            for (int i = 0; i < answers.length; i++)
                answers[i] = args[i + 1];

            serverGradeTestResults(sessionId, answers);
        } else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGradeTestResults(String sessionId, String[] answers) {
        //TODO: Write code
    }
}