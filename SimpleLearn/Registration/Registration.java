package Registration;

import java.util.Scanner;


public class Registration
{
    public static void Registration(String[] args)
    {
        System.out.print("username: ");
        String username = (new Scanner(System.in)).nextLine();
        System.out.print("Password: ");
        String password = (new Scanner(System.in)).nextLine();
        ServerRegistration(username, password);
    }
    private static void ServerRegistration(String username, String password)
    {
        System.out.println("Registration completed successfully.");
    }
}