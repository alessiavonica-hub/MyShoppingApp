
import java.sql.*;

public class UserManager{
    private Connection conn;

    public UserManager() {
        try {
            //creare baza de date
            conn = DriverManager.getConnection("jdbc:sqlite:data/myApp.db");
            createTableIfNotExist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //creare tabel daca nu exista
    private void createTableIfNotExist() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL" +
                ")";
        Statement state = conn.createStatement();
        state.execute(sql);
    }
    public boolean emailExistent(String email){
        try {
            PreparedStatement state = conn.prepareStatement(
                    "SELECT * FROM users WHERE email = ?"
            );
            state.setString(1, email);
            ResultSet rs = state.executeQuery();
            return rs.next(); //true daca exista
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean verifyPassword(String email, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT password FROM users WHERE email = ?"
            );
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(password);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Adaugă un user nou în baza de date
    public void addUser(String email, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (email, password) VALUES (?, ?)"
            );
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Închide conexiunea la baza de date
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
