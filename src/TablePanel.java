
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TablePanel extends JPanel {
    private JTable table;
    private ProductManager productManager;
    private CartManager cartManager;

    DefaultTableModel model;
    private boolean isUser;
    private CartPanel cartPanel;

    public void initTabel(){
        this.cartManager = new CartManager();
        setLayout(new BorderLayout()); model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Name");
        model.addColumn("Code");
        model.addColumn("Price");
        model.addColumn("Quantity");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
        if(isUser){
            addDoubleClickListener();
        }
    }

    public void addDoubleClickListener(){
        table.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount() == 2){
                    int row = table.getSelectedRow();
                    String name = table.getValueAt(row, 0).toString();
                    int stock = (int) table.getValueAt(row, 3);

                    int option = JOptionPane.showConfirmDialog(null, "Add the product '" + name +"' to cart?",
                            "Add to cart", JOptionPane.YES_NO_OPTION);

                    if(option == JOptionPane.YES_OPTION) {
                        String offerString = JOptionPane.showInputDialog(null, "Enter quantity: ");
                        try {
                            int offer = Integer.parseInt(offerString);
                            if (offer > stock) {
                                JOptionPane.showMessageDialog(null, "Not that many products in stock");
                            } else if (offer <= 0) {
                                JOptionPane.showMessageDialog(null, "The quantity must be a positive number");
                            } else {
                                JOptionPane.showMessageDialog(null, "Product added to cart");
                                int i = (int) table.getValueAt(row, 3) - offer;
                                productManager.modifyQuantity(table.getValueAt(row, 1).toString(),
                                        Integer.toString(i));
                                cartManager.addProduct(table.getValueAt(row, 0).toString(),
                                        table.getValueAt(row, 1).toString(),
                                        table.getValueAt(row, 2).toString(),
                                        offer);
                                if (cartPanel != null) {
                                    cartPanel.addInCart(table.getValueAt(row, 0).toString(),
                                            table.getValueAt(row, 1).toString(),
                                            table.getValueAt(row, 2).toString(),
                                            offer);
                                }
                                loadProduct();
                            }
                        } catch(NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid number for quantity");
                        }

                    }
                }

            }
        });
    }
    public TablePanel(ProductManager productManager, boolean isUser){
        this.productManager = productManager;
        this.isUser = isUser;
        initTabel();
    }
    public TablePanel(ProductManager productManager, CartPanel cartPanel, boolean isUser){
        this.productManager = productManager;
        this.isUser = isUser;
        this.cartPanel = cartPanel;

        initTabel();
    }
    public void loadProduct(){
        model.setRowCount(0);

        try{
            Connection conn = productManager.getConnection();
            String sql = "SELECT * FROM products";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Object[] row = new Object[]{
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity")
                };
                model.addRow(row);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products from DataBase");
        }
    }
}
