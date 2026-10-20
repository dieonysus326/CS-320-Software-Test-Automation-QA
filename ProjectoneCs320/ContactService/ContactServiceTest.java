import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService();
    }

    // ----- Add -----

    @Test
    void testAddContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        assertEquals(contact, service.getContact("1"));
    }

    @Test
    void testAddContactWithDuplicateIdThrows() {
        Contact contact1 = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        Contact contact2 = new Contact("1", "Jane", "Smith", "0987654321", "456 Oak Avenue");
        service.addContact(contact1);
        assertThrows(IllegalArgumentException.class, () -> service.addContact(contact2));
    }

    @Test
    void testAddNullContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    // ----- Delete -----

    @Test
    void testDeleteContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        service.deleteContact("1");
        assertNull(service.getContact("1"));
    }

    @Test
    void testDeleteNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("nonexistent"));
    }

    // ----- Update first name -----

    @Test
    void testUpdateFirstName() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        service.updateFirstName("1", "Jane");
        assertEquals("Jane", service.getContact("1").getFirstName());
    }

    @Test
    void testUpdateFirstNameNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("nonexistent", "Jane"));
    }

    @Test
    void testUpdateFirstNameInvalidValueThrows() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("1", null));
    }

    // ----- Update last name -----

    @Test
    void testUpdateLastName() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        service.updateLastName("1", "Smith");
        assertEquals("Smith", service.getContact("1").getLastName());
    }

    @Test
    void testUpdateLastNameNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("nonexistent", "Smith"));
    }

    // ----- Update phone -----

    @Test
    void testUpdatePhone() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        service.updatePhone("1", "9876543210");
        assertEquals("9876543210", service.getContact("1").getPhone());
    }

    @Test
    void testUpdatePhoneInvalidValueThrows() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("1", "12345"));
    }

    // ----- Update address -----

    @Test
    void testUpdateAddress() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        service.updateAddress("1", "456 Oak Avenue");
        assertEquals("456 Oak Avenue", service.getContact("1").getAddress());
    }

    @Test
    void testUpdateAddressNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("nonexistent", "456 Oak Avenue"));
    }

    @Test
    void testUpdateAddressInvalidValueThrows() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () ->
                service.updateAddress("1", "This address is definitely way too long"));
    }

    // ----- Contact ID immutability (service-level check) -----

    @Test
    void testContactIdRemainsUnchangedAfterUpdates() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main Street");
        service.addContact(contact);
        service.updateFirstName("1", "Jane");
        service.updateLastName("1", "Smith");
        service.updatePhone("1", "9876543210");
        service.updateAddress("1", "456 Oak Avenue");
        assertEquals("1", service.getContact("1").getContactId());
    }
}
