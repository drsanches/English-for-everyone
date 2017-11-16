package login;

import com.mashape.unirest.http.*;
import serveraddress.ServerAddress;

import java.util.Scanner;

public class Login {
    
    public static void login(String[] args) {

        String username = "";
        String password = "";
        boolean rememberMe = false;

        if (args.length >= 2) {
            username = args[0];
            password = args[1];
            if (args.length == 3) {
                rememberMe = Boolean.parseBoolean(args[2]);
            }
        }
        else {
            if (args.length != 0) {
                System.out.println("Incorrect count of arguments.");
            }
            System.out.print("username: ");
            username = (new Scanner(System.in)).nextLine();
            System.out.print("Password: ");
            password = (new Scanner(System.in)).nextLine();
            System.out.print("Remember me (true / false): ");
            rememberMe = Boolean.parseBoolean((new Scanner(System.in)).nextLine());
        }

        serverLogin(username, password, rememberMe);
    }
    
    private static void serverLogin(String username, String password, boolean rememberMe) {
        try {
            String url = ServerAddress.getAddress("login.py");
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .body("Username=" + username + "&Password=" + password + "&RememberMe=" + rememberMe)
                    .asJson();

            String status = jsonResponse.getBody().getObject().getString("Status");
            System.out.println(status);
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
