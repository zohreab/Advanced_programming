package app.view;

import app.Controller;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuHandler implements MenuHandler {


    @Override
    public boolean handle(Controller controller) throws IllegalActionException {
        String menuCommands = "Profile menu:\n" +
                "1.menu show-current\n" +
                "2.profile change --nickname|-n <nickname>\n" +
                "3.profile change --password|-p --current|-cu <current password> --new|-nw <new password>\n" +
                "4.menu exit\n"+
                "5.end program";
        System.out.println(menuCommands);
        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = ProfileCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Profile Menu");
        } else if ((matcher = ProfileCommand.CHANGE_NICKNAME.getStringMatcher(command)).find()) {
            controller.changeNickName(matcher.group("nickname"));
        } else if ((matcher = ProfileCommand.CHANGE_PASSWORD.getStringMatcher(command)).find()) {
            controller.changePassword(matcher.group("cp"), matcher.group("password"));
        } else if ((matcher = ProfileCommand.SHOW_USER.getStringMatcher(command)).find()) {
            System.out.println(controller.getCurrentUser());
        } else if ((matcher = ProfileCommand.EXIT.getStringMatcher(command)).find()) {
            controller.exit();
        }else if ((matcher=ProfileCommand.ENTER_MENU.getStringMatcher(command)).find()){
            System.out.println("menu navigation is not possible");
        }
        else if ((matcher = ProfileCommand.END_PROGRAM.getStringMatcher(command)).find()){
            return false;
        }else {
            System.out.println("invalid command");
        }
        return true;
    }

    enum ProfileCommand {
        SHOW_MENU("^menu show-current$"),
        CHANGE_NICKNAME("^profile change (?=.*(--nickname|-n) (?<nickname>\\S+))"),
        CHANGE_PASSWORD("^profile change (?=.*(--password|-p))(?=.*(--current|-cu) (?<cp>\\S+))(?=.*(--new|-nw) (?<password>\\S+))"),
        SHOW_USER("^profile show"),
        EXIT("^menu exit$"),
        END_PROGRAM("^end program$"),
        ENTER_MENU("^menu enter$");

        private final Pattern commandPattern;

        ProfileCommand(String commandPatternString) {

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
