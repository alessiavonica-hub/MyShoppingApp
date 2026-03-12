
import javax.swing.*;
import java.awt.*;

public class DeleteProductFrame extends JFrame{
    private ProductManager productManager;
    private Runnable onProductDeleted;

    public DeleteProductFrame(ProductManager productManager) {
        this.productManager = productManager;

        this.setTitle("Delete Product");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2,1, 5, 5));
        JButton nameBtn = new JButton("Delete product by name");
        JButton codeBtn = new JButton("Delete product by code");

        topPanel.add(nameBtn);
        topPanel.add(codeBtn);

        this.add(topPanel, BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Delete product");

        nameBtn.addActionListener(e -> {
            nameBtn.setVisible(false);
            codeBtn.setVisible(false);
            JPanel topPanel1 = new JPanel(new GridLayout(2,1, 5, 5));
            JLabel nameLabel= new JLabel("Delect product by name:");
            JTextField nameInput = new JTextField();

            topPanel1.add(nameLabel);
            topPanel1.add(nameInput);
            this.add(topPanel1, BorderLayout.NORTH);

            this.add(deleteBtn, BorderLayout.CENTER);

            deleteBtn.addActionListener(e1 -> {
                String nume = nameInput.getText();
                if(nume.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Enter a name");
                    nameInput.setText("");
                }
                else{
                    if(!productManager.verifyName(nume)){
                        JOptionPane.showMessageDialog(this, "A product with that name does not exist.");
                        nameInput.setText("");
                    }
                    else{
                        productManager.deleteProductByName(nume);
                        if(onProductDeleted != null) onProductDeleted.run();
                        this.dispose();
                    }
                }
            });
        });
        codeBtn.addActionListener(e -> {
            nameBtn.setVisible(false);
            codeBtn.setVisible(false);
            JPanel topPanel1 = new JPanel(new GridLayout(2,1, 5, 5));
            JLabel codeLabel = new JLabel("Delect product by code:");
            JTextField codeInput = new JTextField();

            topPanel1.add(codeLabel);
            topPanel1.add(codeInput);
            this.add(topPanel1, BorderLayout.NORTH);

            this.add(deleteBtn, BorderLayout.CENTER);

            deleteBtn.addActionListener(e1 -> {
                String code = codeInput.getText();
                if(code.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Enter a code");
                    codeInput.setText("");
                }
                else{
                    if(!productManager.verifyCode(code)){
                        JOptionPane.showMessageDialog(this, "A product with that code does not exist.");
                        codeInput.setText("");
                    }
                    else{
                        productManager.deleteProductByCode(code);
                        if(onProductDeleted != null) onProductDeleted.run();
                        this.dispose();
                    }
                }
            });
        });

        this.setVisible(true);
    }

    public void setOnProductDeleted(Runnable callback){
        this.onProductDeleted = callback;
    }



    //Top Panel
        /*
        JPanel topPannel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel code = new JLabel("Delete product by code:");
        JTextField codeInput = new JTextField();

        JLabel name = new JLabel("Delete product by name:");
        JTextField nameInput = new JTextField();

        topPannel.add(name);
        topPannel.add(nameInput);
        topPannel.add(code);
        topPannel.add(codeInput);

        this.add(topPannel, BorderLayout.NORTH);

        //Center Panel
        JPanel centerPanel = new JPanel();
        JButton deletePrdBtn = new JButton("Delete product");

        centerPanel.add(deletePrdBtn);

        this.add(centerPanel, BorderLayout.CENTER);

        deletePrdBtn.addActionListener(e -> {
            if(verifyInput(nameInput.getText(), codeInput.getText())){
                productManager.deleteProduct(nameInput.getText(), codeInput.getText());
                JOptionPane.showMessageDialog(this, "Product was removed from the list!");
                this.dispose();
            }

        });

        this.setVisible(true);
    }

    public boolean verifyInput(String name, String code) {
        if (name.isEmpty() || code.isEmpty()) {
            return false;
        }
        if (!name.isEmpty() && productManager.verifyName(name)){
            return false;
        }
        if (!code.isEmpty() && productManager.verifyCode(code)){
            return false;
        }
        return true;
    }

    public void setOnProductDeleted(Runnable callback){
        this.onProductDeleted = callback;
    }
    */
}
