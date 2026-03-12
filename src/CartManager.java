import java.sql.*;

import java.sql.DriverManager;
import java.sql.SQLException;

public class CartManager {
    private Connection conn;
    private ProductManager productManager;
    public CartManager(){
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/myCart.db");
            createTableIfNotExists();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    private void createTableIfNotExists() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS cart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL, " +
                "code TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "total REAL NOT NULL" +
                ")";
        Statement state = conn.createStatement();
        state.execute(sql);
    }
    public boolean verifyCode(String code){
        String sql = "SELECT 1 FROM cart WHERE code = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); //verificare daca deja exista - true daca exista
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public int returnQuantity( String code){
        String sql1 = "SELECT quantity FROM cart WHERE code = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql1)){
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("quantity");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void addProduct(String name, String code, String Price, int quantity) {
        if(verifyCode(code)){
            int i = returnQuantity(code);
            quantity = i+quantity;
            String Quantity = Integer.toString(quantity);
            String sql = "UPDATE cart SET quantity = ? WHERE code = ?";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, Quantity);
                statement.setString(2, code);

                statement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            String sql ="INSERT INTO cart (name, code, price, quantity, total) VALUES(?,?,?,?, ?)";
            double price = Double.parseDouble(Price);
            double total = price * quantity;

            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, name);
                statement.setString(2, code);
                statement.setDouble(3, price);
                statement.setInt(4, quantity);
                statement.setDouble(5, total);
                statement.executeUpdate();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void placeOrder(){
        String sql = "DELETE FROM cart";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
