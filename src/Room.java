/**
 * Room.java
 *
 * <p>This class represents a hotel room used in the reservation system.
 * It holds the room number, type (e.g., "City View" or "Patio View"),
 * and the nightly rate.</p>
 *
 * <p>Each reservation references a specific room to complete the booking details.
 * This implementation assumes the hotel has a maximum of 18 rooms.</p>
 *
 * @author Andres Vera
 */

public class Room {
    /** The room number (1–18) */
    private int roomNumber;
    /** The type of the room (e.g., "City View", "Patio View") */
    private String type;
    /** The nightly rate for the room */
    private double rate;

    /**
     * Constructs a Room with the specified room number, type, and rate.
     *
     * @param roomNumber the room number (should be between 1–18)
     * @param type the type of the room
     * @param rate the price per night
     */
    public Room(int roomNumber, String type, double rate) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.rate = rate;
    }

    /**
     * Gets the room number.
     *
     * @return the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Gets the room type.
     *
     * @return the room type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the nightly rate.
     *
     * @return the nightly rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber the new room number
     */
    public void setRoomNumber(int roomNumber) {

        this.roomNumber = roomNumber;
    }

    /**
     * Sets the room type.
     *
     * @param type the new room type
     */
    public void setType(String type) {

        this.type = type;
    }

    /**
     * Sets the nightly rate.
     *
     * @param rate the new nightly rate
     */
    public void setRate(double rate) {

        this.rate = rate;
    }

    /**
     * Returns a string representation of the room's details.
     *
     * @return a string containing room number, type, and rate
     */
    @Override
    public String toString() {
        return "Room #" + roomNumber + ".\nType: " + type + ".\nRate: " + rate;
    }
}
