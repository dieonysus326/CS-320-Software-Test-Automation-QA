import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    private Contact validContact() {
        return new Contact("1234567890", "John", "Doe", "1234567890", "123 Main Street");
    }

    // ----- Valid construction -----

    @Test
    void testCreateContactWithValidFields() {
        Contact contact = validContact();
        assertEquals("1234567890", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main Street", contact.getAddress());
    }

    // ----- Contact ID -----

    @Test
    void testContactIdCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(null, "John", "Doe", "1234567890", "123 Main Street"));
    }

    @Test
    void testContactIdCannotExceedTenCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("12345678901", "John", "Doe", "1234567890", "123 Main Street"));
    }

    @Test
    void testContactIdAtMaxLengthIsValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main Street");
        assertEquals("1234567890", contact.getContactId());
    }

    @Test
    void testContactIdIsNotUpdatable() {
        // The class must not expose any way to change the ID after construction.
        assertTrue(Arrays.stream(Contact.class.getMethods())
                .noneMatch(method -> method.getName().equals("setContactId")));

        // The ID also stays the same when every updatable field is changed.
        Contact contact = validContact();
        contact.setFirstName("Jane");
        contact.setLastName("Smith");
        contact.setPhone("9876543210");
        contact.setAddress("456 Oak Avenue");
        assertEquals("1234567890", contact.getContactId());
    }

    // ----- First name -----

    @Test
    void testFirstNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", null, "Doe", "1234567890", "123 Main Street"));
    }

    @Test
    void testFirstNameCannotExceedTenCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "JohnJohnJohn", "Doe", "1234567890", "123 Main Street"));
    }

    @Test
    void testSetFirstNameValid() {
        Contact contact = validContact();
        contact.setFirstName("Jane");
        assertEquals("Jane", contact.getFirstName());
    }

    @Test
    void testSetFirstNameNullThrows() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
    }

    @Test
    void testSetFirstNameTooLongThrows() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("JohnJohnJohn"));
    }

    // ----- Last name -----

    @Test
    void testLastNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "John", null, "1234567890", "123 Main Street"));
    }

    @Test
    void testLastNameCannotExceedTenCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "John", "DoeDoeDoeDoe", "1234567890", "123 Main Street"));
    }

    @Test
    void testSetLastNameValid() {
        Contact contact = validContact();
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
    }

    @Test
    void testSetLastNameNullThrows() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
    }

    @Test
    void testSetLastNameTooLongThrows() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("DoeDoeDoeDoe"));
    }

    // ----- Phone -----

    // One method runs once per value: nine digits, eleven digits, a letter,
    // punctuation, an empty string, and null.
    @ParameterizedTest
    @ValueSource(strings = {"123456789", "12345678901", "123456789a", "123-456-78", ""})
    @NullSource
    void testInvalidPhoneRejectedByConstructor(String phone) {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "John", "Doe", phone, "123 Main Street"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456789", "12345678901", "123456789a", "123-456-78", ""})
    @NullSource
    void testInvalidPhoneRejectedBySetter(String phone) {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone(phone));
        // A rejected update must leave the original value in place.
        assertEquals("1234567890", contact.getPhone());
    }

    @Test
    void testSetPhoneValid() {
        Contact contact = validContact();
        contact.setPhone("9876543210");
        assertEquals("9876543210", contact.getPhone());
    }

    // ----- Address -----

    @Test
    void testAddressCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "John", "Doe", "1234567890", null));
    }

    @Test
    void testAddressCannotExceedThirtyCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
                new Contact("1", "John", "Doe", "1234567890", "This address is definitely way too long"));
    }

    @Test
    void testAddressAtMaxLengthIsValid() {
        String exactlyThirty = "123456789012345678901234567890";
        assertEquals(30, exactlyThirty.length());
        Contact contact = new Contact("1", "John", "Doe", "1234567890", exactlyThirty);
        assertEquals(exactlyThirty, contact.getAddress());
    }

    @Test
    void testSetAddressValid() {
        Contact contact = validContact();
        contact.setAddress("456 Oak Avenue");
        assertEquals("456 Oak Avenue", contact.getAddress());
    }

    @Test
    void testSetAddressNullThrows() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
    }

    @Test
    void testSetAddressTooLongThrows() {
        Contact contact = validContact();
        assertThrows(IllegalArgumentException.class, () ->
                contact.setAddress("This address is definitely way too long"));
    }
}
