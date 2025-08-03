import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuessNumber extends JFrame {
    public GuessNumber() {
        super("Guess The Number - Menu");
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Pilih Mode Permainan", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton adminButton = new JButton("Admin");
        JButton serverButton = new JButton("Server (Host)");
        JButton clientButton = new JButton("Client (Guest)");

        buttonPanel.add(adminButton);
        buttonPanel.add(serverButton);
        buttonPanel.add(clientButton);

        add(buttonPanel, BorderLayout.CENTER);

        adminButton.addActionListener(e -> openAdmin());
        serverButton.addActionListener(e -> runJavaFile("ServerGUI"));
        clientButton.addActionListener(e -> runJavaFile("ClientGUI"));

        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void openAdmin() {
        String input = JOptionPane.showInputDialog(this, "Masukkan password admin:");
        if ("admin123".equals(input)) {
            runJavaFile("AdminGUI");
        } else {
            JOptionPane.showMessageDialog(this, "Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runJavaFile(String className) {
        try {
            Class<?> cls = Class.forName(className);
            SwingUtilities.invokeLater(() -> {
                try {
                    cls.getDeclaredConstructor().newInstance();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal membuka: " + className);
                    ex.printStackTrace();
                }
            });
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Class tidak ditemukan: " + className);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessNumber::new);
    }
}
