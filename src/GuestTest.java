/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
GuestTest.java

This class tests the functionality of toString method in the Guest class.
 */

import static org.junit.jupiter.api.Assertions.*;
class GuestTest {
    //variable to store a Guest object used in each test
    private Guest guest;

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