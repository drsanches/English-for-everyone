package timers;

import java.util.Scanner;

public class Timers {

    public static void setTimer(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Time: ");
            String time = (new Scanner(System.in)).nextLine();
            System.out.print("Period: ");
            String period = (new Scanner(System.in)).nextLine();
            System.out.print("Count of words: ");
            int count = Integer.parseInt((new Scanner(System.in)).nextLine());
            serverSetTimer(sessionId, time, period, count);
        }
        else if (args.length == 4) {
            String sessionId = args[0];
            String time = args[1];
            String period = args[2];
            int count = Integer.parseInt(args[3]);
            serverSetTimer(sessionId, time, period, count);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverSetTimer(String sessionId, String time, String period, int count) {
        //TODO: Write code
    }

    public static void deleteTimer(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Timer id: ");
            int timerId = Integer.parseInt((new Scanner(System.in)).nextLine());
            serverDeleteTimer(sessionId, timerId);
        }
        else if (args.length == 2) {
            String sessionId = args[0];
            int timerId = Integer.parseInt(args[1]);
            serverDeleteTimer(sessionId, timerId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverDeleteTimer(String sessionId, int timerId) {
        //TODO: Write code
    }

    public static void getTimers(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            serverGetTimers(sessionId);
        }
        else if (args.length == 1) {
            String sessionId = args[0];
            serverGetTimers(sessionId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetTimers(String sessionId) {
        //TODO: Write code
    }
}
