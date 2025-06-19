/* This class represents a hotel guest and stores their information that
will be used as part of a reservation.
Input: name and email of the guest.
 */

public class Guest {
    //attributes to store guest's information
    private String name;
    private String email;

    //Constructor
    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //Getter and Setters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //Method
    @Override
    public String toString() {
        return "Guest Name: " + name + ", email= " + email;
    }


}
