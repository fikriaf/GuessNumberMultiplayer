import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JLabel statusLabel;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientGUI() {
        super("Game Tebak Angka - Client");

        chatArea = new JTextArea(15, 30);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField(20);
        sendButton = new JButton("Kirim");
        statusLabel = new JLabel("Status: Belum terhubung");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);

        sendButton.addActionListener(e -> sendGuess());
        inputField.addActionListener(e -> sendGuess());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // tengah layar
        setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        String host = JOptionPane.showInputDialog(this, "Masukkan IP Server:", "127.0.0.1");
        if (host == null || host.isEmpty()) {
            System.exit(0);
        }

        try {
            socket = new Socket(host, 1234);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            statusLabel.setText("Terhubung ke server: " + host);

            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        chatArea.append("Server: " + line + "\n");
                        if (line.startsWith("Tepat!") || line.startsWith("Kamu gagal")) {
                            statusLabel.setText("Permainan selesai");
                            inputField.setEditable(false);
                            sendButton.setEnabled(false);
                        }
                    }
                } catch (IOException ex) {
                    chatArea.append("Koneksi terputus.\n");
                    statusLabel.setText("Koneksi terputus");
                    inputField.setEditable(false);
                    sendButton.setEnabled(false);
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Tidak bisa terhubung ke server: " + e.getMessage());
            System.exit(0);
        }
    }

    private void sendGuess() {
        String guess = inputField.getText().trim();
        if (guess.isEmpty()) return;

        try {
            int angka = Integer.parseInt(guess);
            if (angka < 0 || angka > 100) {
                JOptionPane.showMessageDialog(this, "Masukkan angka antara 0 sampai 100.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input bukan angka valid.");
            return;
        }

        out.println(guess);
        chatArea.append("Kamu: " + guess + "\n");
        inputField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }
}
