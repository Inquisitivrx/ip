package luna;

import luna.task.Deadline;
import luna.task.Event;
import luna.task.Task;
import luna.task.TaskList;
import luna.task.Todo;
import luna.exception.LunaException;

/**
 * The Parser class handles the interpretation and execution of user commands.
 * It processes user input and updates the task list accordingly.
 */
public class Parser {

    /**
     * Processes user input and executes the corresponding command.
     *
     * @param userInput The command entered by the user.
     * @param tasks The list of tasks.
     * @param ui The UI instance to display messages.
     * @param storage The storage handler for saving and loading tasks.
     * @return {@code false} if the command is "bye" (to exit the program), otherwise {@code true}.
     * @throws LunaException If the input is invalid or an unknown command is entered.
     */
    public boolean processCommand(String userInput, TaskList tasks, Ui ui, Storage storage) throws LunaException {
        String[] inputParts = userInput.split(" ", 2);
        String command = inputParts[0].toLowerCase();

        switch (command) {
            case "bye":
                ui.showMessage("Bye. Hope to see you again soon!");
                ui.showBorder();
                return false;

            case "list":
                tasks.listTasks(ui);
                break;

            case "mark":
                int markIndex = getTaskIndex(userInput);
                tasks.markTaskDone(markIndex);
                ui.showMessage("Nice! I've marked this task as done:\n  " + tasks.getTask(markIndex));
                ui.showBorder();
                break;

            case "unmark":
                int unmarkIndex = getTaskIndex(userInput);
                tasks.markTaskNotDone(unmarkIndex);
                ui.showMessage("OK, I've marked this task as not done yet:\n  " + tasks.getTask(unmarkIndex));
                ui.showBorder();
                break;

            case "todo":
                Task todoTask = new Todo(userInput);
                tasks.addTask(todoTask);
                ui.showMessage("Got it. I've added this task:\n  " + todoTask);
                ui.showBorder();
                break;

            case "deadline":
                String[] deadlineParts = userInput.split(" /by ", 2);
                if (deadlineParts.length < 2) throw new LunaException("Invalid deadline format.");
                Task deadlineTask = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                tasks.addTask(deadlineTask);
                ui.showMessage("Got it. I've added this task:\n  " + deadlineTask);
                ui.showBorder();
                break;

            case "event":
                String[] eventParts = userInput.split(" /from | /to ", 3);
                if (eventParts.length < 3) throw new LunaException("Invalid event format.");
                Task eventTask = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                tasks.addTask(eventTask);
                ui.showMessage("Got it. I've added this task:\n  " + eventTask);
                ui.showBorder();
                break;

            case "delete":
                if (userInput.isEmpty()) {
                    throw new LunaException("Please specify a task number to delete.");
                }
                int deleteIndex = getTaskIndex(userInput);
                Task removedTask = tasks.removeTask(deleteIndex);
                ui.showMessage("Noted. I've removed this task:\n  " + removedTask);
                ui.showBorder();
                break;

            default:
                throw new LunaException("I'm sorry, but I don't recognize that command.");
        }

        storage.saveTasks(tasks.getTasks());
        return true;
    }

    /**
     * Extracts the task index from a user command.
     *
     * @param userInput The full user input string.
     * @return The zero-based index of the task in the list.
     * @throws LunaException If the index is missing or not a valid number.
     */
    private int getTaskIndex(String userInput) throws LunaException {
        try {
            return Integer.parseInt(userInput.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new LunaException("Invalid task index.");
        }
    }
}
