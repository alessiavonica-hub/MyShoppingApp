
import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    private ProductManager productManager;
    private TablePanel tablePanel;

    public AdminFrame(ProductManager productManager){

        this.setTitle("Admin Page");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        //Top Panel
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JButton addPrdBtn = new JButton("Add Product");
        JButton deletePrdBtn = new JButton("Delete product");
        JButton priceBtn = new JButton("Modify Price");
        JButton quantityBtn = new JButton("Modify Quantity");

        topPanel.add(addPrdBtn);
        topPanel.add(deletePrdBtn);
        topPanel.add(priceBtn);
        topPanel.add(quantityBtn);
        this.add(topPanel, BorderLayout.NORTH);

        //Table Panel
        tablePanel = new TablePanel(productManager, false);
        this.add(tablePanel, BorderLayout.CENTER);
        tablePanel.loadProduct();

        //bottom Panel
        JButton logOutBtn = new JButton("Log out");
        this.add(logOutBtn, BorderLayout.SOUTH);

        logOutBtn.addActionListener(e -> {
            new LoginFrame(new UserManager());
        });

        //Action Listener
        addPrdBtn.addActionListener(e -> {
            AddProductFrame addFrame = new AddProductFrame(productManager);
            addFrame.setOnProductAdded(() -> tablePanel.loadProduct());
        });

        deletePrdBtn.addActionListener(e -> {
            DeleteProductFrame addFrame = new DeleteProductFrame(productManager);
            addFrame.setOnProductDeleted(() -> tablePanel.loadProduct());
        });

        priceBtn.addActionListener(e -> {
            ModifyPriceFrame addFrame = new ModifyPriceFrame(productManager);
            addFrame.setOnPriceModified(() -> tablePanel.loadProduct());
        });

        quantityBtn.addActionListener(e -> {
            ModifyQuantityFrame addFrame = new ModifyQuantityFrame(productManager);
            addFrame.setOnQuantityModified(() -> tablePanel.loadProduct());
        });

        this.setVisible(true);
    }

}
