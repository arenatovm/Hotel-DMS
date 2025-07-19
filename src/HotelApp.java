/**
 * HotelApp.java
 * Andres Vera
 * CEN 3024 - Software Development I
 * 07/18/25
 *
 *
 * <p>This is the main entry point for the Hotel Data Management System (Hotel DMS).
 * It provides a console-based interface where users can interact with the reservation system.
 * Users can add, update, remove, and list reservations, load them from a file, and search by guest name.</p>
 *
 * <p>Main features include:</p>
 * <ul>
 *     <li>Input validation for guest details and room information</li>
 *     <li>Dynamic calculation of rates based on room type</li>
 *     <li>Integration with file loading functionality</li>
 *     <li>Search feature to locate reservations by guest name</li>
 * </ul>
 *
 * <p><b>Input:</b> Console inputs from the user such as guest name, email, room number/type, and nights</p>
 * <p><b>Output:</b> Printed confirmations, reservation summaries, and error messages in the console</p>
 *
 * @author Andres Vera
 */

import java.io.IOException;
import java.util.Scanner;

public class HotelApp {
    /**
     * The main method that launches the Hotel DMS system.
     * <p>
     * Provides a menu for the user to manage hotel reservations including:
     * adding, listing, updating, removing, loading from file, and searching.
     * </p>
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        //to scan user inputs
        Scanner scanner = new Scanner(System.in);
        //to create the manager that handle reservations
        ReservationManager manager = new ReservationManager();
        //to control loop
        boolean running = true;
        String input;

        //to start app loop
        while (running) {
            //to display the menu
            System.out.println("\n*** Hotel DMS Menu ***");
            System.out.println("1. Add Reservation");
            System.out.println("2. List Reservations");
            System.out.println("3. Update Reservation");
            System.out.println("4. Remove Reservation");
            System.out.println("5. Load File");
            System.out.println("6. Search Reservation by Guest Name");
            System.out.println("7. Exit");
            System.out.println("Enter your option: ");

            //to read user choice (this block was updated after teacher feedback)fd
            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                scanner.nextLine(); // consume invalid input
                continue; // skip to the next loop iteration
            }


            switch (choice) {
                /**
                 * Adds a new reservation after validating guest name, email,
                 * room number, room type, and number of nights.
                 */
                case 1:

                    String name;
                    do {
                        System.out.println("Enter guest name (letters and spaces only): ");
                        name = scanner.nextLine().trim();

                        if (!name.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Invalid name! Please use letters and spaces only.");
                            name = null; // force retry
                        }
                    } while (name == null);


                    //to add email address (updated allowing email format input only)d

                    String email;
                    do {
                        System.out.println("Enter guest email (e.g., name@example.com): ");
                        email = scanner.nextLine().trim();

                        // Basic email pattern
                        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                            System.out.println("Invalid email format! Try again.");
                            email = null; // force retry
                        }
                    } while (email == null);

                    //to validate room number because there are only 18 rooms in the hotel
                    //(updated to allow only numbers between 1-18)
                    int roomNumber = -1;
                    while (true) {
                        System.out.println("Enter room number (1-18 only): ");
                        input = scanner.nextLine().trim();

                        try {
                            roomNumber = Integer.parseInt(input);
                            if (roomNumber >= 1 && roomNumber <= 18) {
                                break;
                            } else {
                                System.out.println("Room number must be between 1 and 18.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }


                    //to validate Room type, there are only two types City View (C) or Patio View (P)
                    String type;
                    do{
                        System.out.println("Enter room type (C for City View or P for Patio View): ");
                        type = scanner.nextLine().trim().toUpperCase();
                        if (!type.equals("C") && !type.equals("P")){
                            System.out.println("Invalid room type! Please enter C or P");
                        }
                    } while (!type.equals("C") && !type.equals("P"));
                    //to set room type
                    String roomType = type.equals("C") ? "City View" : "Patio View";


                    // Set nightly rate automatically based on room type
                    double rate = roomType.equals("City View") ? 90.0 : 70.0;

                    //to enter number of nights to stay
                    int nights;
                    while (true) {
                        System.out.print("Enter number of nights: ");
                        input = scanner.nextLine().trim();
                        try {
                            nights = Integer.parseInt(input);
                            if (nights > 0) break;
                            System.out.println("Must be at least 1 night.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter a whole number.");
                        }
                    }

                    //to create object based on input
                    Guest guest = new Guest(name, email);
                    Room room = new Room(roomNumber, type, rate);
                    Reservation reservation = new Reservation(guest, room, nights);

                    //to add reservation to the manager
                    manager.addReservation(reservation);
                    break;
                /**
                 * Lists all existing reservations.
                 */
                case 2:
                    //to list reservations
                    manager.listReservations();
                    break;
                /**
                 * Updates an existing reservation after validating index and new inputs.
                 */
                case 3:
                    // Validate reservation index
                    int updateIndex = -1;
                    while (true) {
                        System.out.print("Enter reservation index to update: ");
                        String indexInput = scanner.nextLine();
                        try {
                            updateIndex = Integer.parseInt(indexInput);
                            if (updateIndex >= 0 && updateIndex < manager.getReservations().size()) {
                                break;
                            } else {
                                System.out.println("Invalid index! Please choose between 0 and " + (manager.getReservations().size() - 1));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    // Validate new guest name
                    String newName;
                    do {
                        System.out.println("New guest name (letters and spaces only): ");
                        newName = scanner.nextLine().trim();
                        if (!newName.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Invalid name! Please use letters and spaces only.");
                            newName = null;
                        }
                    } while (newName == null);

                    // Validate new email
                    String newEmail;
                    do {
                        System.out.println("New guest email (e.g., name@example.com): ");
                        newEmail = scanner.nextLine().trim();
                        if (!newEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                            System.out.println("Invalid email format! Try again.");
                            newEmail = null;
                        }
                    } while (newEmail == null);

                    // Validate new room number
                    int newRoomNumber = -1;
                    while (true) {
                        System.out.print("Enter new room number (1–18): ");
                        String roomInput = scanner.nextLine().trim();
                        try {
                            newRoomNumber = Integer.parseInt(roomInput);
                            if (newRoomNumber >= 1 && newRoomNumber <= 18) {
                                break;
                            } else {
                                System.out.println("Room number must be between 1 and 18.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }

                    // Validate new room type
                    String newType;
                    do {
                        System.out.println("Enter room type (C for City View or P for Patio View): ");
                        newType = scanner.nextLine().trim().toUpperCase();
                        if (!newType.equals("C") && !newType.equals("P")) {
                            System.out.println("Invalid room type! Please enter C or P.");
                        }
                    } while (!newType.equals("C") && !newType.equals("P"));
                    String newRoomType = newType.equals("C") ? "City View" : "Patio View";

                    // Set new rate automatically
                    double newRate = newRoomType.equals("City View") ? 90.0 : 70.0;

                    // Validate new number of nights
                    int newNights;
                    while (true) {
                        System.out.print("Enter number of nights: ");
                        String nightInput = scanner.nextLine().trim();
                        try {
                            newNights = Integer.parseInt(nightInput);
                            if (newNights > 0) break;
                            System.out.println("Must be at least 1 night.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter a whole number.");
                        }
                    }

                    // Create and apply updated reservation
                    Guest newGuest = new Guest(newName, newEmail);
                    Room newRoom = new Room(newRoomNumber, newRoomType, newRate);
                    Reservation newReservation = new Reservation(newGuest, newRoom, newNights);
                    manager.updateReservation(updateIndex, newReservation);
                    System.out.println("Reservation updated successfully.");
                    break;

                /**
                 * Removes a reservation at a specified index after validation.
                 */
                case 4:
                    // Validate reservation index to remove
                    int removeIndex = -1;
                    while (true) {
                        System.out.print("Enter reservation index to remove: ");
                        String removeInput = scanner.nextLine();
                        try {
                            removeIndex = Integer.parseInt(removeInput);
                            if (removeIndex >= 0 && removeIndex < manager.getReservations().size()) {
                                break;
                            } else {
                                System.out.println("Invalid index! Please choose between 0 and " + (manager.getReservations().size() - 1));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    // Perform removal
                    manager.removeReservation(removeIndex);
                    System.out.println("Reservation removed successfully.");
                    break;
                /**
                 * Loads reservations from an external file.
                 */
                case 5:
                    System.out.println("Enter the file path to load reservations:");
                    String filePath = scanner.nextLine().trim();

                    try {
                        manager.loadFromFile(filePath); // Attempt to load the file
                        System.out.println("File loaded successfully."); // Confirmation
                        manager.listReservations(); // Optional: Show loaded reservations
                    } catch (IOException e) {
                        System.out.println("Error loading file: " + e.getMessage()); // Show error
                    }

                    break;

                /**
                 * Searches reservations by guest name.
                 */
                case 6:
                    System.out.print("Enter guest name to search: ");
                    String searchName = scanner.nextLine().trim();
                    manager.searchReservationsByGuestName(searchName);
                    break;
                /**
                 * Exits the program.
                 */
                case 7:
                    //exit
                    running = false;
                    System.out.println("Goodbye!");
                    break;


                /**
                 * Handles invalid menu option.
                 */
                default:
                    System.out.println("Invalid option! Try again please.");
            }
        }

        scanner.close();
    }
}
