/**
 * RoomTest.java
 *
 * <p>This test class verifies the behavior of the {@link Room} class, specifically the correctness of
 * the overridden {@code toString()} method.</p>
 *
 * <p>It uses JUnit 5 framework to validate expected and unexpected outputs.</p>
 *
 * @author Andres Vera
 */

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    /** A Room object used for test cases */
    private Room room;

    /**
     * Tests the output of the {@code toString()} method in the Room class.
     * Validates both correct output and a case that should fail to match.
     */
    @org.junit.jupiter.api.Test
    void testToString() {
        //create room object
        room = new Room(5, "City View", 90.0);

        // Expected string from toString()
        String expected = "Room #5.\nType: City View.\nRate: 90.0";

        // Positive test
        assertEquals(expected, room.toString());

        // Negative test
        assertNotEquals("Room #1.\nType: Garden View.\nRate: 50.0", room.toString());
    }
}
