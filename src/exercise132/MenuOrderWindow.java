package exercise132;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * implements the GUI of the main window.
 */
public class MenuOrderWindow extends JFrame{
    
    public static final int GAP = 5;
    public static final ArrayList<String> DISH_CATERGEORIES = new ArrayList<>();
    
     static {
        DISH_CATERGEORIES.add("Appetizer");
        DISH_CATERGEORIES.add("Main");
        DISH_CATERGEORIES.add("Dessert");
        DISH_CATERGEORIES.add("Drink");
    }

    private final JPanel centerPanel;
    private final ArrayList<MenuCategoryPanel> menuCategoryPanels;
    private final String fileName;
    private final BottomPanel bottomPanel;
   
    /**
     * creates and initializes the items in the menu and adds them to the window,
     * each category (4 in total) has its own JPnel which are added to menuCategoryPanels
     * for easier iteration.
     * 
     * @param fileName the name of the file which includes the names of the items
     * on the menu.
     */
    public MenuOrderWindow(String fileName){
        super("Menu Order Window");
        this.setLayout(new BorderLayout(GAP, GAP));        
        this.centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));
        this.add(centerPanel, BorderLayout.CENTER);        
        this.menuCategoryPanels = new ArrayList<>();
        menuCategoryPanels.add(new MenuCategoryPanel("Appetizers"));
        menuCategoryPanels.add(new MenuCategoryPanel("Main Course"));
        menuCategoryPanels.add(new MenuCategoryPanel("Dessert"));
        menuCategoryPanels.add(new MenuCategoryPanel("Drink"));        
        this.bottomPanel = new BottomPanel(this);
        this.add(bottomPanel, BorderLayout.SOUTH);        
        this.fileName = fileName;        
        add(new JLabel("  "), BorderLayout.WEST);
        add(new JLabel("  "), BorderLayout.EAST);
    }

    /**
     * returns the array list which contains the references to the category
     * panels.
     * 
     * @return this.menuCategoryPanels.
     */
    public ArrayList<MenuCategoryPanel> getMenuCategoryPanels() {
        return this.menuCategoryPanels;
    }
    
    /**
     * reads the menu items from the file, creates a new item panel for
     * each, and adds it to its corresponding category panel. should
     * print an error in case the file supplied is incorrect.
     */
    public void populatMenuOrderWindows(){
        Scanner in = null;
        for (MenuCategoryPanel panel : this.menuCategoryPanels)
            this.centerPanel.add(panel);
        try {
             in = new Scanner(new File(this.fileName));
             while(in.hasNext()){
                 this.addMenuItem(in);
             }
        }
        catch (FileNotFoundException e){
            System.err.printf("file \"%s\" does not exist.\n", Exercise132.MENU_FILE);
        }
        finally {
            if (in != null) in.close();
        }         
    }
    
    /**
     * reads the next 3 lines in the file (assuming correct structure),
     * and creates a new item panel for the GUI with its details.
     * 
     * @param in a scanner object to read from.
     */
    private void addMenuItem(Scanner in){
        int categorySelection = this.DISH_CATERGEORIES.indexOf(in.nextLine().replaceAll("\n", ""));
        MenuCategoryPanel categorySelectionPanel = this.menuCategoryPanels.get(categorySelection);
        categorySelectionPanel.addMenuItem(new MenuItemPanel(in.nextLine().replaceAll("\n", ""), in.nextDouble()));
        in.nextLine();
    }
    
    /**
     * iterates over the category panels and clears and selections
     * or changes made by the user.
     */
    public void clearAllItems(){
        for (MenuCategoryPanel panel : this.menuCategoryPanels)
            panel.clearItems();
    }
    
}
