/**
 * Task.java
 *
 * Represents a single task within the task management application.
 * A Task has three required fields: taskID, name, and description.
 * The taskID is immutable once set (no setter is provided for it).
 *
 * Each field's rules live in one private validation method that is called
 * from both the constructor and the matching setter, so a rule only ever
 * needs to change in one place.
 */
public class Task {

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESCRIPTION_LENGTH = 50;

    private final String taskID;
    private String name;
    private String description;

    /**
     * Constructs a new Task.
     *
     * @param taskID      unique identifier for the task; cannot be null and cannot exceed 10 characters
     * @param name        name of the task; cannot be null and cannot exceed 20 characters
     * @param description description of the task; cannot be null and cannot exceed 50 characters
     * @throws IllegalArgumentException if any field is null or exceeds its maximum length
     */
    public Task(String taskID, String name, String description) {
        // The validation methods are private and static, so a subclass cannot
        // override them and they never run against a partially built object.
        this.taskID = validateTaskID(taskID);
        this.name = validateName(name);
        this.description = validateDescription(description);
    }

    /**
     * Gets the task ID. There is intentionally no setter for this field,
     * since the task ID must not be updatable after creation.
     */
    public String getTaskID() {
        return taskID;
    }

    public String getName() {
        return name;
    }

    /**
     * Updates the task name.
     *
     * @param name new name; cannot be null and cannot exceed 20 characters
     * @throws IllegalArgumentException if name is null or exceeds 20 characters
     */
    public void setName(String name) {
        this.name = validateName(name);
    }

    public String getDescription() {
        return description;
    }

    /**
     * Updates the task description.
     *
     * @param description new description; cannot be null and cannot exceed 50 characters
     * @throws IllegalArgumentException if description is null or exceeds 50 characters
     */
    public void setDescription(String description) {
        this.description = validateDescription(description);
    }

    // ---- Validation: the single place each field's rules are defined ----

    private static String validateTaskID(String taskID) {
        if (taskID == null) {
            throw new IllegalArgumentException("Task ID cannot be null.");
        }
        if (taskID.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException(
                    "Task ID cannot be longer than " + MAX_ID_LENGTH + " characters.");
        }
        return taskID;
    }

    private static String validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Task name cannot be null.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Task name cannot be longer than " + MAX_NAME_LENGTH + " characters.");
        }
        return name;
    }

    private static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Task description cannot be null.");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(
                    "Task description cannot be longer than " + MAX_DESCRIPTION_LENGTH + " characters.");
        }
        return description;
    }
}
