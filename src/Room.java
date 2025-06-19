/* This class represents a hotel room and holds information about its
type and rate. It is used by the Reservation class to associate a room
with a booking
Note: the hotel only has 18 rooms
 */

public class Room {
    //Attributes
    private int roomNumber;
    private String type;
    private double rate;

    //Constructor
    public Room(int roomNumber, String type, double rate) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.rate = rate;
    }

    //Getters & Setters
    public int getRoomNumber() {
        return roomNumber;
    }
    public String getType() {
        return type;
    }
    public double getRate() {
        return rate;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

    //Method
    @Override
    public String toString() {
        return "Room #" + roomNumber + ".\nType: " + type + ".\nRate: " + rate;
    }
}
