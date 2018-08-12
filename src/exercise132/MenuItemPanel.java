package exercise132;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * creates a JPanel which will hold the menu item GUI components along
 * with its other properties.
 */
class MenuItemPanel extends JPanel{
    
    public static final Integer ITEMS_COUNT_LIST[] = {0, 1, 2, 3, 4, 5, 6, 7};
    public static final String PADDING_SPACES = "................................................................................";                                                
    public static final int MAX_DESCRIPTION_WIDTH = 70;
    public static final int MAX_STRING_WIDTH = 50;    
    public static final int FONT_SIZE = 14;
    
    private final String itemDescription;
    private final double itemPrice;
    private final JLabel itemLabel;
    private final JCheckBox itemCheckBox;
    private final JComboBox<Integer> itemComboBox;
    private int quantitySelection; 

    /**
     * creates and initializes the different components of the panel.
     * note that a monospaced font is used in order to create labels
     * with the same total width, since all the names will be followed
     * by dots (using PADDING_SPACES string) up to the point where all
     * labels have the width specified in MAX_DESCRIPTION_WIDTH.
     * 
     * @param itemDescription the name and description of the menu item.
     * @param itemPrice the price of the item in US Dollars.
     */
    public MenuItemPanel(String itemDescription, double itemPrice) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCheckBox = new JCheckBox();
        this.add(itemCheckBox);
        this.itemComboBox = new JComboBox<>(this.ITEMS_COUNT_LIST);
        itemComboBox.setMaximumRowCount(8);
        itemComboBox.addItemListener(new ItemComboBoxListener());
        this.add(itemComboBox);        
        this.itemLabel = new JLabel("  " + String.format("%s$%.2f  ", (this.itemDescription + this.PADDING_SPACES).substring(0, this.MAX_DESCRIPTION_WIDTH), this.itemPrice));
        this.itemLabel.setFont(new Font("monospaced", Font.PLAIN + Font.BOLD, FONT_SIZE));
        this.add(itemLabel);
        
        this.quantitySelection = 0;
    }
    
    /**
     * returns the quantity of this item 0  by default ort the user's selection.
     * 
     * @return the selection from JCombobox cast to Integer.
     */
    public Integer getItemsCountSelection(){
        return (Integer)this.itemComboBox.getSelectedItem();
    }
    
    /**
     * return the stater of the item's checkbox.
     * 
     * @return true if selected, false otherwise.
     */
    public boolean isSelected(){
        return this.itemCheckBox.isSelected();
    }
    
    /**
     * getter for the item's price.
     * 
     * @return this.itemPrice.
     */
    public double getItemPrice() {
        return this.itemPrice;
    }
   
    /**
     * resets the item to its default view and sets the internal selection
     * variable to zero.
     */
    public void clearSelection(){
        this.itemCheckBox.setSelected(false);
        this.itemComboBox.setSelectedIndex(0);
        this.quantitySelection = 0;
    }
    
    /**
     * overrides the object's toString method returns the item in string form.
     * this will be printed to the order if selected, it includes the items's
     * description, quantity and pice separated by dots, the total string length
     * will be set by MAX_STRING_WIDTH.
     * 
     * @return string representing the object.
     */
    @Override
    public String toString(){
        return String.format("%s%d x %.2f", (MenuItemPanel.this.itemDescription + this.PADDING_SPACES).substring(0, this.MAX_STRING_WIDTH), this.quantitySelection, this.itemPrice);
    }
    
    /**
     * an ItemListener for the JCombobx, which sets quantitySelection to the
     * value selected in the JCombobox.
     */
    private class ItemComboBoxListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e){
            if (e.getStateChange() == ItemEvent.SELECTED)
                MenuItemPanel.this.quantitySelection = MenuItemPanel.this.itemComboBox.getSelectedIndex();
        }
        
    }    
    
}
