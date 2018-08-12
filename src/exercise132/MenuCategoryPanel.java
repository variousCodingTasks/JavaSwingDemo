package exercise132;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * represents a panel which packs menu items (GUI sub-panel) of 
 * the same category.
 */
public class MenuCategoryPanel extends JPanel{
    
    public static final int FONT_SIZE = 18;
    
    private final String menuCategory;
    /* an arraylist to store the instances of the menu items for easier iteration. */
    private final ArrayList<MenuItemPanel> menuItems;
    private final JLabel categoryLabel;

    /**
     * creates and initializes the JPanel different components, monospaced
     * font is selected for consistency with other components.
     * 
     * @param menuCategory the name of this category.
     */
    public MenuCategoryPanel(String menuCategory) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
        this.menuCategory = menuCategory;
        this.menuItems = new ArrayList<>();        
        this.categoryLabel = new JLabel(this.menuCategory);
        this.categoryLabel.setFont(new Font("monospaced", Font.PLAIN + Font.BOLD, FONT_SIZE));
        this.add(categoryLabel);        
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    /**
     * getter for the ArrayList which holds the menu items.
     * 
     * @return this.menuItems
     */
    public ArrayList<MenuItemPanel> getMenuItems() {
        return this.menuItems;
    }
    
    /**
     * adds the item to this panel and stores it in the ArrayList.
     * 
     * @param item the item to add
     */
    public void addMenuItem(MenuItemPanel item){
        this.menuItems.add(item);
        this.add(item);
    }
    
    /**
     * iterates over all the items in this panel and clears any user
     * made changes.
     */
    public void clearItems(){
        for(MenuItemPanel item : this.menuItems)
            item.clearSelection();
    }
    
    /**
     * a  string representation of this class, simply the name of the category.
     * 
     * @return the name of this category.
     */
    @Override
    public String toString(){
        return this.menuCategory;
    }
    
}
