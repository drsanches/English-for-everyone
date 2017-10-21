package SimpleLearn;

import Registration.*;
import Login.*;
import Configurations.*;
import Lesson.*;
import Test.*;
import Statistics.*;
import Logout.*;
import Unregistration.*;

import java.util.Scanner;


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
        default: 
                System.out.println("Uncorrect command.");
        }
    }
}
