import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * TaskTest.java
 *
 * JUnit tests verifying that the Task class meets all stated requirements:
 * task ID (required, non-null, max 10 chars, not updatable),
 * name (required, non-null, max 20 chars),
 * description (required, non-null, max 50 chars).
 */
public class TaskTest {

    // ---- Valid construction ----

    @Test
    public void testValidTaskIsCreatedSuccessfully() {
        Task task = new Task("1", "Buy groceries", "Milk, eggs, bread");

        assertEquals("1", task.getTaskID());
        assertEquals("Buy groceries", task.getName());
        assertEquals("Milk, eggs, bread", task.getDescription());
    }

    // ---- Task ID requirements ----

    // Runs once for an 11 character ID, once for a much longer ID, and once for null.
    @ParameterizedTest
    @ValueSource(strings = {"12345678901", "this-task-id-is-far-too-long"})
    @NullSource
    public void testInvalidTaskIdIsRejected(String taskID) {
        assertThrows(IllegalArgumentException.class,
                () -> new Task(taskID, "Name", "Description"));
    }

    @Test
    public void testTaskIdAtMaxLengthIsAccepted() {
        Task task = new Task("1234567890", "Name", "Description");
        assertEquals("1234567890", task.getTaskID());
    }

    @Test
    public void testTaskIdHasNoSetterAndCannotBeUpdated() {
        // Task intentionally exposes no setTaskID method, verifying the
        // task ID is not updatable after creation. The ID is confirmed
        // unchanged after other fields are updated.
        Task task = new Task("1", "Name", "Description");
        task.setName("New Name");
        task.setDescription("New Description");

        assertEquals("1", task.getTaskID());
    }

    // ---- Name requirements ----

    // Runs once for a 21 character name, once for a much longer name, and once for null.
    @ParameterizedTest
    @ValueSource(strings = {"123456789012345678901", "This task name is well past the limit"})
    @NullSource
    public void testInvalidNameIsRejectedByConstructor(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> new Task("1", name, "Description"));
    }

    @Test
    public void testNameAtMaxLengthIsAccepted() {
        Task task = new Task("1", "12345678901234567890", "Description");
        assertEquals("12345678901234567890", task.getName());
    }

    @Test
    public void testNameCanBeUpdated() {
        Task task = new Task("1", "Old Name", "Description");
        task.setName("New Name");
        assertEquals("New Name", task.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456789012345678901", "This task name is well past the limit"})
    @NullSource
    public void testInvalidNameIsRejectedBySetter(String name) {
        Task task = new Task("1", "Old Name", "Description");
        assertThrows(IllegalArgumentException.class, () -> task.setName(name));
        // A rejected update must leave the original value in place.
        assertEquals("Old Name", task.getName());
    }

    // ---- Description requirements ----

    // Runs once for a 51 character description, once for a much longer one, and once for null.
    @ParameterizedTest
    @ValueSource(strings = {
            "123456789012345678901234567890123456789012345678901",
            "This description keeps going well beyond the fifty character limit set for tasks"})
    @NullSource
    public void testInvalidDescriptionIsRejectedByConstructor(String description) {
        assertThrows(IllegalArgumentException.class,
                () -> new Task("1", "Name", description));
    }

    @Test
    public void testDescriptionAtMaxLengthIsAccepted() {
        String maxLength = "12345678901234567890123456789012345678901234567890";
        Task task = new Task("1", "Name", maxLength);
        assertEquals(maxLength, task.getDescription());
    }

    @Test
    public void testDescriptionCanBeUpdated() {
        Task task = new Task("1", "Name", "Old Description");
        task.setDescription("New Description");
        assertEquals("New Description", task.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123456789012345678901234567890123456789012345678901",
            "This description keeps going well beyond the fifty character limit set for tasks"})
    @NullSource
    public void testInvalidDescriptionIsRejectedBySetter(String description) {
        Task task = new Task("1", "Name", "Old Description");
        assertThrows(IllegalArgumentException.class, () -> task.setDescription(description));
        // A rejected update must leave the original value in place.
        assertEquals("Old Description", task.getDescription());
    }
}
