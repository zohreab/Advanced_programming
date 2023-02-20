package app.view;

import app.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardMenuHandler implements MenuHandler {

    @Override
    public boolean handle(Controller controller) {
        String menuCommands = "Scoreboard menu:\n" +
                "1.menu show-current\n" +
                "2.scoreboard show\n" +
                "3.menu exit\n"+
                "4.end program";
        System.out.println(menuCommands);
        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = ScoreboardCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Scoreboard Menu");
        } else if ((matcher = ScoreboardCommand.SHOW.getStringMatcher(command)).find()) {
            controller.showScoreboard();
        } else if ((matcher = ScoreboardCommand.EXIT.getStringMatcher(command)).find()) {
            controller.exit();
        }  else if ((matcher=ScoreboardCommand.ENTER_MENU.getStringMatcher(command)).find()){
            System.out.println("menu navigation is not possible");
        }else if ((matcher = ScoreboardCommand.END_PROGRAM.getStringMatcher(command)).find()){
            return false;
        }else {
            System.out.println("invalid command");
        }
        return true;
    }

    enum ScoreboardCommand {
        SHOW_MENU("^menu show-current$"),
        SHOW("^scoreboard show$"),
        EXIT("^menu exit$"),
        END_PROGRAM("^end program$"),
        ENTER_MENU("^menu enter$");

        private final Pattern commandPattern;

        ScoreboardCommand(String commandPatternString) {

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
