package simplelearn;

import registration.*;
import login.*;
import configurations.*;
import lesson.*;
import test.*;
import statistics.*;
import logout.*;
import unregistration.*;

public class SimpleLearn {

    public static void main(String[] args) {

        //Only for debug
        args = new String[4];
        args[0] = "login";
        args[1] = "arg1";
        args[2] = "arg2";
        args[3] = "arg3";

        String commandName = "";
        String[] commandArgs = new String[0];

        if (args.length > 0) {
            commandName = args[0];
            if (args.length > 1) {
                commandArgs = new String[args.length - 1];
                for (int i = 0; i < commandArgs.length; i++)
                    commandArgs[i] = args[i + 1];
            }
        }

        switch (commandName) {
            case "reg":
                Registration.registration(commandArgs);
                break;
            case "login":
                Login.login(commandArgs);
                break;
            case "config":
                Configurations.configurations();
                break;
            case "learn":
                Lesson.lesson();
                break;
            case "test":
                Test.test();
                break;
            case "stat":
                Statistics.statistics();
                break;
            case "logout":
                Logout.logout();
                break;
            case "unreg":
                Unregistration.unregistration();
                break;
            case "help":
                help();
                break;
            case "/?":
                help();
                break;

            default:
                System.out.println("Incorrect command. Use command help or /? to see help");
        }
    }

    public static void help() {
        System.out.println("\n---------------SimpleLearn----------------");
        System.out.println("Available commands:");
        System.out.println("reg    - Description: description;\n"
                         + "         Arguments: username password e-mail;");
        System.out.println("login  - Description: description;\n"
                         + "         Arguments: username password [remember me = false];");
        System.out.println("config - Description: description;");
        System.out.println("learn  - Description: description;");
        System.out.println("test   - Description: description;");
        System.out.println("stat   - Description: description;");
        System.out.println("logout - Description: description;");
        System.out.println("unreg  - Description: description;");
        System.out.println("help   - Description: description.");
    }
}
