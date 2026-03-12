import javax.swing.*;
import java.awt.*;

public class ModifyPriceFrame extends JFrame {
    private ProductManager productManager;
    private Runnable onPriceModified;

    public ModifyPriceFrame(ProductManager productManager){
        this.productManager = productManager;

        this.setTitle("Modify Price");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel code = new JLabel("Product code: ");
        JTextField codeInput = new JTextField();
        JLabel price = new JLabel("New price:");
        JTextField priceInput = new JTextField();

        topPanel.add(code);
        topPanel.add(codeInput);
        topPanel.add(price);
        topPanel.add(priceInput);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        JButton modifyBtn = new JButton("Modify price");

        centerPanel.add(modifyBtn);

        this.add(centerPanel, BorderLayout.CENTER);

        this.setVisible(true);
        modifyBtn.addActionListener(e -> {
            if(verifyInput(codeInput.getText(), priceInput.getText())){
                productManager.modifyPrice(codeInput.getText(), priceInput.getText());
                JOptionPane.showMessageDialog(this,"The price has been modified");
                if(onPriceModified != null) onPriceModified.run();
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Enter valid data");
                codeInput.setText("");
                priceInput.setText("");
            }
        });
    }

    public boolean verifyInput(String code, String price){
        if(!productManager.verifyCode(code)){
            JOptionPane.showMessageDialog(this, "A product with this code does not exist");
            return false;
        }
        double pri;
        try{
            pri = Double.parseDouble(price);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "The price must be a number");
            return false;
        }
        if(pri<=0){
            JOptionPane.showMessageDialog(this, "The price must be a positive number");
            return false;
        }
        return true;
    }

    public void setOnPriceModified(Runnable callback){
        this.onPriceModified = callback;
    }
}
