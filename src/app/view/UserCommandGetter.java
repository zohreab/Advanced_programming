package app.view;

import java.util.Scanner;

public class UserCommandGetter {
    static Scanner scanner = new Scanner(System.in);
    public static String getUserCommand(){
        return scanner.nextLine();
    }
}
