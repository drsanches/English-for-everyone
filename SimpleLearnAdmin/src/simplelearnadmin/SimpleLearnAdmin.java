package simplelearnadmin;

import add.*;
import delete.*;
import backup.*;
import restore.*;

public class SimpleLearnAdmin {
    
    public static void main(String[] args){
//        Only for debug
        args = new String[3];
        args[0] = "add";
        args[1] = "dictionary2.txt";
        args[2] = "../DB/SimpleLearnBD2.db";

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
            case "add":
                    Add.add(commandArgs);
                    break;
            case "del":
                    Delete.delete(commandArgs);
                    break;
            case "backup":
                    Backup.backup(commandArgs);
                    break;
            case "restore":
                    Restore.restore(commandArgs);
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
        System.out.println("\n----------SimpleLearn for admins----------");
        System.out.println("Available commands:");
        System.out.println("add - Add a new dictionary to the system;");
        System.out.println("del - Description of del;");
        System.out.println("backup - Description of backup;");
        System.out.println("restore - Description of restore.");
    }
}
