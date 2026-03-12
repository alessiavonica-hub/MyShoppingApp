
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private UserManager userManager;

    public LoginFrame(UserManager userManager){
        this.userManager = userManager;

        this.setTitle("LogIn");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        //top Panel - partea de login
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel email = new JLabel("email: ");
        JTextField emailInput = new JTextField();
        emailInput.setColumns(20);

        JLabel password = new JLabel("password:");
        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setColumns(20);

        topPanel.add(email);
        topPanel.add(emailInput);
        topPanel.add(password);
        topPanel.add(passwordInput);

        this.add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel();
        JButton singInBtn = new JButton("Sign in");

        centerPanel.add(singInBtn);
        this.add(centerPanel, BorderLayout.CENTER);

        //bottom panel - cont nou
        JPanel bottomPanel = new JPanel();
        JLabel text = new JLabel("Don't have an account?");
        JButton signUpBtn = new JButton("Sign Up");

        bottomPanel.add(text);
        bottomPanel.add(signUpBtn);

        this.add(bottomPanel, BorderLayout.SOUTH);

        singInBtn.addActionListener(e -> {

            String emailString  = emailInput.getText();
            String passString = new String(passwordInput.getPassword());

            if(!validateLoginIn(emailString, passString)){
                emailInput.setText("");
                passwordInput.setText("");
            }
            else {
                ProductManager productManager = new ProductManager();
                if (emailString.equals("admin@gmail.com")) {
                    new AdminFrame(productManager);
                    this.dispose();
                } else {
                    new ShoppingFrame(productManager);
                    this.dispose();
                }
            }
        });

        signUpBtn.addActionListener(e -> {
            new SignUpFrame(userManager);
            this.dispose();
        });

        this.setVisible(true);
    }

    public boolean validateLoginIn(String email, String password){
        if(email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Introduceti emailul si parola");
            return false;
        }
        if(userManager.emailExistent(email)){
            if(userManager.verifyPassword(email, password)){
                //email validat
                return true;
            }
            else {
                JOptionPane.showMessageDialog(this, "Incorrect password or email!");
                return false;
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Nonexistent Email");
            return false;
        }
    }
}
