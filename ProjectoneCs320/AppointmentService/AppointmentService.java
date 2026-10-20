import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Appointment service that stores appointments in memory.
 * Supports adding appointments with a unique ID, deleting by ID,
 * and updating the date and description of an existing appointment.
 */
public class AppointmentService {

    private final Map<String, Appointment> appointments = new HashMap<>();

    public void addAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        if (appointments.containsKey(appointment.getAppointmentId())) {
            throw new IllegalArgumentException("Appointment ID already exists");
        }
        appointments.put(appointment.getAppointmentId(), appointment);
    }

    public void deleteAppointment(String appointmentId) {
        if (appointmentId == null || !appointments.containsKey(appointmentId)) {
            throw new IllegalArgumentException("Appointment ID not found");
        }
        appointments.remove(appointmentId);
    }

    public void updateAppointmentDate(String appointmentId, Date appointmentDate) {
        getExistingAppointment(appointmentId).setAppointmentDate(appointmentDate);
    }

    public void updateDescription(String appointmentId, String description) {
        getExistingAppointment(appointmentId).setDescription(description);
    }

    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }

    private Appointment getExistingAppointment(String appointmentId) {
        Appointment appointment = appointments.get(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment ID not found");
        }
        return appointment;
    }
}
