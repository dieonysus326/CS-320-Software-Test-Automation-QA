import java.util.Date;

/**
 * Appointment object for the mobile application.
 * The appointment ID is required, unique, at most 10 characters, and cannot be changed.
 * The appointment date is required and cannot be in the past.
 * The description is required and at most 50 characters.
 */
public class Appointment {

    private final String appointmentId;
    private Date appointmentDate;
    private String description;

    public Appointment(String appointmentId, Date appointmentDate, String description) {
        if (appointmentId == null || appointmentId.length() > 10) {
            throw new IllegalArgumentException("Invalid appointment ID");
        }
        this.appointmentId = appointmentId;
        setAppointmentDate(appointmentDate);
        setDescription(description);
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Date getAppointmentDate() {
        // Return a copy so callers cannot change the stored date directly
        return new Date(appointmentDate.getTime());
    }

    public String getDescription() {
        return description;
    }

    public void setAppointmentDate(Date appointmentDate) {
        if (appointmentDate == null || appointmentDate.before(new Date())) {
            throw new IllegalArgumentException("Invalid appointment date");
        }
        // Store a copy so outside changes to the Date object do not affect this appointment
        this.appointmentDate = new Date(appointmentDate.getTime());
    }

    public void setDescription(String description) {
        if (description == null || description.length() > 50) {
            throw new IllegalArgumentException("Invalid description");
        }
        this.description = description;
    }
}
