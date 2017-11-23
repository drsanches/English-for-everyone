package login;

import com.mashape.unirest.http.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

            String status = jsonResponse.getBody().getObject().getString("Status");
            System.out.println(status);
        }
        catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
