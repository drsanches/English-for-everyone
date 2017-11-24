package registration;

import java.util.Scanner;
import com.mashape.unirest.http.*;
import org.json.*;
import serveraddress.*;


public class Registration {

    public static void registration(String[] args) {
        if (args.length == 0) {
            System.out.print("Username: ");
            String username = (new Scanner(System.in)).nextLine();
            System.out.print("Password: ");
            String password = (new Scanner(System.in)).nextLine();
            System.out.print("E-mail: ");
            String email = (new Scanner(System.in)).nextLine();
            serverRegistration(username, password, email);
        }
        else if (args.length == 3) {
            String username = args[0];
            String password = args[1];
            String email = args[2];
            serverRegistration(username, password, email);
        }
        else {
            System.out.println("Incorrect count of arguments.");
        }
    }

    private static void serverRegistration(String username, String password, String email) {
        try {
            String url = ServerAddress.getAddress("reg.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("Username=" + username + "&Password=" + password + "&E-mail=" + email)
                    .asJson();

            printRegistrationResponse(jsonResponse.getBody().getObject());
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static void printRegistrationResponse(JSONObject response) {
        try {
            String status = response.getString("Status");
            System.out.println("Status: " + status);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}