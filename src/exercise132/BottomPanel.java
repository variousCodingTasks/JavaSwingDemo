package exercise132;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * this class implements the bottom panel which includes three buttons
 * for the user to press, it is separated from the main window class
 * for convenience purposes.
 */
public class BottomPanel extends JPanel{
    
    public static final int WINDOW_WIDTH = 550;
    public static final int WINDOW_HEIGHT = 700;
    
    private final MenuOrderWindow menuOrderWindow;
    private final JButton clearButton;
    private final JButton orderButton;
    private final JButton closeButton;

    /**
     * creates and sets the different JPanel components and adds
     * their action listeners.
     * 
     * @param menuOrderWindow the main window object JFrame
     */
    public BottomPanel(MenuOrderWindow menuOrderWindow) {
        this.menuOrderWindow = menuOrderWindow;        
        this.clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonHandler());
        this.add(clearButton);                
        this.orderButton = new JButton("Order");
        orderButton.addActionListener(new OrderButtonHandler());        
        this.add(orderButton);        
        this.closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonHandler());
        this.add(closeButton);        
    }
    
    /**
     * this class implements an action listener for the close button.
     */
    private class CloseButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            System.exit(0);
        }
    }
    
    /**
     * this class creates an action listener for the clear button which
     * clears all the changes made by the user.
     */
    private class ClearButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            BottomPanel.this.menuOrderWindow.clearAllItems();
        }
    }
    
    /**
     * implements an action listener for the order button, which opens
     * a dialog with the order summarized: the action actionPerformed creates
     * a new orderDialog and sets its different parameters and then displays
     * it to the user.
     */
    private class OrderButtonHandler implements ActionListener{        
        @Override
        public void actionPerformed(ActionEvent event){
            OrderDialog orderDialog = new OrderDialog(BottomPanel.this.menuOrderWindow);
            MenuOrder menuOrder =  new MenuOrder(BottomPanel.this.menuOrderWindow);
            menuOrder.processGUIInput();
            orderDialog.getOrderText().append(menuOrder.getOrderText());
            orderDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            orderDialog.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            orderDialog.setResizable(false);            
            orderDialog.setVisible(true);
        }
    }
    
}
