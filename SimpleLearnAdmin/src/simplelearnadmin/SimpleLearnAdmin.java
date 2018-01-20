package simplelearnadmin;

import add.*;
import delete.*;
import backup.*;
import restore.*;

public class SimpleLearnAdmin {
    
    public static void main(String[] args){
//        Only for debug
        args = new String[2];
        args[0] = "backup";
        args[1] = "dictionary1.txt";

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
                    Delete.delete();
                    break;
            case "backup":
                    Backup.backup(commandArgs);
                    break;
            case "restore":
                    Restore.restore();
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
    
    public static void help() {
        System.out.println("\n----------SimpleLearn for admins----------");
        System.out.println("Available commands:");
        System.out.println("add - Add a new dictionary to the system;");
        System.out.println("del - Description of del;");
        System.out.println("backup - Description of backup;");
        System.out.println("restore - Description of restore.");
    }
}
