import java.util.HashMap;
import java.util.Map;

/**
 * TaskService.java
 *
 * Provides in-memory management of Task objects. Supports adding tasks
 * with a unique ID, deleting tasks by ID, and updating a task's name and
 * description by ID.
 */
public class TaskService {

    private final Map<String, Task> tasks = new HashMap<>();

    /**
     * Adds a new task to the service.
     *
     * @param task the task to add; must not be null and must have a unique task ID
     * @throws IllegalArgumentException if task is null or a task with the same ID already exists
     */
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        if (tasks.containsKey(task.getTaskID())) {
            throw new IllegalArgumentException("A task with ID " + task.getTaskID() + " already exists.");
        }
        tasks.put(task.getTaskID(), task);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskID the ID of the task to delete
     * @throws IllegalArgumentException if no task with the given ID exists
     */
    public void deleteTask(String taskID) {
        if (!tasks.containsKey(taskID)) {
            throw new IllegalArgumentException("No task found with ID " + taskID + ".");
        }
        tasks.remove(taskID);
    }

    /**
     * Updates the name of an existing task.
     *
     * @param taskID  the ID of the task to update
     * @param newName the new name for the task; must not be null and cannot exceed 20 characters
     * @throws IllegalArgumentException if no task with the given ID exists, or if newName is invalid
     */
    public void updateTaskName(String taskID, String newName) {
        getExistingTask(taskID).setName(newName);
    }

    /**
     * Updates the description of an existing task.
     *
     * @param taskID         the ID of the task to update
     * @param newDescription the new description; must not be null and cannot exceed 50 characters
     * @throws IllegalArgumentException if no task with the given ID exists, or if newDescription is invalid
     */
    public void updateTaskDescription(String taskID, String newDescription) {
        getExistingTask(taskID).setDescription(newDescription);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param taskID the ID of the task to retrieve
     * @return the Task with the given ID, or null if none exists
     */
    public Task getTask(String taskID) {
        return tasks.get(taskID);
    }

    /**
     * Returns the number of tasks currently held by the service.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Looks up a task that must already exist. Shared by the update methods
     * so the "not found" check is written only once.
     */
    private Task getExistingTask(String taskID) {
        Task task = tasks.get(taskID);
        if (task == null) {
            throw new IllegalArgumentException("No task found with ID " + taskID + ".");
        }
        return task;
    }
}
