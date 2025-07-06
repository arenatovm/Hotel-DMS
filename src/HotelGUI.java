/*
Andres Vera
CEN 3024 - Software Development I
07/05/25
HotelGUI.java

This class provides a GUI for the Hotel DMS. It allows users to
interact with the guest bookings using buttons and input fields, it
will also allow all CRUD operations, load file, and the custom action of searching
a reservation by a name.
 */

import javax.swing.*;
import java.awt.*;

public class HotelGUI extends JFrame {
    private ReservationManager manager;
    private JTextArea displayArea;


    public HotelGUI() {
        //initialize manger
        manager = new ReservationManager();
        //Set Title and settings
        setTitle("Hotel DMS GUI");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //main layout
        setLayout(new BorderLayout());
        //Menu with buttons
        JPanel topPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JButton loadFileButton = new JButton("Load File");
        JButton listButton = new JButton("List Reservations");
        JButton createButton = new JButton("Create Reservation");
        JButton updateButton = new JButton("Update Reservation");
        JButton removeButton = new JButton("Remove Reservation");
        JButton searchButton = new JButton("Search by Name");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");
        //adding colors to clear button
        clearButton.setBackground(Color.GREEN);
        clearButton.setForeground(Color.WHITE); // optional, to make text readable
        //adding color red to exit button
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE); // optional, to make text readable

        //adding buttons to the panel
        topPanel.add(loadFileButton);
        topPanel.add(listButton);
        topPanel.add(createButton);
        topPanel.add(updateButton);
        topPanel.add(removeButton);
        topPanel.add(searchButton);
        topPanel.add(clearButton);
        topPanel.add(exitButton);

        //Area to display results
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //Functionality buttons
        exitButton.addActionListener(e -> System.exit(0));
        clearButton.addActionListener(e -> displayArea.setText(""));

        //button to load a file
        loadFileButton.addActionListener(e -> {
            // Prompt user to enter the file name
            String filename = JOptionPane.showInputDialog(this, "Enter file name (e.g., sample_data.txt):");

            if (filename != null && !filename.trim().isEmpty()) {
                try {
                    // Attempt to load the file
                    manager.loadFromFile(filename.trim());
                    displayArea.append("File loaded successfully.\n");
                } catch (Exception ex) {
                    // Display error in the GUI instead of crashing
                    displayArea.append("Error reading file: " + ex.getMessage() + "\n");
                }
            } else {
                displayArea.append("No file name provided.\n");
            }
        });

        //list reservations buttons
        listButton.addActionListener(e -> {
            displayArea.setText(""); // clear previous results

            if (manager.getReservations().isEmpty()) {
                displayArea.append("No reservations found.\n");
            } else {
                for (int i = 0; i < manager.getReservations().size(); i++) {
                    displayArea.append(i + ": " + manager.getReservations().get(i).toString() + "\n");
                }
            }
        });

        //create/manual input button
        createButton.addActionListener(e -> {
            try {
                // Ask for guest name and validate it (must include at least one letter, only letters and spaces)
                String name;
                do {
                    name = JOptionPane.showInputDialog(this, "Enter guest name:");
                    if (name == null) return; // User cancelled

                    name = name.trim(); // Remove leading/trailing spaces

                    if (!name.matches(".*[a-zA-Z]+.*")) {
                        JOptionPane.showMessageDialog(this, "Invalid name. It must contain letters only and not be empty.");
                        name = null;
                    }
                } while (name == null);


                // Ask for guest email and validate it (simple email format)
                String email;
                do {
                    email = JOptionPane.showInputDialog(this, "Enter guest email:");
                    if (email == null) return;
                    if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        JOptionPane.showMessageDialog(this, "Invalid email format.");
                        email = null;
                    }
                } while (email == null);


                // Prompt user to enter the room number (as a string)
                String roomNumStr = JOptionPane.showInputDialog(this, "Enter room number:");
                int roomNumber = Integer.parseInt(roomNumStr.trim());  // Convert to integer

                // Provide the user with a dropdown menu to select room type
                String[] roomTypes = {"City View", "Patio View"};
                String roomType = (String) JOptionPane.showInputDialog(
                        this,                       // Parent component
                        "Select room type:",        // Message
                        "Room Type",                // Dialog title
                        JOptionPane.QUESTION_MESSAGE,
                        null,                       // Icon (none)
                        roomTypes,                  // Choices
                        roomTypes[0]                // Default selection
                );
                if (roomType == null)
                    throw new IllegalArgumentException("Room type is required.");

                // Prompt user to enter the number of nights
                String nightsStr = JOptionPane.showInputDialog(this, "Enter number of nights:");
                int nights = Integer.parseInt(nightsStr.trim());  // Convert to integer

                // Determine the room rate based on selected room type
                double rate = roomType.equalsIgnoreCase("City View") ? 90.0 : 70.0;

                // Create Guest, Room, and Reservation objects using user input
                Guest guest = new Guest(name, email);
                Room room = new Room(roomNumber, roomType, rate);
                Reservation reservation = new Reservation(guest, room, nights);

                // Add the new reservation to the ReservationManager
                manager.addReservation(reservation);

                // Display confirmation of the reservation in the text area
                displayArea.append("Reservation created:\n" + reservation + "\n");

            } catch (NumberFormatException nfe) {
                // Catches errors when room number or nights are not valid numbers
                displayArea.append("Error: Room number and nights must be numeric.\n");
            } catch (IllegalArgumentException iae) {
                // Catches custom errors when required fields are left blank
                displayArea.append("Error: " + iae.getMessage() + "\n");
            } catch (Exception ex) {
                // Catches any unexpected errors and displays them
                displayArea.append("Unexpected error: " + ex.getMessage() + "\n");
            }
        });

        //remove reservation form list button
        removeButton.addActionListener(e -> {
            try {
                // Prompt the user to enter the index (position) of the reservation to remove
                String indexStr = JOptionPane.showInputDialog(this, "Enter the reservation index to remove:");

                // Check if the user canceled or entered nothing
                if (indexStr == null || indexStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Reservation index is required.");
                }

                // Convert the entered string to an integer
                int index = Integer.parseInt(indexStr.trim());

                // Check if the index is within the range of the reservation list
                if (index < 0 || index >= manager.getReservations().size()) {
                    throw new IndexOutOfBoundsException("Invalid reservation index.");
                }

                // Get the reservation to be removed (to show a message with its info)
                Reservation removed = manager.getReservations().get(index);

                // Remove the reservation from the manager
                manager.removeReservation(index);

                // Confirm to the user that the reservation was removed
                displayArea.append("Reservation removed:\n" + removed + "\n");

            } catch (NumberFormatException nfe) {
                // If the user entered something that isn't a number
                displayArea.append("Error: Please enter a valid number for the reservation index.\n");
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                // For missing input or invalid index
                displayArea.append("Error: " + ex.getMessage() + "\n");
            } catch (Exception ex) {
                // Catch-all for unexpected issues
                displayArea.append("Unexpected error: " + ex.getMessage() + "\n");
            }
        });

        //update reservation button
        updateButton.addActionListener(e -> {
            try {
                // Ask user which reservation they want to update by index
                String indexStr = JOptionPane.showInputDialog(this, "Enter the index of the reservation to update:");
                if (indexStr == null || indexStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Reservation index is required.");
                }

                int index = Integer.parseInt(indexStr.trim());

                // Validate the index is within bounds
                if (index < 0 || index >= manager.getReservations().size()) {
                    throw new IndexOutOfBoundsException("Invalid reservation index.");
                }

                // Prompt for new guest name
                String name = JOptionPane.showInputDialog(this, "Enter new guest name:");
                if (name == null || name.trim().isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty.");
                }

                // Prompt for new guest email
                String email = JOptionPane.showInputDialog(this, "Enter new guest email:");
                if (email == null || email.trim().isEmpty()) {
                    throw new IllegalArgumentException("Email cannot be empty.");
                }

                // Prompt for new room number (must be between 1 and 18)
                String roomNumberStr = JOptionPane.showInputDialog(this, "Enter new room number (1–18):");
                int roomNumber = Integer.parseInt(roomNumberStr.trim());
                if (roomNumber < 1 || roomNumber > 18) {
                    throw new IllegalArgumentException("Room number must be between 1 and 18.");
                }

                // Prompt for room type (C for City View, P for Patio View)
                String type = JOptionPane.showInputDialog(this, "Enter room type (C for City View, P for Patio View):");
                if (type == null || (!type.equalsIgnoreCase("C") && !type.equalsIgnoreCase("P"))) {
                    throw new IllegalArgumentException("Invalid room type. Enter C or P.");
                }

                String roomType = type.equalsIgnoreCase("C") ? "City View" : "Patio View";
                double rate = roomType.equals("City View") ? 90.0 : 70.0;

                // Prompt for number of nights
                String nightsStr = JOptionPane.showInputDialog(this, "Enter number of nights:");
                int nights = Integer.parseInt(nightsStr.trim());
                if (nights <= 0) {
                    throw new IllegalArgumentException("Number of nights must be at least 1.");
                }

                // Create new reservation and update it at the given index
                Guest guest = new Guest(name, email);
                Room room = new Room(roomNumber, roomType, rate);
                Reservation newReservation = new Reservation(guest, room, nights);

                manager.updateReservation(index, newReservation);

                // Confirm update to user
                displayArea.append("Reservation updated successfully:\n" + newReservation + "\n");

            } catch (NumberFormatException nfe) {
                displayArea.append("Error: Please enter valid numeric values.\n");
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                displayArea.append("Error: " + ex.getMessage() + "\n");
            } catch (Exception ex) {
                displayArea.append("Unexpected error: " + ex.getMessage() + "\n");
            }
        });

        //search reservation by name button
        searchButton.addActionListener(e -> {
            try {
                // Ask the user to input a guest name to search
                String name = JOptionPane.showInputDialog(this, "Enter guest name to search:");

                // Check if the input was canceled or left blank
                if (name == null || name.trim().isEmpty()) {
                    throw new IllegalArgumentException("Guest name cannot be empty.");
                }

                // Prepare a StringBuilder to gather matching results
                StringBuilder results = new StringBuilder();
                boolean found = false;

                // Loop through all reservations
                for (int i = 0; i < manager.getReservations().size(); i++) {
                    Reservation res = manager.getReservations().get(i);

                    // Compare the reservation's guest name to the search term (case-insensitive)
                    if (res.getGuest().getName().toLowerCase().contains(name.toLowerCase())) {
                        results.append("[").append(i).append("] ").append(res).append("\n\n");
                        found = true;
                    }
                }

                // Display results or an error message if none were found
                if (found) {
                    displayArea.append("Search results for \"" + name + "\":\n" + results);
                } else {
                    displayArea.append("No reservations found for guest: " + name + "\n");
                }

            } catch (IllegalArgumentException ex) {
                displayArea.append("Error: " + ex.getMessage() + "\n");
            } catch (Exception ex) {
                displayArea.append("Unexpected error: " + ex.getMessage() + "\n");
            }
        });



        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelGUI::new);
    }
}
