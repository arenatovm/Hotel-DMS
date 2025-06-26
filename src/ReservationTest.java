/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
ReservationTest.java

This class tests the functionality of the Reservation class,
including guest, room, number of nights, total calculation, and the toString method.
 */

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    // Variables used across tests
    private Guest guest;
    private Room room;
    private Reservation reservation;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // Create a sample guest and a room (room number is valid: 5)
        guest = new Guest("Andres", "andres@gmail.com");
        room = new Room(5, "City View", 90.0);
        reservation = new Reservation(guest, room, 3); // Staying for 3 nights
    }

    @org.junit.jupiter.api.Test
    void getGuest() {
        // Check if the guest object is correctly returned
        assertEquals(guest, reservation.getGuest());
    }

    @org.junit.jupiter.api.Test
    void getRoom() {
        // Check if the room object is correctly returned
        assertEquals(room, reservation.getRoom());
    }

    @org.junit.jupiter.api.Test
    void getNights() {
        // Check if the correct number of nights is returned
        assertEquals(3, reservation.getNights());
    }

    @org.junit.jupiter.api.Test
    void calculateTotal() {
        // Positive test: 90 * 3 = 270
        assertEquals(270.0, reservation.calculateTotal());

        // Negative test: result should not be incorrect
        assertNotEquals(300.0, reservation.calculateTotal());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        // Build the expected string based on guest, room, and total
        String expected = guest.toString() + " *** " + room.toString() +
                " *** Nights:3 *** Total: $270.0";

        // Positive test: check if the output is as expected
        assertEquals(expected, reservation.toString());

        // Negative test: ensure it doesn't match incorrect string
        assertNotEquals("Incorrect output", reservation.toString());
    }
}
