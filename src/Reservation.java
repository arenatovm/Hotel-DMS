/* This class links a guest with a room and reservation dates. It calculates
the total cost and stores all booking details.

Input: guest making the reservation, the room being reserved and the
number of nights
Output: total cost and full reservation info
 */

public class Reservation {
    //Attributes associated with reservation
    private Guest guest;
    private Room room;
    private int nights;

    //Constructor
    public Reservation(Guest guest, Room room, int nights) {
        this.guest = guest;
        this.room = room;
        this.nights = nights;
    }

    //Getters
    public Guest getGuest() {
        return guest;
    }
    public Room getRoom() {
        return room;
    }
    public int getNights() {
        return nights;
    }

    //Methods
    //Calculate and returns the total cost of reservation
    public double calculateTotal(){
        return room.getRate() *  nights;
    }
    //Display reservation details
    @Override
    public String toString() {
        return guest.toString() + " *** " +  room.toString() +
                " *** Nights:" + nights + " *** Total: $" + calculateTotal();
    }

}
