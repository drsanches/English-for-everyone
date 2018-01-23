package information;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
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

    private static void serverGetInformation(String sessionId) {
        try {
            String url = ServerAddress.getAddress("getinfo.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId)
                    .asJson();

            printGetInformationResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printGetInformationResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);

            if (status.equals("Success")) {
                JSONArray information = response.getJSONArray("Information");

                for (int i = 0; i < information.length(); i++) {
                    JSONObject inf = information.getJSONObject(i);
                    System.out.println("Username: " + inf.getString("Username"));
                    System.out.println("Native language: " + inf.getString("NativeLanguage"));
                    System.out.println("XP: " + inf.getInt("XP"));

                    JSONArray languages = inf.getJSONArray("Languages");
                    System.out.println("Learning languages:");

                    for (int j = 0; j < languages.length(); j++)
                    {
                        JSONObject language = languages.getJSONObject(j);
                        System.out.println("    " + language.getString("Language") + " - " + language.getString("Level") + " level");
                    }
                    System.out.println();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
