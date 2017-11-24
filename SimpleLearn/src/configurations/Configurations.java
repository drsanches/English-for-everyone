package configurations;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
import java.util.Scanner;


public class Configurations {

    public static void setConfigurations(String[] args) {
        if (args.length == 0) {
            System.out.print("Session id: ");
            String sessionId = (new Scanner(System.in)).nextLine();
            System.out.print("Native language: ");
            String nativeLanguage = (new Scanner(System.in)).nextLine();
            System.out.print("Foreign language: ");
            String foreignLanguage = (new Scanner(System.in)).nextLine();
            System.out.print("Level: ");
            int level = Integer.parseInt((new Scanner(System.in)).nextLine());
            serverSetConfigurations(sessionId, nativeLanguage, foreignLanguage, level);
        }
        else if (args.length == 4) {
            String sessionId = args[0];
            String nativeLanguage = args[1];
            String foreignLanguage = args[2];
            int level = Integer.parseInt(args[3]);
            serverSetConfigurations(sessionId, nativeLanguage, foreignLanguage, level);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverSetConfigurations(String sessionId, String nativeLanguage, String foreignLanguage, int level) {
        try {
            String url = ServerAddress.getAddress("setconfig.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("SessionId=" + sessionId
                            + "&NativeLanguage=" + nativeLanguage
                            + "&ForeignLanguage=" + foreignLanguage
                            + "&Level=" + level)
                    .asJson();

            printSetConfigurationsResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printSetConfigurationsResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
