package registration;

import com.mashape.unirest.http.*;
import java.util.Scanner;
import org.json.*;

public class Registration {
    
    public static void registration()
    {
        System.out.print("username: ");
        String username = (new Scanner(System.in)).nextLine();
        System.out.print("Password: ");
        String password = (new Scanner(System.in)).nextLine();
        System.out.print("E-mail: ");
        String email = (new Scanner(System.in)).nextLine();
        serverRegistration(username, password, email);
    }
    private static void serverRegistration(String username, String password, String email)
    {
        String url = "http://www.something.com";
        
        //TODO: Test it!
        try{
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
                    .header("Accept", "application/json")
                    .routeParam("Username", username)
                    .routeParam("Password", password)
                    .routeParam("E-mail", email)
                    .asJson();
                    
            String status = jsonResponse.getBody().getObject().getString("Status");
            System.out.println(status);
        }
        catch(Exception e){
            System.out.println("Error: " + e.toString());
        }
    }
}
