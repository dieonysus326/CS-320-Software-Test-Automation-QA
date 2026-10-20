import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TaskServiceTest.java
 *
 * JUnit tests verifying that the TaskService class meets all stated requirements:
 * adding tasks with a unique ID, deleting tasks by ID, and updating a task's
 * name and description by ID.
 */
public class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
    }

    // ---- Add requirements ----

    @Test
    public void testAddTaskSuccessfully() {
        Task task = new Task("1", "Buy groceries", "Milk, eggs, bread");
        taskService.addTask(task);

        assertEquals(1, taskService.getTaskCount());
        assertEquals(task, taskService.getTask("1"));
    }

    @Test
    public void testAddMultipleTasksWithUniqueIds() {
        taskService.addTask(new Task("1", "Task One", "Description one"));
        taskService.addTask(new Task("2", "Task Two", "Description two"));

        assertEquals(2, taskService.getTaskCount());
    }

    @Test
    public void testAddTaskWithDuplicateIdThrowsException() {
        taskService.addTask(new Task("1", "Task One", "Description one"));
        Task duplicate = new Task("1", "Task Duplicate", "Another description");

        assertThrows(IllegalArgumentException.class, () -> taskService.addTask(duplicate));
        assertEquals(1, taskService.getTaskCount());
    }

    @Test
    public void testAddNullTaskThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> taskService.addTask(null));
    }

    // ---- Delete requirements ----

    @Test
    public void testDeleteTaskSuccessfully() {
        taskService.addTask(new Task("1", "Task One", "Description one"));
        taskService.deleteTask("1");

        assertEquals(0, taskService.getTaskCount());
        assertNull(taskService.getTask("1"));
    }

    @Test
    public void testDeleteNonExistentTaskThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask("does-not-exist"));
    }

    @Test
    public void testDeleteOnlyRemovesSpecifiedTask() {
        taskService.addTask(new Task("1", "Task One", "Description one"));
        taskService.addTask(new Task("2", "Task Two", "Description two"));

        taskService.deleteTask("1");

        assertEquals(1, taskService.getTaskCount());
        assertNull(taskService.getTask("1"));
        assertEquals("Task Two", taskService.getTask("2").getName());
    }

    // ---- Update requirements ----

    @Test
    public void testUpdateTaskNameSuccessfully() {
        taskService.addTask(new Task("1", "Old Name", "Description"));
        taskService.updateTaskName("1", "New Name");

        assertEquals("New Name", taskService.getTask("1").getName());
    }

    @Test
    public void testUpdateTaskNameForNonExistentTaskThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTaskName("does-not-exist", "New Name"));
    }

    @Test
    public void testUpdateTaskNameWithInvalidNameThrowsException() {
        taskService.addTask(new Task("1", "Old Name", "Description"));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTaskName("1", "123456789012345678901"));

        // Name should remain unchanged after the failed update.
        assertEquals("Old Name", taskService.getTask("1").getName());
    }

    @Test
    public void testUpdateTaskNameWithNullThrowsException() {
        taskService.addTask(new Task("1", "Old Name", "Description"));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTaskName("1", null));
        assertEquals("Old Name", taskService.getTask("1").getName());
    }

    @Test
    public void testUpdateTaskDescriptionSuccessfully() {
        taskService.addTask(new Task("1", "Name", "Old description"));
        taskService.updateTaskDescription("1", "New description");

        assertEquals("New description", taskService.getTask("1").getDescription());
        // Updating the description must not touch the name.
        assertEquals("Name", taskService.getTask("1").getName());
    }

    @Test
    public void testUpdateTaskDescriptionForNonExistentTaskThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTaskDescription("does-not-exist", "New description"));
    }

    @Test
    public void testUpdateTaskDescriptionWithInvalidDescriptionThrowsException() {
        taskService.addTask(new Task("1", "Name", "Old description"));
        String tooLong = "123456789012345678901234567890123456789012345678901";

        assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTaskDescription("1", tooLong));
        assertThrows(IllegalArgumentException.class,
                () -> taskService.updateTaskDescription("1", null));

        // Description should remain unchanged after the failed updates.
        assertEquals("Old description", taskService.getTask("1").getDescription());
    }

    @Test
    public void testUpdatesDoNotChangeTaskId() {
        taskService.addTask(new Task("1", "Old Name", "Old description"));
        taskService.updateTaskName("1", "New Name");
        taskService.updateTaskDescription("1", "New description");

        assertEquals("1", taskService.getTask("1").getTaskID());
    }
}
