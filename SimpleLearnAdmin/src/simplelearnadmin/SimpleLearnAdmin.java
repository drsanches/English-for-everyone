package simplelearnadmin;

import add.*;
import delete.*;
import backup.*;
import restore.*;


public class SimpleLearnAdmin {
    
    public static void main(String[] args) {
        String commandName = "";
        if (args.length > 0)
                commandName = args[0];
        
        //For debug:
        commandName = "add";
        
        switch (commandName) {
            case "add":
                    Add.Add();
                    break;
            case "del":
                    Delete.Delete();
                    break;
            case "backup":
                    Backup.Backup();
                    break;
            case "restore":
                    Restore.Restore();
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
    
    public static void Help() {
        System.out.println("\n----------SimpleLearn for admins----------");
        System.out.println("Available commands:");
        System.out.println("add - Add a new dictionary to the system;");
        System.out.println("del - Description of del;");
        System.out.println("backup - Description of backup;");
        System.out.println("restore - Description of restore.");
    }
}
