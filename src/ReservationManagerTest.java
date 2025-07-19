/**
 * ReservationManagerTest.java
 *
 * <p>This class contains unit tests for the {@link ReservationManager} class.</p>
 *
 * <p>It tests all the major methods of the ReservationManager class including:
 * adding, removing, updating, retrieving, searching, and loading reservations from a file.</p>
 *
 * <p>JUnit 5 annotations are used to structure and manage the test lifecycle.</p>
 *
 * <p><b>Test File Used:</b> sample_data.txt (must exist in project root)</p>
 *
 * @author Andres Vera
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

class ReservationManagerTest {
    /** Instance of ReservationManager used for testing */
    private ReservationManager manager;
    /** Sample Guest object */
    private Guest guest;
    /** Sample Room object */
    private Room room;
    /** Sample Reservation object */
    private Reservation reservation;

    /**
     * Initializes sample guest, room, and reservation before each test.
     */
    @BeforeEach
    void setUp() {
        // Sample guest, room, and reservation for testing
        guest = new Guest("Andres", "andres@gmail.com");
        room = new Room(5, "Suite", 120.0);
        reservation = new Reservation(guest, room, 3);
        manager = new ReservationManager();
    }

    /**
     * Tests the {@code addReservation()} method by adding a reservation and checking list size and content.
     */
    @Test
    void addReservation() {
        // Add reservation and verify it exists in the list
        manager.addReservation(reservation);
        assertEquals(1, manager.getReservations().size());
        assertEquals(reservation, manager.getReservations().get(0));
    }

    /**
     * Tests the {@code removeReservation()} method by removing an added reservation.
     */
    @Test
    void removeReservation() {
        // Add and remove reservation, then check size is 0
        manager.addReservation(reservation);
        manager.removeReservation(0);
        assertTrue(manager.getReservations().isEmpty());
    }

    /**
     * Tests the {@code updateReservation()} method by modifying an existing reservation.
     */
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

    /**
     * Tests the {@code getReservations()} method by checking if list returns expected data.
     */
    @Test
    void getReservations() {
        // Add reservation and ensure getReservations returns expected list
        manager.addReservation(reservation);
        ArrayList<Reservation> list = manager.getReservations();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    /**
     * Tests the {@code searchReservationsByGuestName()} method when a match is found.
     */
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

    /**
     * Tests the {@code searchReservationsByGuestName()} method when no match is found.
     */
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

    /**
     * Tests the {@code loadFromFile()} method with a sample text file.
     * <p>Note: This test assumes 'sample_data.txt' exists in the project folder and is properly formatted.</p>
     *
     * @throws IOException if the file cannot be read or parsed.
     */
    @Test
    void loadFromFile_addsReservations() throws IOException {
        manager.loadFromFile("sample_data.txt"); // this file must exist in your project folder
        assertFalse(manager.getReservations().isEmpty());
        assertEquals("Andres", manager.getReservations().get(0).getGuest().getName());
    }


}
