package Login;

import java.util.Scanner;


public class Login
{    
    public static void Login(String[] args)
    {
        String username = "";
        String password = "";
        switch (args.length)
        {
            case 1:
                System.out.print("username: ");
                username = (new Scanner(System.in)).nextLine();
                System.out.print("Password: ");
                password = (new Scanner(System.in)).nextLine();
                ServerLogin(username, password);
                break;
            case 2:
                System.out.print("Password: ");
                password = (new Scanner(System.in)).nextLine();
                ServerLogin(username, password);
                break;
            case 3:
                username = args[1];
                password = args[2];
                ServerLogin(username, password);
                break;
            default:
                System.out.println("Uncorrect format.");
                break;
        }
    }
    private static void ServerLogin(String username, String password)
    {
        System.out.println("Entered successfully.");
    }
}