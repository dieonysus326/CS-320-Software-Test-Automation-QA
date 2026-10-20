import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    // One day in the future and one day in the past, relative to when the test runs
    private Date futureDate() {
        return new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000);
    }

    private Date pastDate() {
        return new Date(System.currentTimeMillis() - 24L * 60 * 60 * 1000);
    }

    @Test
    public void testValidAppointment() {
        Date date = futureDate();
        Appointment appointment = new Appointment("1234567890", date, "Dentist checkup");
        assertEquals("1234567890", appointment.getAppointmentId());
        assertEquals(date, appointment.getAppointmentDate());
        assertEquals("Dentist checkup", appointment.getDescription());
    }

    // Appointment ID requirements

    @Test
    public void testAppointmentIdTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment("12345678901", futureDate(), "Description"));
    }

    @Test
    public void testAppointmentIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(null, futureDate(), "Description"));
    }

    @Test
    public void testAppointmentIdIsNotUpdatable() {
        // The class must not expose any way to change the ID after construction.
        assertTrue(Arrays.stream(Appointment.class.getMethods())
                .noneMatch(method -> method.getName().equals("setAppointmentId")));

        // The ID also stays the same when the updatable fields are changed.
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        appointment.setAppointmentDate(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
        appointment.setDescription("New description");
        assertEquals("1", appointment.getAppointmentId());
    }

    // Appointment date requirements

    @Test
    public void testAppointmentDateInPast() {
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment("1", pastDate(), "Description"));
    }

    @Test
    public void testAppointmentDateNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment("1", null, "Description"));
    }

    @Test
    public void testSetAppointmentDateValid() {
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        Date newDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
        appointment.setAppointmentDate(newDate);
        assertEquals(newDate, appointment.getAppointmentDate());
    }

    @Test
    public void testSetAppointmentDateInPast() {
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        assertThrows(IllegalArgumentException.class,
                () -> appointment.setAppointmentDate(pastDate()));
    }

    @Test
    public void testSetAppointmentDateNull() {
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        assertThrows(IllegalArgumentException.class,
                () -> appointment.setAppointmentDate(null));
    }

    @Test
    public void testAppointmentDateIsDefensiveCopy() {
        Date date = futureDate();
        Appointment appointment = new Appointment("1", date, "Description");
        long original = date.getTime();
        date.setTime(0); // try to change the date from outside the object
        assertEquals(original, appointment.getAppointmentDate().getTime());
        assertNotSame(date, appointment.getAppointmentDate());
    }

    // Description requirements

    @Test
    public void testDescriptionAtMaxLength() {
        String fifty = "a".repeat(50);
        Appointment appointment = new Appointment("1", futureDate(), fifty);
        assertEquals(fifty, appointment.getDescription());
    }

    @Test
    public void testDescriptionTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment("1", futureDate(), "a".repeat(51)));
    }

    @Test
    public void testDescriptionNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment("1", futureDate(), null));
    }

    @Test
    public void testSetDescriptionValid() {
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        appointment.setDescription("New description");
        assertEquals("New description", appointment.getDescription());
    }

    @Test
    public void testSetDescriptionTooLong() {
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        assertThrows(IllegalArgumentException.class,
                () -> appointment.setDescription("a".repeat(51)));
    }

    @Test
    public void testSetDescriptionNull() {
        Appointment appointment = new Appointment("1", futureDate(), "Description");
        assertThrows(IllegalArgumentException.class,
                () -> appointment.setDescription(null));
    }
}
