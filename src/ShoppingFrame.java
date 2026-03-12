
import javax.swing.*;
import java.awt.*;

public class ShoppingFrame extends JFrame {
    private ProductManager productManager;
    private CartPanel cartPanel;
    private TablePanel tablePanel;

    public ShoppingFrame(ProductManager productManager){

        this.setTitle("Shopping Page");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        tablePanel = new TablePanel(productManager, cartPanel, true);
        this.add(tablePanel, BorderLayout.CENTER);
        tablePanel.loadProduct();

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton viewBtn = new JButton("View cart");
        JButton logBtn = new JButton("Log Out");

        bottomPanel.add(logBtn);
        bottomPanel.add(viewBtn);

        this.add(bottomPanel, BorderLayout.SOUTH);

        viewBtn.addActionListener(e -> {
            new ViewCart();
        });

        logBtn.addActionListener(e -> {
            new LoginFrame(new UserManager());
        });

        this.setVisible(true);
    }
}
