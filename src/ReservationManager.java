/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
ReservationManager.java

 This class allows adding, removing, updating, listing reservations, search a reservatioin by name, and load a file.
it handles storing and managing reservations

 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReservationManager {
    //List to store all reservations
    private ArrayList<Reservation> reservations;

    //Constructor to initializes the list
    public ReservationManager() {
        reservations = new ArrayList<>();
    }

    //To add a new reservation to the list
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added!");
    }
    //To list all reservations in the system
    public void listReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found!");
        } else {
            for (int i = 0; i < reservations.size(); i++) {
                System.out.println(i + ": " + reservations.get(i));
            }
        }
    }
    //To remove a reservation using its index
    public void removeReservation(int index){
        if (index >= 0 && index < reservations.size()) {
            reservations.remove(index);
            System.out.println("Reservation removed!");
        } else  {
            System.out.println("Invalid Index!");
        }
    }

    //To update a reservation using index of new data
    public void updateReservation(int index, Reservation newReservation) {
        if (index >= 0 && index < reservations.size()) {
            reservations.set(index, newReservation);
            System.out.println("Reservation updated!");
        } else {
            System.out.println("Invalid Index!");
        }
    }

    //this is a new block so user can load a file
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                System.out.println("Reading line " + lineNumber + ": " + line); // debug

                String[] parts = line.split(","); // split on comma
                if (parts.length != 5) {
                    System.out.println("Invalid line format at line " + lineNumber + ": " + line);
                    continue;
                }
                //to trim info from file, so blank spaces don't affect the file format
                String name = parts[0].trim();
                String email = parts[1].trim();
                int roomNumber = Integer.parseInt(parts[2].trim());
                String roomType = parts[3].trim(); // "City View" or "Patio View"
                int nights = Integer.parseInt(parts[4].trim());

                // Assign room rate based on type
                double rate = roomType.equalsIgnoreCase("City View") ? 90.0 : 70.0;

                Guest guest = new Guest(name, email);
                Room room = new Room(roomNumber, roomType, rate);
                Reservation reservation = new Reservation(guest, room, nights);

                addReservation(reservation);
            }

            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error: File not found or cannot be opened.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid data format in file.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    //new block, a custom action. To search a reservation by guest name
    public void searchReservationsByGuestName(String name) {
        boolean found = false;
        for (int i = 0; i < reservations.size(); i++) {
            //o find a partial name
            if (reservations.get(i).getGuest().getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(i + ": " + reservations.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No reservations found for guest: " + name);
        }
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}
