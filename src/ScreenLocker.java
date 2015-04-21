import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScreenLocker {
    private final TrayIcon trayIcon;
    private final ScheduledThreadPoolExecutor executor;

    public ScreenLocker() throws Exception {
        this.trayIcon = createTrayIcon();
        this.executor = new ScheduledThreadPoolExecutor(1);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                SystemTray.getSystemTray().add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        });
        executor.scheduleAtFixedRate(() -> {
            String msg = "20 minutes elapsed, it's time to make a little break!";
            trayIcon.displayMessage("BREAK TIME!", msg, TrayIcon.MessageType.INFO);
        }, 20, 20, TimeUnit.MINUTES);
    }

    private TrayIcon createTrayIcon() throws Exception {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            throw new Exception("SystemTray is not supported");
        }
        final SystemTray tray = SystemTray.getSystemTray();
        final Image trayIconImg = createImage("images/eye.png", "tray icon");
        if (trayIconImg == null) {
            throw new Exception("Icon image cannot be created");
        }
        final TrayIcon trayIcon = new TrayIcon(trayIconImg);
        trayIcon.setImageAutoSize(true);

        final PopupMenu popup = new PopupMenu();
        // Create a popup menu components
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            executor.shutdownNow();
            System.exit(0);
        });
        //Add components to popup menu
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip("EyeKeeper");
        trayIcon.addActionListener(e -> {
            trayIcon.displayMessage("<TIME HERE> ", "Time remaining before action", TrayIcon.MessageType.NONE);
        });
        return trayIcon;
    }

    private Image createImage(String path, String description) {
        URL imageURL = Main.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
