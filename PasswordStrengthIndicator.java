import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordStrengthIndicator extends JFrame {
    private JTextField passwordField;
    private JLabel strengthLabel;

    public PasswordStrengthIndicator() {
        setTitle("Password Strength Indicator");
        setSize(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        passwordField = new JTextField();
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
                updateStrengthIndicator();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
                updateStrengthIndicator();
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        });

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password: "));
        passwordPanel.add(passwordField);
        add(passwordPanel, BorderLayout.NORTH);

        strengthLabel = new JLabel("Password is ");
        add(strengthLabel, BorderLayout.CENTER);

        JButton checkButton = new JButton("Check Strength");
        checkButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                updateStrengthIndicator();
        }
        });
        add(checkButton, BorderLayout.SOUTH);
        setVisible(true);
    }
    private void updateStrengthIndicator() {
        String password = passwordField.getText();
        int strength = calculatePasswordStrength(password);

        if (strength < 3) {
            strengthLabel.setForeground(Color.RED);
            strengthLabel.setText("Password is : Low");
        } else if (strength < 4) {
            strengthLabel.setForeground(Color.YELLOW);
            strengthLabel.setText("Password is : Medium");  
        } else {
            strengthLabel.setForeground(Color.GREEN);
            strengthLabel.setText("Password is : High");
        }
    }
    private int calculatePasswordStrength(String password) {
        if (password.length() < 8) {
            return 1;
        }
         int strength = 0;
         if (password.matches(".*[a-z].*")) {
            strength++;
        }
        if (password.matches(".*[A-Z].*")) {
            strength++;
        }
        if (password.matches(".*\\d.*")) {
            strength++;
        }
        if (password.length() >= 8) {
            strength++;
        }
        return strength;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordStrengthIndicator());
    }
}
