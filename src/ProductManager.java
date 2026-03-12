
import java.sql.*;

public class ProductManager {
    private Connection conn;

    public ProductManager(){
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/myProducts.db");
            createTableIfNotExists();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    //creare Tabel daca nu exista
    private void createTableIfNotExists() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS products(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL, " +
                "code TEXT UNIQUE NOT NULL," +
                "price REAL NOT NULL," +
                "quantity INTEGER NOT NULL" +
                ")";
        Statement state = conn.createStatement();
        state.execute(sql);
    }

    public void addProduct(String name, String code, String Price, String Quantity) {
        String sql ="INSERT INTO products (name, code, price, quantity) VALUES(?,?,?,?)";
        double price = Double.parseDouble(Price);
        int quantity = Integer.parseInt(Quantity);

        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, code);
            statement.setDouble(3, price);
            statement.setInt(4, quantity);
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteProductByName(String name) {
        String sql = "DELETE FROM products WHERE name = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, name);
            int row = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteProductByCode(String code) {
        String sql = "DELETE FROM products WHERE code = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, code);
            int row = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean verifyName(String name){
        String sql = "SELECT 1 FROM products WHERE name = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); //verificare daca deja exista - true daca exista
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyCode(String code){
        String sql = "SELECT 1 FROM products WHERE code = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); //verificare daca deja exista - true daca exista
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void modifyPrice(String code, String price){
        String sql = "UPDATE products SET price = ? WHERE code = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, price);
            statement.setString(2, code);

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void modifyQuantity(String code, String quantity){
        String sql = "UPDATE products SET quantity = ? WHERE code = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, quantity);
            statement.setString(2, code);

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
