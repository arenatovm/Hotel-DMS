/**
 * ReservationTest.java
 *
 * <p>This class contains unit tests for the {@link Reservation} class. It verifies the
 * correctness of the total cost calculation and the output of the {@code toString()} method.</p>
 *
 * <p>It uses the JUnit 5 framework to test expected behavior through assertions.</p>
 *
 * <p><b>Tested Methods:</b><br>
 * {@link Reservation#calculateTotal()}<br>
 * {@link Reservation#toString()}</p>
 *
 * @author Andres Vera
 */

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    /** Test guest used for reservation */
    private Guest guest;
    /** Test room used for reservation */
    private Room room;
    /** Reservation object used in tests */
    private Reservation reservation;

    /**
     * Tests the total cost calculation of a reservation.
     * Validates that the result is correct and does not match an incorrect value.
     */
    @org.junit.jupiter.api.Test
    void calculateTotal() {
        //test data
        guest = new Guest("Andres", "andres@gmail.com");
        room = new Room(5, "City View", 90.0);
        reservation = new Reservation(guest, room, 3);
        // Positive test: 90 * 3 = 270
        assertEquals(270.0, reservation.calculateTotal());

        // Negative test: result should not be incorrect
        assertNotEquals(300.0, reservation.calculateTotal());
    }

    /**
     * Tests the string output of a reservation using toString().
     * Verifies that the output matches the expected format.
     */
    @org.junit.jupiter.api.Test
    void testToString() {
        //test data
        guest = new Guest("Andres", "andres@gmail.com");
        room = new Room(5, "City View", 90.0);
        reservation = new Reservation(guest, room, 3);
        // Build the expected string based on guest, room, and total
        String expected = guest.toString() + " *** " + room.toString() +
                " *** Nights:3 *** Total: $270.0";

        // Positive test: check if the output is as expected
        assertEquals(expected, reservation.toString());

        // Negative test: ensure it doesn't match incorrect string
        assertNotEquals("Incorrect output", reservation.toString());
    }
}
