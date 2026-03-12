
import javax.swing.*;
import java.awt.*;

public class AddProductFrame extends JFrame {
    private ProductManager productManager;
    private Runnable onProductAdded;

    public AddProductFrame(ProductManager productManager){
        this.productManager = productManager;

        this.setTitle("Add Product");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        /// TOP PANEL
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel name =  new JLabel("Name:");
        JTextField nameInput = new JTextField();
        JLabel code = new JLabel("Product Code:");
        JTextField codeInput = new JTextField();
        JLabel price = new JLabel("Price:");
        JTextField priceInput = new JTextField();
        JLabel quantity = new JLabel("Quantity");
        JTextField quantityInput = new JTextField();

        topPanel.add(name);
        topPanel.add(nameInput);
        topPanel.add(code);
        topPanel.add(codeInput);
        topPanel.add(price);
        topPanel.add(priceInput);
        topPanel.add(quantity);
        topPanel.add(quantityInput);
        this.add(topPanel, BorderLayout.NORTH);

        /// CENTER PANEL
        JPanel centerPanel = new JPanel();
        JButton addPrdBtn = new JButton("Add Product");
        centerPanel.add(addPrdBtn);
        this.add(centerPanel, BorderLayout.CENTER);

        ///
        addPrdBtn.addActionListener(e -> {
            if(verifyInput(nameInput.getText(), codeInput.getText(), priceInput.getText(), quantityInput.getText())){
                JOptionPane.showMessageDialog(this, "Product added successfully" );
                productManager.addProduct(nameInput.getText(), codeInput.getText(), priceInput.getText(), quantityInput.getText());
                if(onProductAdded != null) onProductAdded.run();
                this.dispose();
            }
            else {
                nameInput.setText("");
                codeInput.setText("");
                priceInput.setText("");
                quantityInput.setText("");
            }
        });

        this.setVisible(true);
    }

    public boolean verifyInput(String name, String code, String price, String quantity){
        if(name.isEmpty() || code.isEmpty() || price.isEmpty() || quantity.isEmpty()){
            JOptionPane.showMessageDialog(this, "Introduceti datele");
            return false;
        }
        if(!name.matches("[a-zA-Z\\- ]+")){
            JOptionPane.showMessageDialog(this, "The name can only contains letters, spaces and the '-' symbol");
            return false;
        }
        if(!code.matches("[a-z]{3}-\\d{4}")){
            JOptionPane.showMessageDialog(this, "The product code must be in aaa-0000 format");
            return false;
        }
        int quan;
        double pri;
        try{
            quan = Integer.parseInt(quantity);
            pri = Double.parseDouble(price);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Price and quantity must be numbers");
            return false;
        }
        if(quan<=0 || quan>100){
            JOptionPane.showMessageDialog(this, "The quantity must be greater than 0 and less or equal to 100");
            return false;
        }
        if(pri<=0){
            JOptionPane.showMessageDialog(this, "The price must be a positive number");
            return false;
        }
        if(productManager.verifyCode(code)){
            JOptionPane.showMessageDialog(this, "Product code already exists");
            return false;
        }
        if(productManager.verifyName(name)){
            JOptionPane.showMessageDialog(this, "Product name already exists");
            return false;
        }
        return true;
    }

    public void setOnProductAdded(Runnable callback){
        this.onProductAdded = callback;
    }
}
