/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
RoomTest.java

This class tests the functionality of toString method in the Room class.
 */

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    // Variable to store a Room object used in each test
    private Room room;

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
