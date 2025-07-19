/**
 * <h2>HotelGUI.java</h2>
 * <p><b>Author:</b> Andres Vera</p>
 * <p><b>Course:</b> CEN 3024 - Software Development I</p>
 * <p><b>Date:</b> 07/15/25</p>
 *
 * <p>This class defines the graphical user interface (GUI) for the Hotel Data Management System (DMS).</p>
 * <p>It enables users to connect to a SQLite database and perform full CRUD operations (Create, Read, Update, Delete)
 * through buttons and input fields. It also supports searching reservations by guest name.</p>
 *
 * <p><b>Primary features include:</b></p>
 * <ul>
 *   <li>Connecting to a SQLite database via file selection</li>
 *   <li>Viewing all stored reservations</li>
 *   <li>Adding new reservations with full input validation</li>
 *   <li>Updating and deleting reservations by ID</li>
 *   <li>Searching for reservations using guest names</li>
 * </ul>
 *
 * <p><b>Dependencies:</b> Uses the {@link DatabaseManager} class to interact with the SQLite database.</p>
 *
 * <p><b>Output:</b> Displays status messages and reservation data in a scrollable text area.</p>
 *
 * @author Andres Vera
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HotelGUI extends JFrame {
    private DatabaseManager dbManager;
    private JTextArea displayArea;

    /**
     * Constructs the HotelGUI window, setting up layout, buttons, and event handling.
     * <p>
     * The GUI includes:
     * <ul>
     *   <li>Database connection file chooser</li>
     *   <li>Buttons for View All, Add, Delete, Update, Search, Clear, Exit</li>
     *   <li>Validation logic for inputs (name, email, room number, dates)</li>
     * </ul>
     */
    public HotelGUI() {
        // Initialize the database manager
        dbManager = new DatabaseManager();
        // Set title of the window
        setTitle("Hotel DMS GUI - Database Edition");
        // Set initial size
        setSize(800, 600);
        // Exit the app when closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center the window on screen
        setLocationRelativeTo(null);
        // Use BorderLayout
        setLayout(new BorderLayout());

        // Create text area for displaying messages and query results
        displayArea = new JTextArea();
        displayArea.setEditable(false);               // Make output non-editable
        JScrollPane scrollPane = new JScrollPane(displayArea); // Make it scrollable
        add(scrollPane, BorderLayout.CENTER);         // Add to center of layout

        // Buttons panel
        JPanel topPanel = new JPanel(new GridLayout(2, 5, 10, 10));

        // Connect to Database
        JButton connectDatabaseButton = new JButton("Connect DB");
        connectDatabaseButton.addActionListener(e -> {
            // Open file chooser
            JFileChooser fileChooser = new JFileChooser();
            // Wait for user to choose
            int option = fileChooser.showOpenDialog(this);
            // If a file was selected
            if (option == JFileChooser.APPROVE_OPTION) {
                // Get path
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                // Set DB path
                dbManager.setDatabasePath(path);
                displayArea.setText("Connected to database: " + path);
            }
        });

        // View All Reservations
        JButton viewButton = new JButton("View All");
        viewButton.addActionListener(e -> {
            if (!dbManager.isConnected()) {
                displayArea.setText("Please connect to a database first.");
                return;
            }
            // Fetch all
            ArrayList<String> reservations = dbManager.getAllReservations();
            // Clear display
            displayArea.setText("");
            for (String res : reservations) {
                // Show each
                displayArea.append(res + "\n\n");
            }
        });

        // Add Reservation
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            if (!dbManager.isConnected()) {
                displayArea.setText("Please connect to a database first.");
                return;
            }
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField roomField = new JTextField();
            String[] roomTypes = {"Patio View", "City View"};
            JComboBox<String> typeBox = new JComboBox<>(roomTypes);
            JTextField rateField = new JTextField();
            rateField.setEditable(false); // Auto-filled
            JTextField checkInField = new JTextField("yyyy-MM-dd");
            JTextField checkOutField = new JTextField("yyyy-MM-dd");

            // Auto-fill rate based on room type
            typeBox.addActionListener(a -> {
                String selected = (String) typeBox.getSelectedItem();
                if (selected.equals("Patio View")) {
                    rateField.setText("70.0");
                } else {
                    rateField.setText("90.0");
                }
            });

            Object[] fields = {
                    "Guest Name:", nameField,
                    "Guest Email:", emailField,
                    "Room Number (1-18):", roomField,
                    "Room Type:", typeBox,
                    "Rate (auto):", rateField,
                    "Check-In Date (yyyy-MM-dd):", checkInField,
                    "Check-Out Date (yyyy-MM-dd):", checkOutField
            };

            int result = JOptionPane.showConfirmDialog(this, fields, "Add Reservation", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Validate name (no digits)
                    String name = nameField.getText().trim();
                    if (!name.matches("[a-zA-Z ]+")) throw new IllegalArgumentException("Name must contain only letters and spaces.");

                    // Validate email
                    String email = emailField.getText().trim();
                    if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) throw new IllegalArgumentException("Invalid email format.");

                    // Validate room number
                    int room = Integer.parseInt(roomField.getText().trim());
                    if (room < 1 || room > 18) throw new IllegalArgumentException("Room number must be between 1 and 18.");

                    // Room type and rate
                    String type = (String) typeBox.getSelectedItem();
                    double rate = Double.parseDouble(rateField.getText());

                    // Dates
                    String checkIn = checkInField.getText().trim();
                    String checkOut = checkOutField.getText().trim();
                    java.time.LocalDate inDate = java.time.LocalDate.parse(checkIn);
                    java.time.LocalDate outDate = java.time.LocalDate.parse(checkOut);
                    if (!outDate.isAfter(inDate)) throw new IllegalArgumentException("Check-out must be after check-in.");

                    int nights = (int) java.time.temporal.ChronoUnit.DAYS.between(inDate, outDate);

                    dbManager.addReservation(name, email, room, type, rate, nights, checkIn, checkOut);
                    displayArea.setText("Reservation added successfully!");

                } catch (Exception ex) {
                    displayArea.setText("Error: " + ex.getMessage());
                }
            }
        });

        // Delete Reservation by ID
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (!dbManager.isConnected()) {
                displayArea.setText("Please connect to a database first.");
                return;
            }
            String input = JOptionPane.showInputDialog(this, "Enter Reservation ID to delete:");
            try {
                int id = Integer.parseInt(input);                     // Convert to int
                boolean success = dbManager.deleteReservation(id);    // Try to delete
                displayArea.setText(success ? "Reservation deleted!" : "ID not found.");
            } catch (Exception ex) {
                displayArea.setText("Invalid ID.");                   // Input error
            }
        });

        // Update Reservation by ID
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            if (!dbManager.isConnected()) {
                displayArea.setText("Please connect to a database first.");
                return;
            }
            String input = JOptionPane.showInputDialog(this, "Enter Reservation ID to update:");
            try {
                int id = Integer.parseInt(input);

                JTextField nameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField roomField = new JTextField();
                String[] roomTypes = {"Patio View", "City View"};
                JComboBox<String> typeBox = new JComboBox<>(roomTypes);
                JTextField rateField = new JTextField();
                rateField.setEditable(false); // Auto-filled
                JTextField checkInField = new JTextField("yyyy-MM-dd");
                JTextField checkOutField = new JTextField("yyyy-MM-dd");

                // Auto-fill rate based on room type
                typeBox.addActionListener(a -> {
                    String selected = (String) typeBox.getSelectedItem();
                    if (selected.equals("Patio View")) {
                        rateField.setText("70.0");
                    } else {
                        rateField.setText("90.0");
                    }
                });

                Object[] fields = {
                        "Guest Name:", nameField,
                        "Guest Email:", emailField,
                        "Room Number (1-18):", roomField,
                        "Room Type:", typeBox,
                        "Rate (auto):", rateField,
                        "Check-In Date (yyyy-MM-dd):", checkInField,
                        "Check-Out Date (yyyy-MM-dd):", checkOutField
                };

                int result = JOptionPane.showConfirmDialog(this, fields, "Update Reservation", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String name = nameField.getText().trim();
                        if (!name.matches("[a-zA-Z ]+")) throw new IllegalArgumentException("Name must contain only letters and spaces.");

                        String email = emailField.getText().trim();
                        if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) throw new IllegalArgumentException("Invalid email format.");

                        int room = Integer.parseInt(roomField.getText().trim());
                        if (room < 1 || room > 18) throw new IllegalArgumentException("Room number must be between 1 and 18.");

                        String type = (String) typeBox.getSelectedItem();
                        double rate = Double.parseDouble(rateField.getText());

                        String checkIn = checkInField.getText().trim();
                        String checkOut = checkOutField.getText().trim();
                        java.time.LocalDate inDate = java.time.LocalDate.parse(checkIn);
                        java.time.LocalDate outDate = java.time.LocalDate.parse(checkOut);
                        if (!outDate.isAfter(inDate)) throw new IllegalArgumentException("Check-out must be after check-in.");

                        int nights = (int) java.time.temporal.ChronoUnit.DAYS.between(inDate, outDate);

                        boolean success = dbManager.updateReservation(id, name, email, room, type, rate, nights, checkIn, checkOut);
                        displayArea.setText(success ? "Reservation updated!" : "Update failed. Check ID.");
                    } catch (Exception ex) {
                        displayArea.setText("Error: " + ex.getMessage());
                    }
                }

            } catch (NumberFormatException ex) {
                displayArea.setText("Invalid ID.");
            }
        });

        // Search by Guest Name
        JButton searchButton = new JButton("Search Name");
        searchButton.addActionListener(e -> {
            if (!dbManager.isConnected()) {
                displayArea.setText("Please connect to a database first.");
                return;
            }
            String name = JOptionPane.showInputDialog(this, "Enter guest name to search:");
            ArrayList<String> results = dbManager.searchReservationsByName(name);
            displayArea.setText("");
            for (String res : results) {
                displayArea.append(res + "\n\n");
            }
            if (results.isEmpty()) {
                displayArea.setText("No results found.");
            }
        });

        // Clear Output (Green Button)
        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.GREEN);
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(e -> displayArea.setText(""));

        // Exit Application (Red Button)
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0));

        // Add all buttons to the panel
        topPanel.add(connectDatabaseButton);
        topPanel.add(viewButton);
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(updateButton);
        topPanel.add(searchButton);
        topPanel.add(clearButton);
        topPanel.add(exitButton);

        add(topPanel, BorderLayout.NORTH); // Add button panel to the top

        setVisible(true); // Make the window visible
    }

    /**
     * Launches the Hotel DMS GUI application on the Event Dispatch Thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelGUI::new); // Run the GUI on the Event Dispatch Thread
    }
}
