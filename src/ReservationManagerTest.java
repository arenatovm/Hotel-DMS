/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
ReservationManagerTest.java

This class tests the functionality of the ReservationManager class,
including adding, removing, updating, retrieving reservations, search reservation by name, and load file.
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

class ReservationManagerTest {

    private ReservationManager manager;
    private Guest guest;
    private Room room;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // Sample guest, room, and reservation for testing
        guest = new Guest("Andres", "andres@gmail.com");
        room = new Room(5, "Suite", 120.0);
        reservation = new Reservation(guest, room, 3);
        manager = new ReservationManager();
    }

    @Test
    void addReservation() {
        // Add reservation and verify it exists in the list
        manager.addReservation(reservation);
        assertEquals(1, manager.getReservations().size());
        assertEquals(reservation, manager.getReservations().get(0));
    }

    @Test
    void removeReservation() {
        // Add and remove reservation, then check size is 0
        manager.addReservation(reservation);
        manager.removeReservation(0);
        assertTrue(manager.getReservations().isEmpty());
    }

    @Test
    void updateReservation() {
        // Add one reservation and update it with a new one
        manager.addReservation(reservation);
        Reservation newReservation = new Reservation(
                new Guest("Maria", "maria@gmail.com"),
                new Room(6, "City View", 100.0),
                2
        );
        manager.updateReservation(0, newReservation);
        assertEquals("Maria", manager.getReservations().get(0).getGuest().getName());
    }

    @Test
    void getReservations() {
        // Add reservation and ensure getReservations returns expected list
        manager.addReservation(reservation);
        ArrayList<Reservation> list = manager.getReservations();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void searchReservationsByGuestName_found() {
        // Redirect console output to capture search result
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Add two sample reservations
        manager.addReservation(reservation);  // Andres
        manager.addReservation(new Reservation(
                new Guest("Maria", "maria@gmail.com"),
                new Room(6, "City View", 100.0),
                2
        ));

        manager.searchReservationsByGuestName("Andres");

        String output = outContent.toString();
        assertTrue(output.contains("Andres"));

        // Restore normal output
        System.setOut(System.out);
    }

    @Test
    void searchReservationsByGuestName_notFound() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Add sample reservation
        manager.addReservation(reservation);

        manager.searchReservationsByGuestName("Kurt");

        String output = outContent.toString();
        assertTrue(output.contains("No reservations found for guest: Kurt"));

        System.setOut(System.out);
    }

    @Test
    void loadFromFile_addsReservations() throws IOException {
        manager.loadFromFile("sample_data.txt"); // this file must exist in your project folder
        assertFalse(manager.getReservations().isEmpty());
        assertEquals("Andres", manager.getReservations().get(0).getGuest().getName());
    }


}
