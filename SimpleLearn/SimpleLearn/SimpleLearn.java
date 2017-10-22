package SimpleLearn;

import Registration.*;
import Login.*;
import Configurations.*;
import Lesson.*;
import Test.*;
import Statistics.*;
import Logout.*;
import Unregistration.*;


public class SimpleLearn
{
    public static void main(String[] args) 
    {
        String commandName = "";
        if (args.length > 0)
                commandName = args[0];
        switch (commandName)
        {
        case "reg":
                Registration.Registration(args);
                break;
        case "login":
                Login.Login(args);
                break;
        case "config":
                Configurations.Configurations(args);
                break;
        case "learn":
                Lesson.Lesson(args);
                break;
        case "test":
                Test.Test(args);
                break;
        case "stat":
                Statistics.Statistics(args);
                break;
        case "logout":
                Logout.Logout(args);
                break;
        case "unreg":
                Unregistration.Unregistration(args);
                break;
        case "help":
                Help();
                break;
        case "/?":
                Help();
                break;

        default: 
                System.out.println("Uncorrect command. Use command help or /? to see help");
        }
    }
    public static void Help()
    {
        System.out.println("\n---------------SimpleLearn----------------");
        System.out.println("Available commands:");
        System.out.println("reg - Description of reg;");
        System.out.println("login - Description of login;");
        System.out.println("config - Description of config;");
        System.out.println("learn - Description of learn;");
        System.out.println("test - Description of test;");
        System.out.println("stat - Description of stat;");
        System.out.println("logout - Description of logout;");
        System.out.println("unreg - Description of unreg.");
    }
}