package lesson;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
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
        try {
            String url = ServerAddress.getAddress("getlesson.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId
                            + "&TestId=" + testId
                            + "&Type=" + type)
                    .asJson();

            printGetLessonResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printGetLessonResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);

            if (status.equals("Success")) {
                JSONArray lesson = response.getJSONArray("Lesson");
                System.out.println("Your lesson: ");

                for (int i = 0; i < lesson.length(); i++)
                {
                    JSONObject pair = lesson.getJSONObject(i);
                    System.out.println("Pair " + i + ": ");
                    System.out.println("    Word: " + pair.getString("NativeWord"));
                    System.out.println("    Translation: " + pair.getString("ForeignWord"));
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}