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
                JSONObject information = response.getJSONObject("Information");
                System.out.println("Username: " + information.getString("Username"));
                System.out.println("Native language: " + information.getString("NativeLanguage"));
                System.out.println("XP: " + information.getInt("XP"));

                JSONArray languages = information.getJSONArray("Languages");
                System.out.println("Learning languages:");

                for (int i = 0; i < languages.length(); i++)
                {
                    JSONObject language = languages.getJSONObject(i);
                    System.out.println("    " + language.getString("Language") + " - " + language.getInt("Level") + " level");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
