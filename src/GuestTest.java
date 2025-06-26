/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
GuestTest.java

This class tests the functionality of the Guest class,
including name and email handling and the toString method.
 */

import static org.junit.jupiter.api.Assertions.*;
class GuestTest {
    //variable to store a Guest object used in each test
    private Guest guest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //method runs before each test to create a sample guest
        guest = new Guest("Andres", "andres@gmail.com");
    }

    @org.junit.jupiter.api.Test
    void getName() {
        //this test checks if getName() returns expected value
        assertEquals("Andres", guest.getName());
    }

    @org.junit.jupiter.api.Test
    void getEmail() {
        //this test checks if getEmail() return the expected value
        assertEquals("andres@gmail.com", guest.getEmail());
    }

    @org.junit.jupiter.api.Test
    void setName() {
        //chenge the name using setName
        guest.setName("Kurt");
        //we check that the name was updated
        assertEquals("Kurt", guest.getName());
        //negative test to ensure no longer returns the previous name
        assertNotEquals("Andres", guest.getName());
    }

    @org.junit.jupiter.api.Test
    void setEmail() {
        // We change the email using setEmail
        guest.setEmail("kurt@aol.com");

        // Check the updated email
        assertEquals("kurt@aol.com", guest.getEmail());

        // Negative test: ensure old email is not returned
        assertNotEquals("andres@gmail.com", guest.getEmail());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        // Check if the toString method outputs correctly
        String expected = "Guest Name: Andres, email= andres@gmail.com";
        assertEquals(expected, guest.toString());

        // Negative test: output should not match an incorrect string
        assertNotEquals("Guest: Kurt", guest.toString());
    }

}