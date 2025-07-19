/**
 *Guest.java
 *
 * CEN 3024 - Software Development I
 * Andres Vera
 *
 * This class represents a hotel guest and stores their name and email address.
 * It is used as part of the Reservation system in the Hotel Data Management System (DMS).
 *
 * Role in the system: Provides guest information for reservations.
 *
 * Example usage:
 * Guest guest = new Guest("John Doe", "john@example.com");
 *
 * @author Andres Vera
 * @version 1.0
 * @since 2025-06-25
 */

public class Guest {
    /** The full name of the guest */
    private String name;
    /** The email address of the guest */
    private String email;

    /**
     * Constructs a Guest object with a given name and email.
     *
     * @param name  the name of the guest
     * @param email the email of the guest
     */
    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the name of the guest.
     *
     * @return the guest's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the email of the guest.
     *
     * @return the guest's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the name of the guest.
     *
     * @param name the new name of the guest
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email of the guest.
     *
     * @param email the new email of the guest
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the guest.
     *
     * @return a string with the guest's name and email
     */
    @Override
    public String toString() {
        return "Guest Name: " + name + ", email= " + email;
    }


}
