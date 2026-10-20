import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentServiceTest {

    private AppointmentService service;

    private Date futureDate() {
        return new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000);
    }

    @BeforeEach
    public void setUp() {
        service = new AppointmentService();
    }

    // Add requirements

    @Test
    public void testAddAppointment() {
        Appointment appointment = new Appointment("1", futureDate(), "Checkup");
        service.addAppointment(appointment);
        assertNotNull(service.getAppointment("1"));
        assertEquals("Checkup", service.getAppointment("1").getDescription());
    }

    @Test
    public void testAddMultipleAppointments() {
        service.addAppointment(new Appointment("1", futureDate(), "First"));
        service.addAppointment(new Appointment("2", futureDate(), "Second"));
        assertNotNull(service.getAppointment("1"));
        assertNotNull(service.getAppointment("2"));
    }

    @Test
    public void testAddDuplicateAppointmentId() {
        service.addAppointment(new Appointment("1", futureDate(), "First"));
        assertThrows(IllegalArgumentException.class,
                () -> service.addAppointment(new Appointment("1", futureDate(), "Duplicate")));
    }

    @Test
    public void testAddNullAppointment() {
        assertThrows(IllegalArgumentException.class, () -> service.addAppointment(null));
    }

    // Delete requirements

    @Test
    public void testDeleteAppointment() {
        service.addAppointment(new Appointment("1", futureDate(), "Checkup"));
        service.deleteAppointment("1");
        assertNull(service.getAppointment("1"));
    }

    @Test
    public void testDeleteOnlyRemovesMatchingId() {
        service.addAppointment(new Appointment("1", futureDate(), "First"));
        service.addAppointment(new Appointment("2", futureDate(), "Second"));
        service.deleteAppointment("1");
        assertNull(service.getAppointment("1"));
        assertNotNull(service.getAppointment("2"));
    }

    @Test
    public void testDeleteNonexistentAppointment() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteAppointment("999"));
    }

    @Test
    public void testDeleteNullId() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteAppointment(null));
    }

    // Update behavior

    @Test
    public void testUpdateAppointmentDate() {
        service.addAppointment(new Appointment("1", futureDate(), "Checkup"));
        Date newDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
        service.updateAppointmentDate("1", newDate);
        assertEquals(newDate, service.getAppointment("1").getAppointmentDate());
    }

    @Test
    public void testUpdateDescription() {
        service.addAppointment(new Appointment("1", futureDate(), "Checkup"));
        service.updateDescription("1", "Follow-up visit");
        assertEquals("Follow-up visit", service.getAppointment("1").getDescription());
    }

    @Test
    public void testUpdateNonexistentAppointment() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateDescription("999", "Nothing"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateAppointmentDate("999", futureDate()));
    }
}
