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
            serverGetLesson(sessionId);
        }
        else if (args.length == 1) {
            String sessionId = args[0];
            serverGetLesson(sessionId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetLesson(String sessionId) {
        try {
            String url = ServerAddress.getAddress("getlesson.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId)
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
                    System.out.println("    Word: " + pair.getString("NativeWordSpell"));
                    System.out.println("          " + pair.getString("NativeWordTranscription"));
                    System.out.println("    Translation: " + pair.getString("ForeignWordSpell"));
                    System.out.println("                 " + pair.getString("ForeignWordTranscription"));
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}