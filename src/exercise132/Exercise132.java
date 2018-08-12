package exercise132;

import javax.swing.JFrame;

/**
 * main class of the menu order application.
 */
public class Exercise132 {

    public static final int WINDOW_WIDTH = 720;
    public static final int WINDOW_HEIGHT = 990;
    public static final String MENU_FILE = "menu.txt";
    
    /**
     * creates a new order window which resembles an actual restaurant
     * menu and sets its dimensions and properties.
     */
    public static void main(String[] args) {        
        MenuOrderWindow mainWindow = new MenuOrderWindow(MENU_FILE);
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.populatMenuOrderWindows();
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
    
}
