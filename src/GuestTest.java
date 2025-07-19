/**
 * GuestTest.java
 *
 * <p>This class contains unit tests for the {@link Guest} class using JUnit 5.
 * Specifically, it validates the behavior of the {@code toString()} method.</p>
 *
 * <p>These tests ensure that guest information is formatted correctly and that
 * incorrect formats are properly identified.</p>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * Guest guest = new Guest("Andres", "andres@gmail.com");
 * String output = guest.toString();
 * // Expected: "Guest Name: Andres, email= andres@gmail.com"
 * }</pre>
 *
 * @author Andres
 */

import static org.junit.jupiter.api.Assertions.*;
class GuestTest {
    /** Guest object used in the test */
    private Guest guest;

    /**
     * Tests the {@code toString()} method of the {@link Guest} class.
     * <ul>
     *     <li>Verifies correct string output.</li>
     *     <li>Ensures it does not match an incorrect format.</li>
     * </ul>
     */
    @org.junit.jupiter.api.Test
    void testToString() {
        //initialize guest object
        guest = new Guest("Andres", "andres@gmail.com");

        // Check if the toString method outputs correctly
        String expected = "Guest Name: Andres, email= andres@gmail.com";
        assertEquals(expected, guest.toString());

        // Negative test: output should not match an incorrect string
        assertNotEquals("Guest: Kurt", guest.toString());
    }

}