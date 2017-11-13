package registration;

import java.util.Scanner;
import com.mashape.unirest.http.*;
//import org.json.*;



public class Registration {

    public static void registration() {
        System.out.print("username: ");
        String username = (new Scanner(System.in)).nextLine();
        System.out.print("Password: ");
        String password = (new Scanner(System.in)).nextLine();
        System.out.print("E-mail: ");
        String email = (new Scanner(System.in)).nextLine();
        serverRegistration(username, password, email);
    }

    private static void serverRegistration(String username, String password, String email) {
        String url = "http://localhost:8000/cgi-bin/reg.py";

        try {
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