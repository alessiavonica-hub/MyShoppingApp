
import javax.swing.*;
import java.awt.*;

public class ViewCart extends JFrame {
    private ProductManager productManager;
    private CartPanel cartPanel;
    private CartManager cartManager;

    public ViewCart(){

        this.setTitle("View Cart");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        cartPanel = new CartPanel();
        this.add(cartPanel, BorderLayout.CENTER);

        cartPanel.loadProduct();

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton backBtn = new JButton("Back");
        JButton orderBtn = new JButton("Place order");
        bottomPanel.add(backBtn);
        bottomPanel.add(orderBtn);

        this.add(bottomPanel, BorderLayout.SOUTH);

        productManager = new ProductManager();
        cartManager = new CartManager();

        backBtn.addActionListener(e -> {
            new ShoppingFrame(productManager);
        });

        orderBtn.addActionListener(e -> {
            cartManager.placeOrder();
            JOptionPane.showMessageDialog(this, "Your order has been placed!");
            cartPanel.loadProduct();
        });

        this.setVisible(true);
    }
}
