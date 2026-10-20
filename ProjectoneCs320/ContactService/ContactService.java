import java.util.HashMap;
import java.util.Map;

/**
 * In-memory service for managing Contact objects.
 * Supports adding, deleting, and updating contacts by contact ID.
 */
public class ContactService {

    private final Map<String, Contact> contacts = new HashMap<>();

    /**
     * Adds a new contact. The contact ID must be unique.
     *
     * @param contact the contact to add
     * @throws IllegalArgumentException if contact is null or its ID already exists
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists: " + contact.getContactId());
        }
        contacts.put(contact.getContactId(), contact);
    }

    /**
     * Deletes the contact with the given ID.
     *
     * @param contactId the ID of the contact to delete
     * @throws IllegalArgumentException if no contact with that ID exists
     */
    public void deleteContact(String contactId) {
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found: " + contactId);
        }
        contacts.remove(contactId);
    }

    /**
     * Updates the first name of the contact with the given ID.
     */
    public void updateFirstName(String contactId, String firstName) {
        getContactOrThrow(contactId).setFirstName(firstName);
    }

    /**
     * Updates the last name of the contact with the given ID.
     */
    public void updateLastName(String contactId, String lastName) {
        getContactOrThrow(contactId).setLastName(lastName);
    }

    /**
     * Updates the phone number of the contact with the given ID.
     */
    public void updatePhone(String contactId, String phone) {
        getContactOrThrow(contactId).setPhone(phone);
    }

    /**
     * Updates the address of the contact with the given ID.
     */
    public void updateAddress(String contactId, String address) {
        getContactOrThrow(contactId).setAddress(address);
    }

    /**
     * Retrieves a contact by ID, or null if it does not exist.
     */
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    private Contact getContactOrThrow(String contactId) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID not found: " + contactId);
        }
        return contact;
    }
}
