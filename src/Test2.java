import app.DataCenter;
import app.utils.DatabaseManager;

public class Test2 {
    public static void main(String[] args) {
        DataCenter dataCenter = DataCenter.getInstance();
        dataCenter.loadData();

        DatabaseManager.exportCard(dataCenter.getCards().get("Battle OX"));
    }
}
