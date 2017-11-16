package registration;

import java.util.Scanner;
import com.mashape.unirest.http.*;
import serveraddress.*;



public class Registration {

    public static void registration(String[] args) {
        String username = "";
        String password = "";
        String email = "";
        if (args.length == 3) {
            username = args[0];
            password = args[1];
            email = args[2];
        }
        else {
            if (args.length != 0) {
                System.out.println("Incorrect count of arguments.");
            }
            System.out.print("Username: ");
            username = (new Scanner(System.in)).nextLine();
            System.out.print("Password: ");
            password = (new Scanner(System.in)).nextLine();
            System.out.print("E-mail: ");
            email = (new Scanner(System.in)).nextLine();
        }
        serverRegistration(username, password, email);
    }

    private static void serverRegistration(String username, String password, String email) {
        try {
            String url = ServerAddress.getAddress("reg.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("Username=" + username + "&Password=" + password + "&E-mail=" + email)
                    .asJson();

            String status = jsonResponse.getBody().getObject().getString("Status");
            System.out.println(status);
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}