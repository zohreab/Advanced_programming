package app.view;

import app.Controller;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenuHandler implements MenuHandler {

    @Override
    public boolean handle(Controller controller) throws IllegalActionException {

        String menuCommands = "Shop menu:\n" +
                "1.menu show-current\n" +
                "2.shop buy <card name>\n" +
                "3.shop show --all|-al\n" +
                "4.show card <card name>\n" +
                "5.menu exit\n" +
                "6.end program";
        System.out.println(menuCommands);
        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = ShopCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Shop Menu");
        } else if ((matcher = ShopCommand.BUY_CARD.getStringMatcher(command)).find()) {
            controller.buyCard(matcher.group(1).trim());
        } else if ((matcher = ShopCommand.SHOW_SHOP.getStringMatcher(command)).find()) {
            controller.showShop();
        } else if ((matcher = ShopCommand.CARD_SHOW.getStringMatcher(command)).find()) {
            controller.cardShow(matcher.group(1).trim());
        } else if ((matcher = ShopCommand.EXIT.getStringMatcher(command)).find()) {
            controller.exit();
        } else if ((matcher = ShopCommand.ENTER_MENU.getStringMatcher(command)).find()) {
            System.out.println("menu navigation is not possible");
        } else if ((matcher = ShopCommand.END_PROGRAM.getStringMatcher(command)).find()) {
            return false;
        } else {
            System.out.println("invalid command");
        }
        return true;
    }


    enum ShopCommand {
        SHOW_MENU("^menu show-current$"),
        BUY_CARD("^shop buy ((\\w+ *)+)$"),
        CARD_SHOW("^card show ((\\w+ *)+)$"),
        SHOW_SHOP("^shop show (?=.*(--all|-al))"),
        EXIT("^menu exit$"),
        END_PROGRAM("^end program$"),
        ENTER_MENU("^menu enter$");

        private final Pattern commandPattern;

        public Pattern getCommandPattern() {

            return commandPattern;
        }

        public Matcher getStringMatcher(String input) {

            return this.commandPattern.matcher(input);
        }

        ShopCommand(String commandPatternString) {

            this.commandPattern = Pattern.compile(commandPatternString);
        }
    }
}

