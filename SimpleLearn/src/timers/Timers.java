package timers;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
import java.util.Scanner;


public class Timers {

    public static void setTimer(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Time: ");
            String time = (new Scanner(System.in)).nextLine();
            System.out.print("Days: ");
            String days = (new Scanner(System.in)).nextLine();
            System.out.print("Count of words: ");
            int count = Integer.parseInt((new Scanner(System.in)).nextLine());
            serverSetTimer(sessionId, time, days, count);
        }
        else if (args.length == 4) {
            String sessionId = args[0];
            String time = args[1];
            String days = args[2];
            int count = Integer.parseInt(args[3]);
            serverSetTimer(sessionId, time, days, count);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverSetTimer(String sessionId, String time, String days, int count) {
        try {
            String url = ServerAddress.getAddress("settimer.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId
                            + "&Time=" + time
                            + "&Days=" + days
                            + "&Count=" + count)
                    .asJson();

            printSetTimerResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printSetTimerResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
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
        try {
            String url = ServerAddress.getAddress("deltimer.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId
                            + "&TimerId=" + timerId)
                    .asJson();

            printDelTimerResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printDelTimerResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
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
        try {
            String url = ServerAddress.getAddress("gettimers.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId)
                    .asJson();

            printGetTimersResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printGetTimersResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);

            if (status.equals("Success")) {
                JSONArray timers = response.getJSONArray("Timers");
                System.out.println("Your timers: \n");

                for (int i = 0; i < timers.length(); i++)
                {
                    JSONObject timer = timers.getJSONObject(i);
                    System.out.println("    Timer id: " + timer.getInt("Id"));
                    System.out.println("    Time: " + timer.getString("Time"));
                    System.out.println("    Period: " + timer.getString("Period"));
                    System.out.println("    Count: " + timer.getInt("Count") + "\n");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
