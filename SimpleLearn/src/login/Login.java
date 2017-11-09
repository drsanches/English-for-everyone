package login;

import java.util.Scanner;

public class Login {
    
    public static void login()
    {
        System.out.print("username: ");
        String username = (new Scanner(System.in)).nextLine();
        System.out.print("Password: ");
        String password = (new Scanner(System.in)).nextLine();
        serverLogin(username, password);
    }
    private static void serverLogin(String username, String password)
    {
        System.out.println("Entered successfully.");
    }
}
