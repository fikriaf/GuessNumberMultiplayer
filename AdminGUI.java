import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class AdminGUI extends JFrame {
    private JTable playerTable;
    private DefaultTableModel tableModel;
    private JTextArea logArea;
    private JTextField searchField;
    private JButton refreshButton, exportButton, toggleThemeButton;
    private boolean isDarkMode = false;

    public AdminGUI() {
        super("Admin Panel - Guess Number Multiplayer");

        // Table
        String[] columnNames = {"Username", "IP", "Status", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        playerTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(playerTable);

        // Log area
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);

        // Top panel (search and buttons)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        refreshButton = new JButton("Refresh");
        exportButton = new JButton("Export Log");
        toggleThemeButton = new JButton("Dark Mode");

        topPanel.add(new JLabel("Cari: "));
        topPanel.add(searchField);
        topPanel.add(refreshButton);
        topPanel.add(exportButton);
        topPanel.add(toggleThemeButton);

        // Main layout
        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(logScroll, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        // Dummy Data
        insertDummyPlayers();
        insertDummyLogs();

        // Event listeners
        refreshButton.addActionListener(e -> refreshData());
        exportButton.addActionListener(e -> exportLog());
        toggleThemeButton.addActionListener(e -> toggleTheme());
    }

    private void insertDummyPlayers() {
        tableModel.setRowCount(0); // clear
        tableModel.addRow(new Object[]{"Fikri", "192.168.1.5", "Main", "00:01:12"});
        tableModel.addRow(new Object[]{"Maya", "192.168.1.10", "Timeout", "00:10:00"});
    }

    private void insertDummyLogs() {
        logArea.setText("");
        logArea.append("> Maya menebak: 44 - salah\n");
        logArea.append("> Fikri berhasil menebak!\n");
    }

    private void refreshData() {
        insertDummyPlayers();
        insertDummyLogs();
        log("Data diperbarui.");
    }

    private void exportLog() {
        JOptionPane.showMessageDialog(this, "Fitur Export belum diimplementasikan.");
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        Color bg = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fg = isDarkMode ? Color.WHITE : Color.BLACK;

        getContentPane().setBackground(bg);
        playerTable.setBackground(bg);
        playerTable.setForeground(fg);
        logArea.setBackground(bg);
        logArea.setForeground(fg);
        log("Mode tampilan diubah.");
    }

    private void log(String msg) {
        logArea.append("> " + msg + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}
