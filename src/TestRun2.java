import app.Controller;
import app.DataCenter;
import app.model.Cards.Card;
import app.model.IllegalActionException;
import app.model.User;
import sun.misc.Cache;

import java.util.ArrayList;

public class TestRun2 {
    public static void main(String[] args) {
        DataCenter.getInstance().loadData();
//        for (User user : DataCenter.getInstance().getUsers()) {
//            user.migrate();
//        }
//        DataCenter.getInstance().storeData();
        Controller controller = new Controller();
        Card card = DataCenter.getInstance().getCard("Battle OX");
        System.out.println(card.toString());
    }
}
