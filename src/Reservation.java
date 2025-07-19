/**
 * Reservation.java
 *
 * <p>This class represents a reservation in the hotel system. It connects a guest with a specific room
 * and tracks the number of nights the guest will stay.</p>
 *
 * <p>It includes logic to calculate the total cost of the reservation based on the room rate
 * and number of nights.</p>
 *
 * <p><b>Input:</b> Guest information, room details, number of nights<br>
 * <b>Output:</b> Total cost and full reservation details</p>
 *
 * @author Andres Vera
 */

public class Reservation {
    /** Guest making the reservation */
    private Guest guest;
    /** Room that the guest is reserving */
    private Room room;
    /** Number of nights the guest will stay */
    private int nights;

    /**
     * Constructs a Reservation object with a guest, room, and number of nights.
     *
     * @param guest the guest making the reservation
     * @param room the room assigned to the guest
     * @param nights the number of nights the guest will stay
     */
    public Reservation(Guest guest, Room room, int nights) {
        this.guest = guest;
        this.room = room;
        this.nights = nights;
    }

    /**
     * Gets the guest associated with this reservation.
     *
     * @return the guest object
     */
    public Guest getGuest() {

        return guest;
    }

    /**
     * Gets the room associated with this reservation.
     *
     * @return the room object
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Gets the number of nights in this reservation.
     *
     * @return number of nights
     */
    public int getNights() {

        return nights;
    }

    /**
     * Calculates the total cost of the reservation.
     *
     * @return total cost as a double
     */
    public double calculateTotal(){
        return room.getRate() *  nights;
    }

    /**
     * Returns a formatted string representation of the reservation,
     * including guest info, room details, nights, and total cost.
     *
     * @return string summary of the reservation
     */
    @Override
    public String toString() {
        return guest.toString() + " *** " +  room.toString() +
                " *** Nights:" + nights + " *** Total: $" + calculateTotal();
    }

}
