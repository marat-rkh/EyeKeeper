import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by X on 18.04.2015.
 */
public class Main {
    public static void main(String[] args) {
        try {
            ScreenLocker screenLocker = new ScreenLocker();
            screenLocker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
