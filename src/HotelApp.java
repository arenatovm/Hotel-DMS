/*This is the main class that simulates the Hotel DMS
it allows the user to interact via the console with basic options:
Add, List, Update, and Remove reservations.

Input: User inputs guest name/email, room info, etc
Output: Menu display and printed confirmations or errors
 */

import java.util.Scanner;

public class HotelApp {
    public static void main(String[] args) {
        //to scan user inputs
        Scanner scanner = new Scanner(System.in);
        //to create the manager that handle reservations
        ReservationManager manager = new ReservationManager();
        //to control loop
        boolean running = true;

        //to start app loop
        while (running) {
            //to display the menu
            System.out.println("\n*** Hotel DMS Menu ***");
            System.out.println("1. Add Reservation");
            System.out.println("2. List Reservations");
            System.out.println("3. Update Reservation");
            System.out.println("4. Remove Reservation");
            System.out.println("5. Exit");
            System.out.println("Enter your option: ");

            //to read user choice
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //to add reservation
                    System.out.println("Enter guest name: ");
                    String name = scanner.nextLine();

                    System.out.println("Enter guest email: ");
                    String email = scanner.nextLine();
                    //to validate room number because there are only 18 rooms in the hotel
                    int roomNumber;
                    do {
                        System.out.println("Enter room number (1-18 only): ");
                        roomNumber = scanner.nextInt();
                        if (roomNumber < 1 || roomNumber > 18) {
                            System.out.println("Invalid room number");
                        }
                    } while (roomNumber < 1 || roomNumber > 18);
                    scanner.nextLine();
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

                    //System.out.println("Enter nightly rate: ");
                    //double rate = scanner.nextDouble();
                    // Set nightly rate automatically based on room type
                    double rate = roomType.equals("City View") ? 90.0 : 70.0;

                    System.out.println("Enter number of nights: ");
                    int nights = scanner.nextInt();

                    //to create object based on input
                    Guest guest = new Guest(name, email);
                    Room room = new Room(roomNumber, type, rate);
                    Reservation reservation = new Reservation(guest, room, nights);

                    //to add reservation to the manager
                    manager.addReservation(reservation);
                    break;

                case 2:
                    //to list reservations
                    manager.listReservations();
                    break;

                case 3:
                    //to update reservation
                    System.out.println("Enter reservation index to update: ");
                    int updateIndex = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("New guest name: ");
                    String newName = scanner.nextLine();

                    System.out.println("New guest email: ");
                    String newEmail = scanner.nextLine();

                    //to validate room number because there are only 18 rooms in the hotel
                    int newRoomNumber;
                    do {
                        System.out.println("Enter room number (1-18 only): ");
                        newRoomNumber = scanner.nextInt();
                        if (newRoomNumber < 1 || newRoomNumber > 18) {
                            System.out.println("Invalid room number");
                        }
                    } while (newRoomNumber < 1 || newRoomNumber > 18);
                    scanner.nextLine();
                    //to validate Room type, there are only two types City View (C) or Patio View (P)
                    String newType;
                    do{
                        System.out.println("Enter room type (C for City View or P for Patio View): ");
                        newType = scanner.nextLine().trim().toUpperCase();
                        if (!newType.equals("C") && !newType.equals("P")){
                            System.out.println("Invalid room type! Please enter C or P");
                        }
                    } while (!newType.equals("C") && !newType.equals("P"));
                    //to set room type
                    String newRoomType = newType.equals("C") ? "City View" : "Patio View";

                    //System.out.println("New nightly rate: ");
                    //double newRate = scanner.nextDouble();
                    // Set nightly rate automatically based on room type
                    double newRate = newRoomType.equals("City View") ? 90.0 : 70.0;

                    System.out.println("Enter number of nights: ");
                    int newNights = scanner.nextInt();

                    //to created updated reservation
                    Guest newGuest = new Guest(newName, newEmail);
                    Room newRoom = new Room(newRoomNumber, newRoomType, newRate);
                    Reservation newReservation = new Reservation(newGuest, newRoom, newNights);

                    //to apply update
                    manager.updateReservation(updateIndex, newReservation);
                    break;

                case 4:
                    //to remove reservation
                    System.out.println("Enter reservation index to remove: ");
                    int removeIndex = scanner.nextInt();
                    manager.removeReservation(removeIndex);
                    break;

                case 5:
                    //exit
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Try again please.");
            }
        }

        scanner.close();
    }
}
