
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CartPanel extends JPanel{
    private JTable table;
    DefaultTableModel model;
    private CartManager cartManager;

    public CartPanel(){
        setLayout(new BorderLayout());

        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Name");
        model.addColumn("Code");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Total Price");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        this.cartManager = new CartManager();
    }
    public void addInCart(String name, String code, String Price, int quantity){
        double price = Double.parseDouble(Price);
        double total = price * quantity;
        Object[] row = new Object[]{
                name,
                code,
                price,
                quantity,
                total
        };
        model.addRow(row);
    }
    public void loadProduct(){
        model.setRowCount(0);

        try{
            Connection conn = cartManager.getConnection();
            String sql = "SELECT * FROM cart";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Object[] row = new Object[]{
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("total")
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
