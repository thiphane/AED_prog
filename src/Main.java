import user.Command;

import java.util.Scanner;

public class Main {
    public static final String HELP_FORMAT = "%s - %s\n";


    public static final String EXIT_MESSAGE = "Bye!";
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Command command;
        do {
            String cmd = in.next();
            command = Command.getCommand(cmd);
            switch(command) {
                case Command.HELP -> {
                    for(Command c : Command.values()) {
                        if(!c.getDescription().isEmpty()) {
                            System.out.printf(HELP_FORMAT, c.name().toLowerCase(), c.getDescription());
                        }
                    }
                }
                case Command.EXIT -> {
                    System.out.println(EXIT_MESSAGE);
                }
            }
        } while(command != Command.EXIT);
    }
}
