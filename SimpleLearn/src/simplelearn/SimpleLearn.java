package simplelearn;

import information.Information;
import registration.*;
import login.*;
import configurations.*;
import information.*;
import timers.*;
import lesson.*;

import test.*;
import statistics.*;
import logout.*;
import unregistration.*;

import javax.swing.*;

public class SimpleLearn {

    public static void main(String[] args) {

//        Only for debug
        args = new String[1];
        args[0] = "help";
//        args[1] = "arg1";
//        args[2] = "arg2";
//        args[3] = "3";

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
            case "setconfig":
                Configurations.setConfigurations(commandArgs);
                break;
            case "getinfo":
                Information.getInformation(commandArgs);
                break;
            case "gettimers":
                Timers.getTimers(commandArgs);
                break;
            case "settimer":
                Timers.setTimer(commandArgs);
                break;
            case "deltimer":
                Timers.deleteTimer(commandArgs);
                break;
            case "getlesson":
                Lesson.getLesson(commandArgs);
                break;
            case "gettest":
                Test.getTest(commandArgs);
                break;
            case "gradetestresults":
                Test.gradeTestResults(commandArgs);
                break;
            case "getstat":
                Statistics.getStatistics(commandArgs);
                break;
            case "logout":
                Logout.logout(commandArgs);
                break;
            case "unreg":
                Unregistration.unregistration(commandArgs);
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
        System.out.println("reg              - Description: description;\n"
                         + "                   Arguments:   Username Password E-mail;");
        System.out.println("login            - Description: description;\n"
                         + "                   Arguments:   Username Password RememberMe(true / false);");
        System.out.println("setconfig        - Description: description;\n"
                         + "                   Arguments:   SessionId NativeLanguage ForeignLanguage Level;");
        System.out.println("getinfo          - Description: description;\n"
                         + "                   Arguments:   SessionId;");
        System.out.println("gettimers        - Description: description;\n"
                         + "                   Arguments:   SessionId;");
        System.out.println("settimer         - Description: description;\n"
                         + "                   Arguments:   SessionId Time Period Count;");
        System.out.println("deltimer         - Description: description;\n"
                         + "                   Arguments:   SessionId TimerId;");
        System.out.println("getlesson        - Description: description;\n"
                         + "                   Arguments:   SessionId TestId Type;");
        System.out.println("gettest          - Description: description;\n"
                         + "                   Arguments:   SessionId Type;");
        System.out.println("gradetestresults - Description: description;\n"
                         + "                   Arguments:   SessionId Answer1 Answer2 ... AnswerN;");
        System.out.println("getstat          - Description: description;\n"
                         + "                   Arguments:   SessionId;");
        System.out.println("logout           - Description: description;\n"
                         + "                   Arguments:   SessionId;");
        System.out.println("unreg            - Description: description;\n"
                         + "                   Arguments:   SessionId;");
        System.out.println("help             - Description: shows this help.\n");
    }
}
