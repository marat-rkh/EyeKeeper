import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

/**
 * Created by X on 18.04.2015.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final SystemTray tray = SystemTray.getSystemTray();
        final Image trayIconImg = createImage("images/bulb.gif", "tray icon");
        if (trayIconImg == null) {
            System.out.println("Icon image cannot be created");
            return;
        }
        final TrayIcon trayIcon = new TrayIcon(trayIconImg);

        final PopupMenu popup = new PopupMenu();
        // Create a popup menu components
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });
        //Add components to popup menu
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip("EyeKeeper");
        trayIcon.addActionListener(e -> {
            trayIcon.displayMessage("<TIME HERE> ", "Time remaining before action", TrayIcon.MessageType.NONE);
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = Main.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
