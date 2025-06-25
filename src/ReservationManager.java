/* This class allows adding, removing, updating and listing reservations.
it handles storing and managing reservations

 */

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
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}
