/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
RoomTest.java

This class tests the functionality of the Room class,
including room number, type, rate, and toString output.
 */

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    // Variable to store a Room object used in each test
    private Room room;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // This method runs before each test to create a sample room
        room = new Room(5, "City View", 90.0);
    }

    @org.junit.jupiter.api.Test
    void getRoomNumber() {
        // Test if getRoomNumber() returns the correct number
        assertEquals(5, room.getRoomNumber());
    }

    @org.junit.jupiter.api.Test
    void getType() {
        // Test if getType() returns the correct type
        assertEquals("City View", room.getType());
    }

    @org.junit.jupiter.api.Test
    void getRate() {
        // Test if getRate() returns the correct rate
        assertEquals(90.0, room.getRate());
    }

    @org.junit.jupiter.api.Test
    void setRoomNumber() {
        // Change the room number
        room.setRoomNumber(10);

        // Confirm the new value
        assertEquals(10, room.getRoomNumber());

        // Negative test
        assertNotEquals(5, room.getRoomNumber());
    }

    @org.junit.jupiter.api.Test
    void setType() {
        // Change the type
        room.setType("Patio View");

        // Confirm the new value
        assertEquals("Patio View", room.getType());

        // Negative test
        assertNotEquals("City View", room.getType());
    }

    @org.junit.jupiter.api.Test
    void setRate() {
        // Change the rate
        room.setRate(75.0);

        // Confirm the new value
        assertEquals(75.0, room.getRate());

        // Negative test
        assertNotEquals(90.0, room.getRate());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        // Expected string from toString()
        String expected = "Room #5.\nType: City View.\nRate: 90.0";

        // Positive test
        assertEquals(expected, room.toString());

        // Negative test
        assertNotEquals("Room #1.\nType: Garden View.\nRate: 50.0", room.toString());
    }
}
