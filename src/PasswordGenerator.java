import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;

public class PasswordGenerator extends JFrame implements ActionListener {
    private JTextField lengthField;
    private JCheckBox includeLower, includeUpper, includeNumbers, includeSymbols;
    private JButton generateButton;
    private JTextField resultField;

    private final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String NUMBERS = "0123456789";
    private final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

    public PasswordGenerator() {
        setTitle("Random Password Generator");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 10, 5));

        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lengthPanel.add(new JLabel("Password Length:"));
        lengthField = new JTextField(5);
        lengthPanel.add(lengthField);
        add(lengthPanel);

        includeLower = new JCheckBox("Include Lowercase Letters (a-z)");
        includeUpper = new JCheckBox("Include Uppercase Letters (A-Z)");
        includeNumbers = new JCheckBox("Include Numbers (0-9)");
        includeSymbols = new JCheckBox("Include Special Characters (!@#...)");
        add(includeLower);
        add(includeUpper);
        add(includeNumbers);
        add(includeSymbols);

        generateButton = new JButton("Generate Password");
        generateButton.addActionListener(this);
        add(generateButton);

        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int length = Integer.parseInt(lengthField.getText());
            if (length <= 0) {
                throw new NumberFormatException();
            }

            StringBuilder charPool = new StringBuilder();

            if (includeLower.isSelected()) charPool.append(LOWER);
            if (includeUpper.isSelected()) charPool.append(UPPER);
            if (includeNumbers.isSelected()) charPool.append(NUMBERS);
            if (includeSymbols.isSelected()) charPool.append(SYMBOLS);

            if (charPool.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please select at least one character type.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String password = generatePassword(length, charPool.toString());
            resultField.setText(password);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid password length.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generatePassword(int length, String charPool) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            password.append(charPool.charAt(index));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordGenerator generator = new PasswordGenerator();
            generator.setVisible(true);
        });
    }
}
