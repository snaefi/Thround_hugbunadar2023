package hi.vidmot.verkefni.model;

public class Passenger extends Booking{
    String firstName;
    String lastName;
    String passportNumber;
    String phone;
    String email;
    String seatNumber;
    boolean isContact;


    public Passenger() {
        super();
    }

    public Passenger(String firstName, String lastName, String passport, String phone, String email,
                     boolean isContact, String seatNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passport;
        this.phone = phone;
        this.email = email;
        this.isContact = isContact;
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public boolean isContact() {
        return isContact;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", isContact=" + isContact +
                '}';
    }
}
