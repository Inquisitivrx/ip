import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) throws LunaException {
        if (index < 0 || index >= tasks.size()) {
            throw new LunaException("Task index out of range.");
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws LunaException {
        if (index < 0 || index >= tasks.size()) {
            throw new LunaException("Task index out of range.");
        }
        return tasks.get(index);
    }

    public void markTaskDone(int index) throws LunaException {
        getTask(index).markAsDone();
    }

    public void markTaskNotDone(int index) throws LunaException {
        getTask(index).markAsNotDone();
    }

    public void listTasks(Ui ui) {
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showTask((i + 1) + ". " + tasks.get(i));
        }
        ui.showBorder();
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
