package app.view;

import app.Controller;
import app.DataCenter;
import app.model.Battle.Battle;
import app.model.Battle.BattleCard;
import app.model.Battle.Phases;
import app.model.Battle.Player;
import app.model.IllegalActionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenuHandler implements MenuHandler {

    @Override
    public boolean handle(Controller controller) throws IllegalActionException {
        String menuCommands =
                "Duel menu:\n" +
                        "1.menu show-current\n" +
                        "2.select --monster|-mns <monster number>\n" +
                        "3.select --monster|-mns --opponent|-opp <monster number>\n" +
                        "4.select --spell|-spl <spell number>\n" +
                        "5.select --spell|-spl --opponent|-opp <spell number>\n" +
                        "6.select --field|-fld\n" +
                        "7.select --field|-fld --opponent|-opp\n" +
                        "8.select --hand|-hnd <number>\n" +
                        "9.select -d\n" +
                        "10.summon\n" +
                        "11.set monster\n" +
                        "12.set --position|-pos attack/defense\n" +
                        "13.set spell\n" +
                        "14.set trap\n" +
                        "15.flip-summon\n" +
                        "16.attack <number>\n" +
                        "17.attack direct\n" +
                        "18.activate effect\n" +
                        "19.show graveyard\n" +
                        "20.show graveyard --opponent|-opp\n" +
                        "21.next phase\n" +
                        "22.card show --selected|-slc\n" +
                        "23.surrender\n" +
                        "24.show battlefield\n" +
                        "25.menu exit";


        String command = UserCommandGetter.getUserCommand();
        Matcher matcher;
        if ((matcher = DuelCommand.SHOW_MENU.getStringMatcher(command)).find()) {
            System.out.println("Duel Menu");
            System.out.println(menuCommands);
        } else if ((matcher = DuelCommand.EXIT.getStringMatcher(command)).find()) {
            controller.exit();
        } else if ((matcher = DuelCommand.SELECT_MONSTER.getStringMatcher(command)).find()) {
            controller.selectMonster(Integer.parseInt(matcher.group("number")));
        } else if ((matcher = DuelCommand.SELECT_SPELL.getStringMatcher(command)).find()) {
            controller.selectSpell(Integer.parseInt(matcher.group("number")));
        } else if ((matcher = DuelCommand.MONSTER_OPP.getStringMatcher(command)).find()) {
            controller.oppMonster(Integer.parseInt(matcher.group("number")));
        } else if ((matcher = DuelCommand.SPELL_OPP.getStringMatcher(command)).find()) {
            controller.spellOpp(Integer.parseInt(matcher.group("number")));
        } else if ((matcher = DuelCommand.SELECT_FIELD.getStringMatcher(command)).find()) {
            controller.selectField();
        } else if ((matcher = DuelCommand.FIELD_OPP.getStringMatcher(command)).find()) {
            controller.oppField();
        } else if ((matcher = DuelCommand.SELECT_HAND.getStringMatcher(command)).find()) {
            controller.selectHand(Integer.parseInt(matcher.group("number")));
        } else if ((matcher = DuelCommand.DESELECT.getStringMatcher(command)).find()) {
            controller.deselect();
        } else if ((matcher = DuelCommand.SUMMON.getStringMatcher(command)).find()) {
            controller.summon();
        } else if ((matcher = DuelCommand.NEXT_PHASE.getStringMatcher(command)).find()) {
            Phases phase = controller.nextPhase();
            System.out.println("current phase : " + phase);
            if (phase == Phases.DRAW) {
                printBattle(DataCenter.getInstance().getCurrentBattle());
            }
        } else if ((matcher = DuelCommand.SET_MONSTER.getStringMatcher(command)).find()) {
            controller.setMonster();
        } else if ((matcher = DuelCommand.SET_SPELL.getStringMatcher(command)).find()) {
            controller.setSpell();
        } else if ((matcher = DuelCommand.SET_TRAP.getStringMatcher(command)).find()) {
            controller.setTrap();
        } else if ((matcher = DuelCommand.SET_POSITION.getStringMatcher(command)).find()) {
            controller.setPosition(matcher.group("position"));
        } else if ((matcher = DuelCommand.FLIP.getStringMatcher(command)).find()) {
            controller.flip();
        } else if ((matcher = DuelCommand.ATTACK.getStringMatcher(command)).find()) {
            controller.attack(matcher.group(1));
        } else if ((matcher = DuelCommand.DIRECT_ATTACK.getStringMatcher(command)).find()) {
            controller.directAttack();
        } else if ((matcher = DuelCommand.ACTIVE_EFFECT.getStringMatcher(command)).find()) {
            controller.activeEffect();
        } else if ((matcher = DuelCommand.SHOW_GRAVEYARD.getStringMatcher(command)).find()) {
            controller.showGraveyard();
        } else if ((matcher = DuelCommand.GRAVEYARD_OPP.getStringMatcher(command)).find()) {
            controller.opponentGraveYard();
        } else if ((matcher = DuelMenuHandler.DuelCommand.CARD_SHOW.getStringMatcher(command)).find()) {
            controller.cardShow(matcher.group(1).trim());
        } else if ((matcher = DuelCommand.showbattlefield.getStringMatcher(command)).find()) {
            printBattle(DataCenter.getInstance().getCurrentBattle());
        } else if ((matcher = DuelCommand.SHOW_SELECTED_CARD.getStringMatcher(command)).find()) {
            controller.showSelected();
        } else if ((matcher = DuelCommand.ENTER_MENU.getStringMatcher(command)).find()) {
            System.out.println("menu navigation is not possible");
        } else if ((matcher = DuelCommand.END_PROGRAM.getStringMatcher(command)).find()) {
            return false;
        } else if ((matcher = DuelCommand.SURRENDER.getStringMatcher(command)).find()) {
            controller.surrender();
        } else if ((matcher = DuelCommand.WIN_CHEAT.getStringMatcher(command)).find()) {
            controller.winByCheat(Integer.parseInt(matcher.group(1)));
        } else if ((matcher = DuelCommand.KILL_CHEAT.getStringMatcher(command)).find()) {
            controller.killSelected();
        } else if ((matcher = DuelCommand.INCREASE_LP.getStringMatcher(command)).find()) {
            controller.increaseLP(Integer.parseInt(matcher.group(1)));
        }else if ((matcher = DuelCommand.INCREASE_MONEY.getStringMatcher(command)).find()) {
            controller.increaseMoney(Integer.parseInt(matcher.group(1)));
        } else {
            System.out.println("invalid command");
        }
        return true;
    }

    public void printBattle(Battle battle) {
        int turn = battle.getTurn();
        Player opponent = battle.getPlayer((turn + 1) % 2);
        System.out.println(opponent.getUser().getNickname() + " : " + opponent.getLP());
        for (int i = 0; i < opponent.getHandCards().size(); i++) {
            System.out.print("\tc");
        }
        System.out.println();
        System.out.println(opponent.getRemainedCards().size());
        BattleCard[] spells = battle.getBattleField().getSpellZone((turn + 1) % 2);
        for (int i = 4; i >= 0; i--) {
            System.out.print("\t");
            if (spells[i] == null) {
                System.out.print("E");
            } else {
                System.out.print(spells[i].getState().getS());
            }
        }
        System.out.println();
        BattleCard[] monsters = battle.getBattleField().getMonsterZone((turn + 1) % 2);
        for (int i = 4; i >= 0; i--) {
            System.out.print("\t");
            if (monsters[i] == null) {
                System.out.print("E");
            } else {
                System.out.print(monsters[i].getState().getS());
            }
        }
        System.out.println();
        System.out.print(battle.getBattleField().getGraveYard((turn + 1) % 2).size() + "\t\t\t\t\t\t");
        if (battle.getBattleField().getFieldZone((turn + 1) % 2) == null)
            System.out.println("E");
        else
            System.out.println("O");
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        if (battle.getBattleField().getFieldZone(turn % 2) == null)
            System.out.print("E\t\t\t\t\t\t");
        else
            System.out.print("O\t\t\t\t\t\t");
        System.out.println(battle.getBattleField().getGraveYard(turn % 2).size());
        monsters = battle.getBattleField().getMonsterZone(turn % 2);
        for (int i = 0; i < 5; i++) {
            System.out.print("\t");
            if (monsters[i] == null) {
                System.out.print("E");
            } else {
                System.out.print(monsters[i].getState().getS());
            }
        }
        System.out.println();
        spells = battle.getBattleField().getSpellZone(turn % 2);
        for (int i = 0; i < 5; i++) {
            System.out.print("\t");
            if (spells[i] == null) {
                System.out.print("E");
            } else {
                System.out.print(spells[i].getState().getS());
            }
        }
        System.out.println();
        Player current = battle.getPlayer(turn % 2);
        System.out.println(" \t\t\t\t\t\t" + current.getRemainedCards().size());

        for (int i = 0; i < current.getHandCards().size(); i++) {
            System.out.print("\tc");
        }
        System.out.println();
        System.out.println(current.getUser().getNickname() + " : " + current.getLP());
    }

    enum DuelCommand {
        ACTIVE_EFFECT("^activate effect$"),
        ATTACK("^attack (\\d+)$"),
        DESELECT("^select -d$"),
        DIRECT_ATTACK("^attack direct$"),
        EXIT("^menu exit$"),
        FIELD_OPP("^select (?=.*(--field|-fld))(?=.*(--opponent|-opp))"),
        FLIP("^flip-summon$"),
        MONSTER_OPP("^select (?=.*(--monster|-mns))(?=.*(--opponent|-opp) (?<number>\\d+))"),
        SELECT_FIELD("^select (--field|-fld)"),
        SELECT_HAND("^select (?=.*(--hand|-hnd) (?<number>\\d+))"),
        SELECT_MONSTER("^select (?=.*(--monster|-mns) (?<number>\\d+))"),
        SELECT_SPELL("^select (?=.*(--spell|-spl) (?<number>\\d+))"),
        SET_MONSTER("^set monster$"),
        SET_SPELL("^set spell$"),
        SET_TRAP("^set trap$"),
        SET_POSITION("^set (?=.*(--position|-pos) (?<position>attack|defense))"),
        SHOW_SELECTED_CARD("^card show (--selected|-slc)"),
        SHOW_GRAVEYARD("^show graveyard$"),
        GRAVEYARD_OPP("^show graveyard (--opponent|-opp)"),
        SHOW_MENU("^menu show-current$"),
        SPELL_OPP("^select (?=.*(--spell|-spl))(?=.*(--opponent|-opp) (?<number>\\d+))"),
        SUMMON("^summon$"),
        NEXT_PHASE("^next phase"),
        SURRENDER("^surrender$"),
        END_PROGRAM("^end program$"),
        CARD_SHOW("^card show ((\\w+ *)+)$"),
        showbattlefield("^show battlefield$"),
        INCREASE_LP("^increase --LP (\\d+)$"),
        INCREASE_MONEY("^increase --money (\\d+)$"),
        WIN_CHEAT("^cheat win (\\d)$"),
        KILL_CHEAT("^cheat kill selected$"),
        ENTER_MENU("^menu enter$");


        private Pattern commandPattern;

        public Pattern getCommandPattern() {
            return commandPattern;
        }

        public Matcher getStringMatcher(String input) {
            return this.commandPattern.matcher(input);
        }

        DuelCommand(String commandPatternString) {
            this.commandPattern = Pattern.compile(commandPatternString);
        }
    }
}

