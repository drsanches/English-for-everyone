package simplelearnadmin;

import add.*;
import delete.*;
import backup.*;
import restore.*;

public class SimpleLearnAdmin {
    
    public static void main(String[] args){
//        Only for debug
//        args = new String[2];
//        args[0] = "restore";
//        args[1] = "backup1.log";

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
        System.out.println("add              - Description: description;\n"
                + "                   Arguments:   DictionaryPathname;");
        System.out.println("del              - Description: description;\n"
                + "                   Arguments:   DeletingWordSpell;");
        System.out.println("backup           - Description: description;\n"
                + "                   Arguments:   BackupPathname;");
        System.out.println("restore          - Description: description;\n"
                + "                   Arguments:   BackupPathname.");
    }
}
