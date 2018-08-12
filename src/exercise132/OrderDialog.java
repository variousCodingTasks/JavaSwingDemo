package exercise132;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * this class represents JDialog where the textual representation
 * will be displayed before the user decides which action to take.
 */
public class OrderDialog extends JDialog{
    
    public static final int GAP = 5;    
    public static final int FONT_SIZE = 14;   
    public static final int SAVE_DIALOG_WIDTH = 350;
    public static final int SAVE_DIALOG_HEIGHT = 120;
    
    private final MenuOrderWindow menuOrderWindow;
    private final JTextArea orderText;
    private final JScrollPane textAreaScroller;
    private final JButton confirmButton;
    private final JButton updateButton;
    private final JButton cancelButton;
    private final JPanel bottomPanel;

    /**
     * creates and initializes the different components of the order window
     * dialogue.
     * 
     * @param menuOrderWindow the main window to which this dialog will be attached
     */
    public OrderDialog(MenuOrderWindow menuOrderWindow){
        super(menuOrderWindow ,"Order Confirmation", Dialog.ModalityType.DOCUMENT_MODAL);
        this.menuOrderWindow  = menuOrderWindow;
        this.setLayout(new BorderLayout(GAP, GAP));        
        this.bottomPanel = new JPanel();
        this.add(bottomPanel, BorderLayout.SOUTH);        
        this.confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ConfirmButtonHandler());        
        this.bottomPanel.add(confirmButton);        
        this.updateButton = new JButton("Update");
        updateButton.addActionListener(new CancelUpdateButtonHandler());        
        this.bottomPanel.add(updateButton);
        this.cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelUpdateButtonHandler());
        this.bottomPanel.add(cancelButton);        
        this.orderText = new JTextArea();
        orderText.setEditable(false);
        orderText.setFont(new Font("monospaced", Font.PLAIN + Font.BOLD, FONT_SIZE));
        this.textAreaScroller = new JScrollPane(orderText);
        this.add(textAreaScroller, BorderLayout.CENTER);
    }

    /**
     * getter for orderText which is the text area used by the dialog
     * 
     * @return the orderText which is JTextArea
     */
    public JTextArea getOrderText() {
        return this.orderText;
    }
    
    /**
     * a private class which implements an action listener for the
     * dialogue's save and cancel buttons. in case cancel is pressed,
     * the dialog will close and the main window will be cleared from
     * any user made changes, if update is pressed the dialog will simply
     * close to allow the user to edit his order.
     */
    private class CancelUpdateButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            if (event.getSource() == OrderDialog.this.cancelButton)
                OrderDialog.this.menuOrderWindow.clearAllItems();
            OrderDialog.this.setVisible(false);
            OrderDialog.this.dispose();
        }
    }
    
    /**
     * a private class which implements and action listener for the
     * dialogue's confirm button, if pressed the user will be showed
     * a new dialog on top of this one prompting him to save the order.
     */
    private class ConfirmButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            ConfirmSaveDialog confirmSaveDialog = new ConfirmSaveDialog();
            confirmSaveDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            confirmSaveDialog.setSize(SAVE_DIALOG_WIDTH, SAVE_DIALOG_HEIGHT);
            confirmSaveDialog.setResizable(false);            
            confirmSaveDialog.setVisible(true);
            
        }
    }
    
    /**
     * a private class which implements the save dialog when the user
     * presses confirm button.
     */
    private class ConfirmSaveDialog extends JDialog{
        
        private final JPanel bottomPanel;
        private final JTextField textField;
        private final JButton saveButton;
        private final JButton cancelButton;

        /**
         * creates and initializes the dialog's different components,
         * it also creates an instance of an anonymous class implementing
         * an action listener which closes the dialog and destroys it
         * in case the user presses cancel.
         */
        public ConfirmSaveDialog() {
            super(OrderDialog.this ,"Save Order", Dialog.ModalityType.DOCUMENT_MODAL);
            this.bottomPanel = new JPanel();
            this.add(bottomPanel, BorderLayout.SOUTH);  
            
            this.textField = new JTextField();
            this.add(textField, BorderLayout.CENTER);
            
            this.saveButton = new JButton("Save");
            saveButton.addActionListener(new SaveButtonHandler());
            this.bottomPanel.add(saveButton);
            
            this.cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                     ConfirmSaveDialog.this.setVisible(false);
                     ConfirmSaveDialog.this.dispose();
                }
            });
            this.bottomPanel.add(cancelButton);
            
            this.add(new JLabel("Enter customer name followed by her ID number:"), BorderLayout.NORTH);            
        }
        
        /**
         * checks if the file name supplied by the user is legal, should
         * consist of letters followed by digits only, no spaces or empty
         * strings are allowed.
         * 
         * @param fileName file name entered by the user
         * @return true if legal, 0 otherwise
         */
        private boolean isLegalFileName(String fileName){
            int i;
            for (i = 0; i < fileName.length(); i++){
                if (Character.isAlphabetic(fileName.charAt(i))) continue;
                else if (Character.isDigit(fileName.charAt(i))) break;
                else return false;
            }
            for (; i < fileName.length(); i++){
                if (!Character.isDigit(fileName.charAt(i))) return false;                
            }
            if (fileName.length() > 0 && Character.isAlphabetic(fileName.charAt(0))) return true;
            else return false;
        }
        
        /**
         * a private class which implements and action listener for the
         * save button: if pressed by the user, the actionPerformed method
         * checks if the file name entered in the JTextFiled is legal: if so,
         * it saves the string stored in the JTextArea in a file with the name
         * provided, otherwise the user will be shown an error message, so if
         * the writer fails to create the file (in case of an I/O exception).
         */
        private class SaveButtonHandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent event){
                String fileName = ConfirmSaveDialog.this.textField.getText();
                if (isLegalFileName(fileName)){
                    try {
                        FileWriter writer = new FileWriter(fileName + ".txt");
                        writer.write(orderText.getText());
                        writer.close();
                        ConfirmSaveDialog.this.dispose();
                        OrderDialog.this.dispose();
                        menuOrderWindow.clearAllItems();
                    }
                    catch (IOException e){
                        JOptionPane.showMessageDialog(ConfirmSaveDialog.this, "Could not save file!", "Error!", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(ConfirmSaveDialog.this, "File name must consist of letters followed by digits with no spaces!", "Error!", JOptionPane.ERROR_MESSAGE);
                    ConfirmSaveDialog.this.textField.setText("");
                }
            }
        }

    }
    
}