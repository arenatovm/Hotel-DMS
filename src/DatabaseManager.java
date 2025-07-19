/**
 * DatabaseManager.java
 *
 * <p>This class handles all database operations for the Hotel Data Management System (Hotel DMS)
 * using SQLite. It allows connection to a user-specified SQLite database file and provides
 * methods to create tables, and perform Create, Read, Update, Delete (CRUD) operations,
 * as well as search functionality.</p>
 *
 * <p>Primary responsibilities:</p>
 * <ul>
 *     <li>Set and validate the database file path</li>
 *     <li>Connect to the database using JDBC</li>
 *     <li>Create the reservation table if it doesn't exist</li>
 *     <li>Insert, update, delete, list, and search reservations</li>
 * </ul>
 *
 * <p><b>Input:</b> File path to SQLite database and reservation fields from GUI</p>
 * <p><b>Output:</b> Changes persisted to the database and data returned for GUI display</p>
 *
 * @author Andres Vera
 */

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    /** Path to the SQLite database file set by the user */
    private String dbPath;

    /**
     * Sets the database path and ensures the reservations table exists.
     *
     * @param path The full file path to the SQLite database
     */
    public void setDatabasePath(String path) {
        // Save the file path
        this.dbPath = path;
        // Make sure the reservations table exists
        createTableIfNotExists();
    }

    /**
     * Checks if the database connection is available.
     *
     * @return {@code true} if the database path is set and not empty
     */
    public boolean isConnected() {
        return dbPath != null && !dbPath.isEmpty(); // Return true only if dbPath is not null or empty
    }

    /**
     * Connects to the SQLite database using the provided path.
     *
     * @return A valid {@link Connection} object
     * @throws SQLException If the connection fails or path is not set
     */
    private Connection connect() throws SQLException {
        if (!isConnected()) {
            throw new SQLException("Database path not set."); // Throw an error if no path set
        }
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath); // Return SQLite connection
    }

    /**
     * Creates the reservations table if it does not exist in the database.
     */
    public void createTableIfNotExists() {
        if (!isConnected()) return; // Do nothing if not connected

        // SQL statement for creating the table
        String sql = """
            CREATE TABLE IF NOT EXISTS reservations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                guest_name TEXT NOT NULL,
                guest_email TEXT NOT NULL,
                room_number INTEGER NOT NULL,
                room_type TEXT NOT NULL,
                rate REAL NOT NULL,
                nights INTEGER NOT NULL,
                check_in TEXT NOT NULL,
                check_out TEXT NOT NULL
            );
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // Execute SQL to create the table
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage()); // Show error if occurs
        }
    }

    /**
     * Inserts a new reservation into the database.
     *
     * @param name Guest name
     * @param email Guest email
     * @param roomNumber Room number
     * @param roomType Room type
     * @param rate Rate per night
     * @param nights Number of nights
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     */
    public void addReservation(String name, String email, int roomNumber, String roomType, double rate, int nights, String checkIn, String checkOut) {
        if (!isConnected()) return; // Do nothing if not connected

        // SQL insert statement using placeholders (to prevent SQL injection)
        String sql = """
            INSERT INTO reservations (guest_name, guest_email, room_number, room_type, rate, nights, check_in, check_out)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);         // Set guest name
            pstmt.setString(2, email);        // Set guest email
            pstmt.setInt(3, roomNumber);      // Set room number
            pstmt.setString(4, roomType);     // Set room type
            pstmt.setDouble(5, rate);         // Set nightly rate
            pstmt.setInt(6, nights);          // Set number of nights
            pstmt.setString(7, checkIn);      // Set check-in date
            pstmt.setString(8, checkOut);     // Set check-out date
            pstmt.executeUpdate();            // Execute the insert
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage()); // Print error if fails
        }
    }

    /**
     * Retrieves all reservations from the database.
     *
     * @return A list of formatted reservation strings
     */
    public ArrayList<String> getAllReservations() {
        ArrayList<String> reservations = new ArrayList<>(); // List to hold results
        if (!isConnected()) return reservations;            // Return empty list if not connected

        String sql = "SELECT * FROM reservations;";         // SQL select all statement

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { // Loop through each row returned
                String reservation = "[ID: " + rs.getInt("id") + "] "
                        + rs.getString("guest_name") + " (" + rs.getString("guest_email") + ")"
                        + " *** Room #" + rs.getInt("room_number") + " - " + rs.getString("room_type")
                        + " *** Nights: " + rs.getInt("nights")
                        + " *** Check-In: " + rs.getString("check_in")
                        + " *** Check-Out: " + rs.getString("check_out")
                        + " *** Total: $" + (rs.getDouble("rate") * rs.getInt("nights"));
                reservations.add(reservation); // Add reservation string to list
            }
        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage()); // Print error if fails
        }

        return reservations; // Return list of reservations
    }

    /**
     * Deletes a reservation from the database by ID.
     *
     * @param id Reservation ID to delete
     * @return {@code true} if deletion was successful, {@code false} otherwise
     */
    public boolean deleteReservation(int id) {
        if (!isConnected()) return false; // Return false if not connected

        String sql = "DELETE FROM reservations WHERE id = ?;"; // SQL delete statement

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Set ID to delete
            int affected = pstmt.executeUpdate(); // Execute delete
            return affected > 0; // Return true if row was deleted
        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage()); // Print error if fails
            return false; // Return false on failure
        }
    }

    /**
     * Updates an existing reservation in the database by ID.
     *
     * @param id Reservation ID to update
     * @param name Updated guest name
     * @param email Updated guest email
     * @param roomNumber Updated room number
     * @param roomType Updated room type
     * @param rate Updated rate
     * @param nights Updated number of nights
     * @param checkIn Updated check-in date
     * @param checkOut Updated check-out date
     * @return {@code true} if update was successful, {@code false} otherwise
     */
    public boolean updateReservation(int id, String name, String email, int roomNumber, String roomType, double rate, int nights, String checkIn, String checkOut) {
        if (!isConnected()) return false; // Return false if not connected

        // SQL update statement with placeholders
        String sql = """
            UPDATE reservations
            SET guest_name = ?, guest_email = ?, room_number = ?, room_type = ?, rate = ?, nights = ?, check_in = ?, check_out = ?
            WHERE id = ?;
        """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);         // Set guest name
            pstmt.setString(2, email);        // Set email
            pstmt.setInt(3, roomNumber);      // Set room number
            pstmt.setString(4, roomType);     // Set room type
            pstmt.setDouble(5, rate);         // Set rate
            pstmt.setInt(6, nights);          // Set nights
            pstmt.setString(7, checkIn);      // Set check-in
            pstmt.setString(8, checkOut);     // Set check-out
            pstmt.setInt(9, id);              // Set ID to update
            int affected = pstmt.executeUpdate(); // Run the update
            return affected > 0; // Return true if successful
        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage()); // Print error
            return false; // Return false on error
        }
    }

    /**
     * Searches reservations by guest name using SQL LIKE operator.
     *
     * @param keyword Partial or full name to search
     * @return A list of matching reservation strings
     */
    public ArrayList<String> searchReservationsByName(String keyword) {
        ArrayList<String> matches = new ArrayList<>(); // List for matched results
        if (!isConnected()) return matches;           // Return empty list if not connected

        String sql = "SELECT * FROM reservations WHERE guest_name LIKE ?;"; // SQL with wildcard search

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%"); // Set keyword with wildcard
            ResultSet rs = pstmt.executeQuery(); // Execute query

            while (rs.next()) { // Loop through results
                String reservation = "[ID: " + rs.getInt("id") + "] "
                        + rs.getString("guest_name") + " (" + rs.getString("guest_email") + ")"
                        + " *** Room #" + rs.getInt("room_number") + " - " + rs.getString("room_type")
                        + " *** Nights: " + rs.getInt("nights")
                        + " *** Check-In: " + rs.getString("check_in")
                        + " *** Check-Out: " + rs.getString("check_out")
                        + " *** Total: $" + (rs.getDouble("rate") * rs.getInt("nights"));
                matches.add(reservation); // Add match to list
            }
        } catch (SQLException e) {
            System.out.println("Error searching reservations: " + e.getMessage()); // Print error
        }
        // Return matches
        return matches;
    }
}
