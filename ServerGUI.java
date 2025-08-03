import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGUI extends JFrame {
    private JTextArea logArea;
    private JTextField secretField;
    private JButton startButton, sendSecretButton;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Thread clientThread;
    private int secretNumber;
    private final int PORT = 1234;
    private boolean serverRunning = false;

    public ServerGUI() {
        super("Server Tebak Angka");

        logArea = new JTextArea(20, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        secretField = new JTextField(10);
        sendSecretButton = new JButton("Kirim Angka Rahasia");
        sendSecretButton.setEnabled(false);

        startButton = new JButton("Start Server");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Angka Rahasia (1-100):"));
        topPanel.add(secretField);
        topPanel.add(sendSecretButton);
        topPanel.add(startButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startButton.addActionListener(e -> {
            if (!serverRunning) {
                startServer();
            } else {
                stopServer();
            }
        });

        sendSecretButton.addActionListener(e -> sendSecret());
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            log("Server mulai, menunggu client di port " + PORT);
            serverRunning = true;
            startButton.setText("Stop Server");
            sendSecretButton.setEnabled(false);
            secretField.setEnabled(true);

            // Thread menunggu client connect
            new Thread(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    log("Client terhubung dari " + clientSocket.getInetAddress().getHostAddress());

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                    out.println("Selamat datang! Tunggu angka rahasia dikirim...");

                    sendSecretButton.setEnabled(true);
                    secretField.setEnabled(true);

                    // Thread baca input client
                    clientThread = new Thread(() -> handleClient());
                    clientThread.start();

                } catch (IOException ex) {
                    if (serverRunning) {
                        log("Error menerima client: " + ex.getMessage());
                    }
                }
            }).start();

        } catch (IOException e) {
            log("Gagal mulai server: " + e.getMessage());
        }
    }

    private void stopServer() {
        try {
            serverRunning = false;
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            log("Server dihentikan.");
            startButton.setText("Start Server");
            sendSecretButton.setEnabled(false);
            secretField.setEnabled(false);
        } catch (IOException e) {
            log("Error saat stop server: " + e.getMessage());
        }
    }

    private void sendSecret() {
        String input = secretField.getText().trim();
        try {
            int secret = Integer.parseInt(input);
            if (secret < 1 || secret > 100) {
                JOptionPane.showMessageDialog(this, "Masukkan angka antara 1 sampai 100!");
                return;
            }
            secretNumber = secret;
            out.println("Angka rahasia telah disiapkan.\nSilakan mulai menebak.\nKamu punya 10 percobaan!");
            log("Angka rahasia dikirim ke client: " + secretNumber);
            sendSecretButton.setEnabled(false);
            secretField.setEnabled(false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harus angka valid!");
        }
    }

    private void handleClient() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 10;
        try {
            while (attempts < MAX_ATTEMPTS) {
                String guessStr = in.readLine();
                if (guessStr == null) {
                    log("Client terputus.");
                    break;
                }
                int guess;
                try {
                    guess = Integer.parseInt(guessStr);
                } catch (NumberFormatException e) {
                    out.println("Input tidak valid, masukkan angka!");
                    continue;
                }
                attempts++;
                log("Tebakan ke-" + attempts + ": " + guess);

                if (guess == secretNumber) {
                    out.println("Tepat! Kamu menebaknya dalam " + attempts + " percobaan.");
                    log("Client menang.");
                    break;
                } else if (guess < secretNumber) {
                    out.println("Terlalu kecil.");
                } else {
                    out.println("Terlalu besar.");
                }
            }

            if (attempts == MAX_ATTEMPTS) {
                out.println("Kamu gagal menebak. Angka rahasianya adalah " + secretNumber + ".");
                log("Client gagal menebak.");
            }
            clientSocket.close();
        } catch (IOException e) {
            log("Error komunikasi dengan client: " + e.getMessage());
        }
    }

    private void log(String message) {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        SwingUtilities.invokeLater(() -> logArea.append("[" + time + "] " + message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerGUI::new);
    }
}
