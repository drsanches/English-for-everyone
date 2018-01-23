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

        for (int i = 1; i < args.length; i++)
            args[i] = Integer.toString(i);

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
        String[][] testarg1={{"test1","12345678","test1@h.ru"},{"test1","123","test1@h.ru"},{"test2","123","test1@h.ru"},{"test3","1234","test3.ru"}};
        String[][] testarg2={{"test1","12345678","TEST"},{"test1","12345678","0"},{"test2","12345678","1"},{"test1","123","1"},{"test4","123","1"},{"","","1"},{"test2","123","1"}};
        String[][] testarg4={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06"}};
        String[][] testarg3={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6","ENG","RUS"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5","RUS","ENG"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04","ENG","RUS"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06","RUS","ENG"}};
        String[][] testarg5={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06"}};
        String[][] testarg6={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6","22","2","4"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5","20","2","4"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04","22","1","5"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06","52","2","4"}};
        String[][] testarg7={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6","10"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5","11"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04","10"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04","11"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06","52"}};
        String[][] testarg8={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
        String[][] testarg9={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6","1"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5","1"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04","0"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06","88"}};
        String[][] testarg10={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
        String[][] testarg11={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06"}};
        String[][] testarg12={{"a3a1ee70-d82d-4789-8472-a45c4f6b6fd6"},{"2f292c44-6e44-40b1-ba56-af065ba3ead5"},{"9c095f76-eeb9-4e0f-982b-adea3cebed04"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06"}};
        String[][] testarg13={{"9073c4b6-05fb-4745-b285-3b8e5b0f6ce7"},{"d9d2d228-2802-48c9-894a-12f1380494eb"},{"601070cb-5c54-4eb8-9126-4f559c019721"},{"9c095f76-eeb9-4e0f-982b-adea3cebed06"}};

        switch (commandName) {
            case "reg":
                for(int i=0;i<testarg1.length;i++){
                    Registration.registration(testarg1[i]);}
                break;
            case "login":
                for(int i=0;i<testarg2.length;i++){
                Login.login(testarg2[i]);}
                break;
            case "setconfig":
                for(int i=0;i<testarg3.length;i++){
                Configurations.setConfigurations(testarg3[i]);}
                break;
            case "getinfo":
                for(int i=0;i<testarg4.length;i++){
                Information.getInformation(testarg4[i]);}
                break;
            case "gettimers":
                for(int i=0;i<testarg5.length;i++){
                Timers.getTimers(testarg5[i]);}
                break;
            case "settimer":
                for(int i=0;i<testarg6.length;i++){
                Timers.setTimer(testarg6[i]);}
                break;
            case "deltimer":
                for(int i=0;i<testarg7.length;i++){
                Timers.deleteTimer(testarg7[i]);}
                break;
            case "getlesson":
                for(int i=0;i<testarg8.length;i++){
                Lesson.getLesson(testarg8[i]);}
                break;
            case "gettest":
                for(int i=0;i<testarg9.length;i++){
                Test.getTest(testarg9[i]);}
                break;
            case "gradetestresults":
                for(int i=0;i<testarg10.length;i++){
                Test.gradeTestResults(testarg10[i]);}
                break;
            case "getstat":
                for(int i=0;i<testarg11.length;i++){
                Statistics.getStatistics(testarg11[i]);}
                break;
            case "logout":
                for(int i=0;i<testarg12.length;i++){
                Logout.logout(testarg12[i]);}
                break;
            case "unreg":
                for(int i=0;i<testarg13.length;i++){
                Unregistration.unregistration(testarg13[i]);}
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
