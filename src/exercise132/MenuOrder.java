package exercise132;

import java.util.ArrayList;

/**
 * a class which represents an order made by a client, contains the
 * order in a text form, no GUI components used in this class.
 */
public class MenuOrder {
    
    private final  MenuOrderWindow menuOrderWindow;
    private String orderText;
    private double orderTotal;

    /**
     * creates and initializes the fields of this instance.
     * 
     * @param menuOrderWindow the main windows of the application.
     */
    public MenuOrder(MenuOrderWindow menuOrderWindow){        
        this.menuOrderWindow = menuOrderWindow;
        this.orderText = "";
        this.orderTotal = 0;        
    }

    /**
     * getter for the text of this order.
     * 
     * @return this.orderText.
     */
    public String getOrderText() {
        return this.orderText;
    }

    /**
     * getter for the total price for the items in this order.
     * 
     * @return this.orderTotal.
     */
    public double getOrderTotal() {
        return this.orderTotal;
    }
    
    /**
     * iterates over the selected menu items (in all the category panels),
     * if the quantity is positive, the items string representation is added
     * and formatted to the order's text. the last line includes the total
     * amount of the order. if a category did not have any items selected,
     * it is no printed into the text.
     */
    public void processGUIInput(){
        ArrayList<MenuCategoryPanel> panels = this.menuOrderWindow.getMenuCategoryPanels();
        for (MenuCategoryPanel panel : panels){            
            ArrayList<MenuItemPanel> items = panel.getMenuItems();
            boolean panelNameAdded = false;
            for (MenuItemPanel item : items){
                if (item.isSelected() && item.getItemsCountSelection() > 0){
                    if (!panelNameAdded){
                        panelNameAdded = true;
                        this.orderText = String.format("%s\n %s:\n\n", this.orderText, panel);                    
                    }
                    this.orderTotal += item.getItemsCountSelection() * item.getItemPrice();
                    this.orderText += " " + item + "\n";
                }
            }
        }
        this.orderText += String.format("\n Subtotal = $%.2f\n\n Thank you, have a nice day!\n", this.orderTotal);        
    }
    
}
