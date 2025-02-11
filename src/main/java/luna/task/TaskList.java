package luna.task;

import luna.exception.LunaException;
import luna.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Initializes an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initializes a task list with existing tasks.
     *
     * @param tasks The list of tasks to initialize with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     * @throws LunaException If the index is out of bounds.
     */
    public Task removeTask(int index) throws LunaException {
        if (index < 0 || index >= tasks.size()) {
            throw new LunaException("luna.task.Task index out of range.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list based on index.
     *
     * @param index The index of the task to retrieve.
     * @return The requested task.
     * @throws LunaException If the index is out of bounds.
     */
    public Task getTask(int index) throws LunaException {
        if (index < 0 || index >= tasks.size()) {
            throw new LunaException("luna.task.Task index out of range.");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done based on its index.
     *
     * @param index The index of the task to mark as done.
     * @throws LunaException If the index is out of bounds.
     */
    public void markTaskDone(int index) throws LunaException {
        getTask(index).markAsDone();
    }

    /**
     * Marks a task as not done based on its index.
     *
     * @param index The index of the task to mark as not done.
     * @throws LunaException If the index is out of bounds.
     */
    public void markTaskNotDone(int index) throws LunaException {
        getTask(index).markAsNotDone();
    }

    /**
     * Lists all tasks in the task list.
     *
     * @param ui The UI instance used to display the tasks.
     */
    public void listTasks(Ui ui) {
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showTask((i + 1) + ". " + tasks.get(i));
        }
        ui.showBorder();
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }
}
