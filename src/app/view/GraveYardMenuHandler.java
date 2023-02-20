package app.view;

import app.Controller;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraveYardMenuHandler implements MenuHandler{

    @Override
    public boolean handle(Controller controller) throws IllegalActionException {
        String menuCommands ="graveYard menu commands:\n"
                +"1.back";
        System.out.println(menuCommands);

        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = GraveYardCommand.BACK.getStringMatcher(command)).find()){
            controller.back();
        }
        else {
            System.out.println("invalid command");
        }
        return true;
    }
    enum GraveYardCommand {
        BACK("^back$");

        private final Pattern commandPattern;

        public Pattern getCommandPattern() {

            return commandPattern;
        }

        public Matcher getStringMatcher(String input) {

            return this.commandPattern.matcher(input);
        }

        GraveYardCommand(String commandPatternString) {

            this.commandPattern = Pattern.compile(commandPatternString);
        }
    }
}
