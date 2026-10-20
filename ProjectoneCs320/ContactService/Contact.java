import java.util.regex.Pattern;

/**
 * Represents a single contact with a unique, immutable ID and four
 * updatable fields: firstName, lastName, phone, and address.
 *
 * All fields are validated in the constructor (for contactId) and in
 * the setters (for the updatable fields), so an invalid Contact can
 * never exist in memory.
 */
public class Contact {

    // Compiled once and reused. Pattern is thread safe, so sharing one instance
    // avoids recompiling the regular expression on every call to setPhone.
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null");
        }
        if (contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID cannot exceed 10 characters");
        }
        this.contactId = contactId;

        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    // contactId has no setter -- it is not updatable per the requirements.
    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        if (firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot exceed 10 characters");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
        if (lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot exceed 10 characters");
        }
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone cannot be null");
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits");
        }
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (address.length() > 30) {
            throw new IllegalArgumentException("Address cannot exceed 30 characters");
        }
        this.address = address;
    }
}
