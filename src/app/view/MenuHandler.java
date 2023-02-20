package app.view;

import app.Controller;
import app.model.IllegalActionException;

public interface MenuHandler {
    boolean handle(Controller controller)throws IllegalActionException;

}
