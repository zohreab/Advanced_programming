package app.view;

import app.Controller;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenuHandler implements MenuHandler {

    @Override
    public boolean handle(Controller controller) throws IllegalActionException {
        String menuCommands = "Deck menu:\n" +
                "1.menu show-current\n" +
                "2.deck create <deck name>\n" +
                "3.deck delete <deck name>\n" +
                "4.deck set-activate <deck name>\n" +
                "5.deck add-card --card|-c <card name> --deck|-dc <deck name> --side|-s(optional)\n" +
                "6.deck rm-card --card|-c <card name> --deck|-dc <deck name> --side|-s(optional)\n" +
                "7.deck show --all\n" +
                "8.deck show --deck-name|-dn <deck name> --side(Opt)\n" +
                "9.deck show --cards|cds\n" +
                "10.menu exit\n" +
                "11.end program";
        System.out.println(menuCommands);
        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = DeckCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Deck Menu");
        } else if ((matcher = DeckCommand.CREATE_DECK.getStringMatcher(command)).find()) {
            controller.createDeck(matcher.group(1).trim());
        } else if ((matcher = DeckCommand.DELETE_DECK.getStringMatcher(command)).find()) {
            controller.deleteDeck(matcher.group(1).trim());
        } else if ((matcher = DeckCommand.SET_ACTIVE.getStringMatcher(command)).find()) {
            controller.setActive(matcher.group(1).trim());
        } else if ((matcher = DeckCommand.ADD_CARD.getStringMatcher(command)).find()) {
            boolean isSide = command.contains("--side")||command.contains("-s");
            controller.addCard(matcher.group("cardName").trim(), matcher.group("deckName").trim(), isSide);
        } else if ((matcher = DeckCommand.REMOVE_CARD.getStringMatcher(command)).find()) {
            boolean isSide = command.contains("--side")||command.contains("-s");
            controller.removeCard(matcher.group("cardName").trim(), matcher.group("deckName").trim(), isSide);
        } else if ((matcher = DeckCommand.SHOW_ALL.getStringMatcher(command)).find()) {
            controller.showAll();
        } else if ((matcher = DeckCommand.SHOW_DECK.getStringMatcher(command)).find()) {
            boolean isSide = command.contains("--side")||command.contains("-s");
            controller.showDeck(matcher.group("deckName").trim(), isSide);
        } else if ((matcher = DeckCommand.SHOW_CARD.getStringMatcher(command)).find()) {
            controller.showCardDeckMenu();
        }else if ((matcher = DeckMenuHandler.DeckCommand.CARD_SHOW.getStringMatcher(command)).find()) {
            controller.cardShow(matcher.group(1).trim());
        } else if ((matcher = DeckCommand.EXIT.getStringMatcher(command)).find()) {
            controller.exit();
        } else if ((matcher = DeckCommand.ENTER_MENU.getStringMatcher(command)).find()) {
            System.out.println("menu navigation is not possible");
        } else if ((matcher = DeckCommand.END_PROGRAM.getStringMatcher(command)).find()) {
            return false;
        } else {
            System.out.println("invalid command");
        }
        return true;
    }

    enum DeckCommand {
        SHOW_MENU("^menu show-current$"),
        CREATE_DECK("^deck create ((\\w+ *)+)$"),
        DELETE_DECK("^deck delete ((\\w+ *)+)$"),
        SET_ACTIVE("^deck set-activate ((\\w+ *)+)$"),
        ADD_CARD("^deck add-card (?=.*(--card|-c) (?<cardName>(\\w+ *)+))(?=.*(--deck|-dc) (?<deckName>(\\w+ *)+))"),
        REMOVE_CARD("^deck rm-card  (?=.*(--card|-c) (?<cardName>(\\w+ *)+))(?=.*(--deck|-dc) (?<deckName>(\\w+ *)+))"),
        SHOW_ALL("^deck show (?=.*(--all|-al))"),
        SHOW_DECK("^deck show (?=.*(--deck-name|-dn) (?<deckName>(\\w+ *)+))"),
        SHOW_CARD("^deck show (?=.*(--cards|-cds))"),
        EXIT("^menu exit$"),
        END_PROGRAM("^end program$"),
        CARD_SHOW("^card show ((\\w+ *)+)$"),
        ENTER_MENU("^menu enter$");

        private Pattern commandPattern;
        public Pattern getCommandPattern() {

            return commandPattern;
        }

        public Matcher getStringMatcher(String input) {

            return this.commandPattern.matcher(input);
        }

        DeckCommand(String commandPatternString) {

            this.commandPattern = Pattern.compile(commandPatternString);
        }
    }
}

