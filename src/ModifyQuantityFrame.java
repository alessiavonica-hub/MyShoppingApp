
import javax.swing.*;
import java.awt.*;

public class ModifyQuantityFrame extends JFrame {
    private ProductManager productManager;
    private Runnable onQuantityModified;

    public ModifyQuantityFrame(ProductManager productManager){
        this.productManager = productManager;

        this.setTitle("Modify Quantity");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel code = new JLabel("Product code: ");
        JTextField codeInput = new JTextField();
        JLabel quantity = new JLabel("New quantity:");
        JTextField quantityInput = new JTextField();

        topPanel.add(code);
        topPanel.add(codeInput);
        topPanel.add(quantity);
        topPanel.add(quantityInput);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        JButton modifyBtn = new JButton("Modify quantity");

        centerPanel.add(modifyBtn);

        this.add(centerPanel, BorderLayout.CENTER);

        this.setVisible(true);
        modifyBtn.addActionListener(e -> {
            if(verifyInput(codeInput.getText(), quantityInput.getText())){
                productManager.modifyQuantity(codeInput.getText(), quantityInput.getText());
                JOptionPane.showMessageDialog(this,"The quantity has been modified");
                if(onQuantityModified != null) onQuantityModified.run();
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Enter valid data");
                codeInput.setText("");
                quantityInput.setText("");
            }
        });
    }

    public boolean verifyInput(String code, String quantity){
        if(!productManager.verifyCode(code)){
            JOptionPane.showMessageDialog(this, "A product with this code does not exist");
            return false;
        }
        int quan;
        try{
            quan = Integer.parseInt(quantity);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Quantity must be a number");
            return false;
        }
        if(quan<=0 || quan>100){
            JOptionPane.showMessageDialog(this, "The quantity must be greater than 0 and less or equal to 100");
            return false;
        }
        return true;
    }

    public void setOnQuantityModified(Runnable callback){
        this.onQuantityModified = callback;
    }
}

