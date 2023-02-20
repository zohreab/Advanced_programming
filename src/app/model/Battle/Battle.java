package app.model.Battle;

import app.model.CardTypes.CardType;
import app.model.Cards.Card;
import app.model.Cards.Monster;
import app.model.IllegalActionException;
import app.model.User;

import java.util.ArrayList;
import java.util.Random;

public class Battle {
    private final int rounds;
    private int currentRound;
    private Player player0;
    private Player player1;
    private BattleField battleField;
    private int selectedAddress;
    private SelectType selectType;
    private Phases currentPhase;
    private boolean hasSetOrSummon;
    private int turn;
    private BattleCard battleCard;

    public Battle(User currentUser, User secondPlayer, int round) {
        Random random = new Random();
        if (random.nextInt() % 2 == 0) {
            player0 = new Player(8000, currentUser);
            player1 = new Player(8000, secondPlayer);
        } else {
            player1 = new Player(8000, currentUser);
            player0 = new Player(8000, secondPlayer);
        }
        player0.draw();
        this.rounds = round;
        this.battleField = new BattleField();
        currentPhase = Phases.DRAW;
    }

    public void select(int selectedAddress, SelectType selectType) {
        this.selectType = selectType;
        this.selectedAddress = selectedAddress;
    }

    public Player getPlayer(int i) {
        if (i == 0)
            return player0;
        return player1;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player getCurrentPlayer() {
        return getPlayer(turn % 2);
    }

    public void deselect() throws IllegalActionException {
        if (selectType == null) {
            throw new IllegalActionException("no card is selected yet");
        }
        selectType = null;
    }

    public void summon() throws IllegalActionException {
        if (selectType == null) {
            throw new IllegalActionException("no card is selected yet");
        }
        if (selectType != SelectType.SELECT_HAND) {
            throw new IllegalActionException("you can’t summon this card");
        }
        ArrayList<BattleCard> handCards = getCurrentPlayer().getHandCards();
        BattleCard selectedCard = handCards.get(selectedAddress);
        if (selectedCard.getCard().getType() != CardType.MONSTER) {
            throw new IllegalActionException("you can’t summon this card");
        }
        if (currentPhase != Phases.MAIN_PHASE_1 && currentPhase != Phases.MAIN_PHASE_2) {
            throw new IllegalActionException("action not allowed in this phase");
        }
        BattleCard[] monsterZone = battleField.getMonsterZone(turn % 2);
        if (!hasCapacity(monsterZone)) {
            throw new IllegalActionException("monster card zone is full");
        }
        if (hasSetOrSummon) {
            throw new IllegalActionException("you already summoned/set on this turn");
        }
        Monster card = ((Monster) selectedCard.getCard());
        if (card.getLevel() <= 4) {

        } else if (card.getLevel() <= 6) {

        } else {

        }
        for (int i = 0; i < 5; i++) {
            if (monsterZone[i] == null) {
                monsterZone[i] = selectedCard;
                handCards.remove(selectedAddress);
                break;
            }
        }
        selectedCard.setState(State.OFFENSIVE_OCCUPIED);

        hasSetOrSummon = true;
        deselect();
    }

    private boolean hasCapacity(BattleCard[] zone) {
        for (int i = 0; i < 5; i++) {
            if (zone[i] == null)
                return true;

        }
        return false;
    }

    public Phases getCurrentPhase() {
        return currentPhase;
    }

    public void winGame(int i) {
        int scoreRewardAmount = 1000 * rounds;
        if (i == 0) {
            player0.getUser().increaseScore(scoreRewardAmount);
            player0.getUser().increaseBalance(scoreRewardAmount + (player0.getMaxLPReward() * rounds));
            player1.getUser().increaseBalance(100 * rounds);
        } else {
            player1.getUser().increaseScore(scoreRewardAmount);
            player1.getUser().increaseBalance(scoreRewardAmount + (player1.getMaxLPReward() * rounds));
            player0.getUser().increaseBalance(100 * rounds);
        }
    }

    public void winRound(int i) {
        if (i == 0) {
        } else {
        }
        currentRound++;
    }

    public Phases nextPhase() {
        switch (currentPhase) {
            case DRAW:
                currentPhase = Phases.STANDBY;
                break;
            case STANDBY:
                currentPhase = Phases.MAIN_PHASE_1;
                break;
            case MAIN_PHASE_1:
                currentPhase = Phases.BATTLE_PHASE;
                break;
            case BATTLE_PHASE:
                currentPhase = Phases.MAIN_PHASE_2;
                break;
            case MAIN_PHASE_2:
                currentPhase = Phases.END_PHASE;
                break;
            case END_PHASE:
                currentPhase = Phases.DRAW;
                turn++;
                hasSetOrSummon = false;
                if (!getCurrentPlayer().draw()) {
                    winRound((turn + 1) % 2);
                }
                break;
        }
        return currentPhase;
    }

    public SelectType getSelectType() {
        return selectType;
    }

    public BattleCard getSelected() throws IllegalActionException {
        if (selectType == SelectType.SELECT_MONSTER) {
            BattleCard[] monsterZone = battleField.getMonsterZone(turn % 2);
            return monsterZone[selectedAddress];
        }
        if (selectType == SelectType.MONSTER_OPPONENT) {
            BattleCard[] monsterOpponent = battleField.getMonsterZone((turn + 1) % 2);
            if (monsterOpponent[selectedAddress].getState() == State.DEFENSIVE_HIDDEN) {
                throw new IllegalActionException("card is not visible");
            }
            return monsterOpponent[selectedAddress];
        }
        if (selectType == SelectType.SELECT_SPELL) {
            BattleCard[] spellZone = battleField.getSpellZone(turn % 2);
            return spellZone[selectedAddress];
        }
        if (selectType == SelectType.SPELL_OPPONENT) {
            BattleCard[] spellOpponent = battleField.getSpellZone((turn + 1) % 2);
            if (spellOpponent[selectedAddress].getState() == State.HIDDEN) {
                throw new IllegalActionException("card is not visible");
            }
            return spellOpponent[selectedAddress];
        }
        if (selectType == SelectType.SELECT_FIELLD) {
            return battleField.getFieldZone(turn % 2);
        }
        if (selectType == SelectType.FIELD_OPPONENT) {
            return battleField.getFieldZone((turn + 1) % 2);
        }
        if (selectType == SelectType.SELECT_HAND) {
            BattleCard[] handCard = getCurrentPlayer().getHandCards().toArray(new BattleCard[0]);
            return handCard[selectedAddress];
        }
        throw new IllegalActionException("no card is selected yet");
    }

    public void changeMonsterState(String state) throws IllegalActionException {
        BattleCard selectedCard = getSelected();
        if (selectType != SelectType.SELECT_MONSTER) {
            throw new IllegalActionException("you can’t change this card position");
        }

        if (currentPhase != Phases.MAIN_PHASE_1 && currentPhase != Phases.MAIN_PHASE_2) {
            throw new IllegalActionException("you can’t do this action in this phase");
        }
        if (selectedCard.isHasStateChanged()) {
            throw new IllegalActionException("you already changed this card position in this turn");
        }
        if (state.equals("attack") && selectedCard.getState() != State.DEFENSIVE_OCCUPIED) {

            throw new IllegalActionException("this card is already in the wanted position");
        }
        if (state.equals("defense") && selectedCard.getState() != State.OFFENSIVE_OCCUPIED) {
            throw new IllegalActionException("this card is already in the wanted position");
        }

        selectedCard.changeState(selectedCard);
        selectedCard.setHasStateChanged(true);
    }

    public void setMonster() throws IllegalActionException {
        if (selectType == null) {
            throw new IllegalActionException("no card is selected yet");
        }
        if (selectType != SelectType.SELECT_HAND) {
            throw new IllegalActionException("you can’t set this card");
        }

        if (currentPhase != Phases.MAIN_PHASE_1 && currentPhase != Phases.MAIN_PHASE_2) {
            throw new IllegalActionException("you can’t do this action in this phase");
        }
        ArrayList<BattleCard> handCards = getCurrentPlayer().getHandCards();
        BattleCard selectedCard = handCards.get(selectedAddress);
        if (selectedCard.getCard().getType() != CardType.MONSTER) {
            throw new IllegalActionException("you can’t set this card");
        }
        BattleCard[] monsterZone = battleField.getMonsterZone(turn % 2);
        if (!hasCapacity(monsterZone)) {
            throw new IllegalActionException("monster card zone is full");
        }
        if (hasSetOrSummon) {
            throw new IllegalActionException("you already summoned/set on this turn");
        }

        for (int i = 0; i < 5; i++) {
            if (monsterZone[i] == null) {
                monsterZone[i] = selectedCard;
                handCards.remove(selectedAddress);
                break;
            }

        }
        selectedCard.setState(State.DEFENSIVE_HIDDEN);
        hasSetOrSummon = true;
        deselect();
    }

    public void setSpell() throws IllegalActionException {
        if (selectType == null) {
            throw new IllegalActionException("no card is selected yet");
        }
        if (selectType != SelectType.SELECT_HAND) {
            throw new IllegalActionException("you can’t set this card");
        }

        if (currentPhase != Phases.MAIN_PHASE_1 && currentPhase != Phases.MAIN_PHASE_2) {
            throw new IllegalActionException("you can’t do this action in this phase");
        }
        ArrayList<BattleCard> handCards = getCurrentPlayer().getHandCards();
        BattleCard selectedCard = handCards.get(selectedAddress);
        if (selectedCard.getCard().getType() != CardType.SPELL) {
            throw new IllegalActionException("you can’t set this card");
        }
        BattleCard[] spellZone = battleField.getSpellZone(turn % 2);
        if (!hasCapacity(spellZone)) {
            throw new IllegalActionException("spell card zone is full");
        }
        if (hasSetOrSummon) {
            throw new IllegalActionException("you already summoned/set on this turn");
        }

        for (int i = 0; i < 5; i++) {
            if (spellZone[i] == null) {
                spellZone[i] = selectedCard;
                handCards.remove(selectedAddress);
                break;
            }

        }
        selectedCard.setState(State.HIDDEN);
        hasSetOrSummon = true;
        deselect();
    }

    public void setTrap() throws IllegalActionException {
        if (selectType == null) {
            throw new IllegalActionException("no card is selected yet");
        }
        if (selectType != SelectType.SELECT_HAND) {
            throw new IllegalActionException("you can’t set this card");
        }

        if (currentPhase != Phases.MAIN_PHASE_1 && currentPhase != Phases.MAIN_PHASE_2) {
            throw new IllegalActionException("you can’t do this action in this phase");
        }
        ArrayList<BattleCard> handCards = getCurrentPlayer().getHandCards();
        BattleCard selectedCard = handCards.get(selectedAddress);
        if (selectedCard.getCard().getType() != CardType.TRAP) {
            throw new IllegalActionException("you can’t set this card");
        }
        BattleCard[] spellZone = battleField.getSpellZone(turn % 2);
        if (!hasCapacity(spellZone)) {
            throw new IllegalActionException("spell card zone is full");
        }
        if (hasSetOrSummon) {
            throw new IllegalActionException("you already summoned/set on this turn");
        }

        for (int i = 0; i < 5; i++) {
            if (spellZone[i] == null) {
                spellZone[i] = selectedCard;
                handCards.remove(selectedAddress);
                break;
            }

        }
        selectedCard.setState(State.HIDDEN);
        hasSetOrSummon = true;
        deselect();
    }

    public void surrender() {
        winGame((turn + 1) % 2);
    }

    public void killSelected() {
        if (selectType == SelectType.MONSTER_OPPONENT) {
            BattleCard[] monsterOpponent = battleField.getMonsterZone((turn + 1) % 2);
            battleField.addToGraveYard((turn + 1) % 2, monsterOpponent[selectedAddress]);
            monsterOpponent[selectedAddress] = null;
        }
        if (selectType == SelectType.SELECT_MONSTER) {
            BattleCard[] monsterZone = battleField.getMonsterZone(turn % 2);
            battleField.addToGraveYard(turn % 2, monsterZone[selectedAddress]);
            monsterZone[selectedAddress] = null;
        }
        System.out.println("card added to graveyard successfully");
    }
}
