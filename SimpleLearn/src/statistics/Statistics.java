package statistics;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
import java.util.Scanner;


public class Statistics {
    
    public static void getStatistics(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            serverGetStatistics(sessionId);
        }
        else if (args.length == 1) {
            String sessionId = args[0];
            serverGetStatistics(sessionId);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverGetStatistics(String sessionId) {
        try {
            String url = ServerAddress.getAddress("getstat.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId)
                    .asJson();

            printGetStatisticResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printGetStatisticResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);

            if (status.equals("Success")) {
                JSONArray statistics = response.getJSONArray("Statistics");
                System.out.println("Other users: \n");

                for (int i = 0; i < statistics.length(); i++)
                {
                    JSONObject timer = statistics.getJSONObject(i);
                    System.out.println("    Username: " + timer.getString("Username"));
                    System.out.println("    XP: " + timer.getInt("XP") + "\n");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
