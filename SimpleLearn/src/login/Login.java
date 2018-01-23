package login;

import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.ServerAddress;
import java.util.Scanner;


public class Login {
    
    public static void login(String[] args) {
        if (args.length == 0) {
            System.out.print("Username: ");
            String username = (new Scanner(System.in)).nextLine();
            System.out.print("Password: ");
            String password = (new Scanner(System.in)).nextLine();
            System.out.print("Remember me (true / false): ");
            boolean rememberMe = Boolean.parseBoolean((new Scanner(System.in)).nextLine());
            serverLogin(username, password, rememberMe);
        }
        else if (args.length == 3) {
            String username = args[0];
            String password = args[1];
            boolean rememberMe = Boolean.parseBoolean(args[2]);
            serverLogin(username, password, rememberMe);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverLogin(String username, String password, boolean rememberMe) {
        try {
            String url = ServerAddress.getAddress("login.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("Username=" + username + "&Password=" + password + "&RememberMe=" + rememberMe)
                    .asJson();

            printLoginResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printLoginResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);
            if (status.equals("Success")) {
                String sessionId = response.getString("SessionId");
                String startTime = response.getString("StartTime");
                String expiryPeriod = response.getString("ExpiryPeriod");
                System.out.println("Session id: " + sessionId);
                System.out.println("Start time: " + startTime);
                System.out.println("Expiry period: " + expiryPeriod);
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
