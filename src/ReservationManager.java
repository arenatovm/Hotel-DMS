/**
 * ReservationManager.java
 *
 * <p>This class handles the management of reservations in memory.
 * It allows adding, removing, updating, listing, and searching reservations.
 * It also supports loading reservation data from a file.</p>
 *
 * <p>This class uses an ArrayList to store Reservation objects and provides
 * methods that simulate basic CRUD functionality for managing hotel bookings.</p>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * ReservationManager manager = new ReservationManager();
 * manager.addReservation(new Reservation(...));
 * manager.listReservations();
 * }</pre>
 *
 * @author Andres Vera
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReservationManager {
    /** A list that stores all reservation objects */
    private ArrayList<Reservation> reservations;

    /**
     * Constructs a new ReservationManager with an empty reservation list.
     */
    public ReservationManager() {
        reservations = new ArrayList<>();
    }

    /**
     * Adds a new reservation to the list.
     *
     * @param reservation the Reservation object to be added
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added!");
    }

    /**
     * Lists all reservations in the system to the console.
     */
    public void listReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found!");
        } else {
            for (int i = 0; i < reservations.size(); i++) {
                System.out.println(i + ": " + reservations.get(i));
            }
        }
    }

    /**
     * Removes a reservation by its index.
     *
     * @param index the index of the reservation to remove
     */
    public void removeReservation(int index){
        if (index >= 0 && index < reservations.size()) {
            reservations.remove(index);
            System.out.println("Reservation removed!");
        } else  {
            System.out.println("Invalid Index!");
        }
    }

    /**
     * Updates a reservation at the given index with a new reservation.
     *
     * @param index the index of the reservation to update
     * @param newReservation the new Reservation object to replace the old one
     */
    public void updateReservation(int index, Reservation newReservation) {
        if (index >= 0 && index < reservations.size()) {
            reservations.set(index, newReservation);
            System.out.println("Reservation updated!");
        } else {
            System.out.println("Invalid Index!");
        }
    }

    /**
     * Loads reservations from a text file. Each line must be formatted as:
     * name,email,roomNumber,roomType,nights
     *
     * @param filename the name of the file to load from
     * @throws IOException if the file format is incorrect or cannot be read
     */
    public void loadFromFile(String filename) throws IOException {
        // throws FileNotFoundException if file doesn't exist
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int lineNumber = 0;
        // clear existing data if needed
        reservations.clear();

        while ((line = br.readLine()) != null) {
            lineNumber++;
            String[] parts = line.split(",");
            if (parts.length != 5) {
                throw new IOException("Invalid format at line " + lineNumber + ": " + line);
            }

            String name = parts[0].trim();
            String email = parts[1].trim();
            int roomNumber = Integer.parseInt(parts[2].trim());
            String roomType = parts[3].trim();
            int nights = Integer.parseInt(parts[4].trim());

            double rate = roomType.equalsIgnoreCase("City View") ? 90.0 : 70.0;

            Guest guest = new Guest(name, email);
            Room room = new Room(roomNumber, roomType, rate);
            Reservation reservation = new Reservation(guest, room, nights);

            addReservation(reservation);
        }

        br.close();
    }


    /**
     * Searches for reservations by guest name. Prints matches to the console.
     *
     * @param name the partial or full name to search for
     */
    public void searchReservationsByGuestName(String name) {
        boolean found = false;
        for (int i = 0; i < reservations.size(); i++) {
            //to find a partial name
            if (reservations.get(i).getGuest().getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(i + ": " + reservations.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No reservations found for guest: " + name);
        }
    }

    /**
     * Gets the list of all reservations.
     *
     * @return an ArrayList of Reservation objects
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}
