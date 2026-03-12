
import javax.swing.*;
import java.awt.*;

public class SignUpFrame extends JFrame {
    private UserManager userManager;

    public SignUpFrame(UserManager userManager) {
        this.userManager = userManager;

        this.setTitle("SingUp");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel email = new JLabel("email:");
        JTextField emailInputField = new JTextField();
        emailInputField.setColumns(20);
        JLabel password = new JLabel("password:");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setColumns(20);
        JLabel confirmPassword = new JLabel("confirm password");
        JPasswordField confirmPasswordField = new JPasswordField();
        JLabel details = new JLabel("Password must contain at least one upercase, lowercase and one number and be at least 6 characters");

        topPanel.add(email);
        topPanel.add(emailInputField);
        topPanel.add(password);
        topPanel.add(passwordField);
        topPanel.add(confirmPassword);
        topPanel.add(confirmPasswordField);
        topPanel.add(details);

        this.add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel();
        JButton createBtn = new JButton("Create Account");

        centerPanel.add(createBtn);

        this.add(centerPanel, BorderLayout.CENTER);

        createBtn.addActionListener(e -> {
            String emailString  = emailInputField.getText();
            String passString = new String(passwordField.getPassword());
            String confString = new String(confirmPasswordField.getPassword());

            if (validateSignUp(emailString, passString, confString)) {
                userManager.addUser(emailString, passString);
                new LoginFrame(userManager);
                this.dispose();
            }
            else{
                emailInputField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
            }
        });


        this.setVisible(true);
    }

    public boolean validateSignUp(String email, String password, String confrimPassword) {
        if (email.isEmpty() || password.isEmpty() || confrimPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceti datele");
            return false;
        }
        if (userManager.emailExistent(email)) {
            JOptionPane.showMessageDialog(this, "Email deja existent");
            return false;
        } else {
            if (!password.equals(confrimPassword)) {
                JOptionPane.showMessageDialog(this, "Parola nu a fost confirmata");
                return false;
            }
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email invalid");
            return false;
        }
        if (!password.matches(".*[0-9].*") || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*") || password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Incorrect Password. It must containat least one upercase, lowercase and one number and be at least 6 characters");
            return false;
        }
        return true;
    }

}