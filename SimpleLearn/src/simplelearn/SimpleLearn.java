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
        String commandName = "";
        if (args.length > 0)
                commandName = args[0];
        
        //For debug:
        commandName = "reg";
        
        switch (commandName){
        case "reg":
                Registration.registration();
                break;
        case "login":
                Login.login();
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
                System.out.println("Uncorrect command. Use command help or /? to see help");
        }
    }
    public static void help(){
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
