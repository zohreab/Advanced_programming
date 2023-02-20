package app.view;

import app.Controller;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuHandler implements MenuHandler {

    @Override
    public boolean handle(Controller controller) throws IllegalActionException {
        String menuCommands = "Login menu:\n" +
                "1.user create --username|-u <username> --nickname|-n <nickname> --password|-p <password>\n" +
                "2.user login --username|-u <username> --password|-p <password>\n" +
                "3.menu show-current\n" +
                "4.menu enter\n"+
                "5.menu exit\n"+
                "6.end program";
        System.out.println(menuCommands);

        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = LoginCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Login Menu");
        } else if ((matcher = LoginCommand.EXIT.getStringMatcher(command)).find()) {
            return false;
        } else if ((matcher = LoginCommand.LOGIN_USER.getStringMatcher(command)).find()) {
            controller.login(matcher.group("username").trim(), matcher.group("password"));
        } else if ((matcher = LoginCommand.CREATE_USER.getStringMatcher(command)).find()) {
            controller.createUser(matcher.group("username").trim(), matcher.group("password"), matcher.group("nickname"));
        }else if ((matcher=LoginCommand.ENTER_MENU.getStringMatcher(command)).find()){
            System.out.println("menu navigation is not possible");
        }
        else if ((matcher = LoginCommand.END_PROGRAM.getStringMatcher(command)).find()){
            return false;
        }
        else {
            System.out.println("invalid command");
        }

        return true;
    }

    enum LoginCommand {
        SHOW_MENU("^menu show-current$"),
        CREATE_USER("^user create (?=.*(--username|-u) (?<username>(\\w+ *)+))(?=.*(--password|-p) (?<password>\\S+))(?=.*(--nickname|-n) (?<nickname>\\S+))"),
        LOGIN_USER("^user login (?=.*(--username|-u) (?<username>(\\w+ *)+))(?=.*(--password|-p) (?<password>\\S+))"),
        EXIT("^menu exit$"),
        END_PROGRAM("^end program$"),
        ENTER_MENU("^menu enter$");

        private Pattern commandPattern;

        public Pattern getCommandPattern() {
            return commandPattern;
        }

        public Matcher getStringMatcher(String input) {
            return this.commandPattern.matcher(input);
        }

        LoginCommand(String commandPatternString) {
            this.commandPattern = Pattern.compile(commandPatternString);
        }
    }

}

