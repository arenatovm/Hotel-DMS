/*
Andres Vera
CEN 3024 - Software Development I
06/25/25
ReservationManagerTest.java

This class tests the functionality of the ReservationManager class,
including adding, removing, updating, and retrieving reservations.
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
}
