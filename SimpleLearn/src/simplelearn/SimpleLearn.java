package simplelearn;

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


public class SimpleLearn {

    public static void main(String[] args) {

//        Only for debug
//        args = new String[2];
//        args[0] = "getstat";
//        args[1] = "35763f24-7266-4fe5-9770-eace223f03b0";

//        for (int i = 1; i < args.length; i++)
//            args[i] = Integer.toString(i);

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
                + "                   Arguments:   SessionId NativeLanguage ForeignLanguage;");
        System.out.println("getinfo          - Description: description;\n"
                + "                   Arguments:   SessionId;");
        System.out.println("gettimers        - Description: description;\n"
                + "                   Arguments:   SessionId;");
        System.out.println("settimer         - Description: description;\n"
                + "                   Arguments:   SessionId Time Days Count;");
        System.out.println("deltimer         - Description: description;\n"
                + "                   Arguments:   SessionId TimerId;");
        System.out.println("getlesson        - Description: description;\n"
                + "                   Arguments:   SessionId TestId Type;");
        System.out.println("gettest          - Description: description;\n"
                + "                   Arguments:   SessionId Type;");
        System.out.println("gradetestresults - Description: description;\n"
                + "                   Arguments:   SessionId TestId Answer1 Answer2 ... AnswerN;");
        System.out.println("getstat          - Description: description;\n"
                + "                   Arguments:   SessionId;");
        System.out.println("logout           - Description: description;\n"
                + "                   Arguments:   SessionId;");
        System.out.println("unreg            - Description: description;\n"
                + "                   Arguments:   SessionId;");
        System.out.println("help             - Description: shows this help.\n");
    }
}
