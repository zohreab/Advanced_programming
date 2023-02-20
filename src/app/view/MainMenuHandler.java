package app.view;

import app.Controller;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuHandler implements MenuHandler {

    public MainMenuHandler() {

    }

    @Override
    public boolean handle(Controller controller) throws IllegalActionException {
        String menuCommands = "Main menu:\n" +
                "1.menu show-current\n" +
                "2.menu enter <menu name> (Deck|Scoreboard|Profile|Shop)\n" +
                "3.user logout\n" +
                "4.menu exit\n"+
                "5.duel new --second-player|-sp <player2 username> --rounds|-rnd <1/3>\n"+
                "6.import card [card name]\n"+
                "7.export card [card name]\n"+
                "8.end program";
        System.out.println(menuCommands);
        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = MainCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Main Menu");
        } else if ((matcher = MainCommand.ENTER_MENU.getStringMatcher(command)).find()) {
            controller.enterMenu(matcher.group(1));
        } else if ((matcher = MainCommand.LOGOUT.getStringMatcher(command)).find()) {
            controller.logout();
        }else if((matcher = MainCommand.NEW_DUEL.getStringMatcher(command)).find()){
            controller.newDuel(matcher.group("username").trim(), Integer.parseInt(matcher.group("round")));
        }
        else if ((matcher = MainCommand.EXIT.getStringMatcher(command)).find()) {
            controller.exitMain();
        }   else if ((matcher = MainCommand.END_PROGRAM.getStringMatcher(command)).find()){
            return false;
        }
        else if ((matcher = MainCommand.EXPORT.getStringMatcher(command)).find()){
            controller.export(matcher.group(1).trim());
        }
        else if ((matcher = MainCommand.IMPORT.getStringMatcher(command)).find()){
            controller.importCard(matcher.group(1).trim());
        }
        else {
            System.out.println("invalid command");
        }
        return true;
    }

    enum MainCommand {
        SHOW_MENU("^menu show-current$"),
        ENTER_MENU("^menu enter (Deck|Scoreboard|Profile|Shop)$"),
        LOGOUT("^user logout$"),
        EXIT("^menu exit$"),
        NEW_DUEL("^duel new (?=.*(--second-player|-sp) (?<username>(\\w+ *)+))(?=.*(--rounds|-rnd) (?<round>\\d+))"),
        END_PROGRAM("^end program$"),
        IMPORT("^import card ((\\w+ *)+)$"),
        EXPORT("^export card ((\\w+ *)+)$");

        private final Pattern commandPattern;

        MainCommand(String commandPatternString) {

            this.commandPattern = Pattern.compile(commandPatternString);
        }

        public Pattern getCommandPattern() {

            return commandPattern;
        }

        public Matcher getStringMatcher(String input) {

            return this.commandPattern.matcher(input);
        }
    }

}
