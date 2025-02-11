package luna;

import luna.task.Deadline;
import luna.task.Event;
import luna.task.Task;
import luna.task.TaskList;
import luna.task.Todo;
import luna.exception.LunaException;

/**
 * The {@code Parser} class handles the interpretation and execution of user commands.
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
                handleMarkCommand(userInput, tasks, ui);
                break;

            case "unmark":
                handleUnmarkCommand(userInput, tasks, ui);
                break;

            case "todo":
                handleTodoCommand(userInput, tasks, ui);
                break;

            case "deadline":
                handleDeadlineCommand(userInput, tasks, ui);
                break;

            case "event":
                handleEventCommand(userInput, tasks, ui);
                break;

            case "delete":
                handleDeleteCommand(userInput, tasks, ui);
                break;

            case "find":
                handleFindCommand(userInput, tasks, ui);
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
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new LunaException("Invalid task index. Please enter a valid number.");
        }
    }

    private void handleMarkCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        int index = getTaskIndex(userInput);
        tasks.markTaskDone(index);
        ui.showMessage("Nice! I've marked this task as done:\n  " + tasks.getTask(index));
        ui.showBorder();
    }

    private void handleUnmarkCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        int index = getTaskIndex(userInput);
        tasks.markTaskNotDone(index);
        ui.showMessage("OK, I've marked this task as not done yet:\n  " + tasks.getTask(index));
        ui.showBorder();
    }

    private void handleTodoCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        if (userInput.trim().equals("todo")) {
            throw new LunaException("The description of a todo cannot be empty.");
        }

        Task todoTask = new Todo(userInput.substring(5).trim());
        tasks.addTask(todoTask);
        ui.showMessage("Got it. I've added this task:\n  " + todoTask);
        ui.showBorder();
    }

    private void handleDeadlineCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        String[] parts = userInput.split(" /by ", 2);
        if (parts.length < 2) {
            throw new LunaException("Invalid deadline format. Use: deadline <description> /by <date/time>");
        }

        Task deadlineTask = new Deadline(parts[0].substring(9).trim(), parts[1].trim());
        tasks.addTask(deadlineTask);
        ui.showMessage("Got it. I've added this task:\n  " + deadlineTask);
        ui.showBorder();
    }

    private void handleEventCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        String[] parts = userInput.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new LunaException("Invalid event format. Use: event <description> /from <start> /to <end>");
        }

        Task eventTask = new Event(parts[0].substring(6).trim(), parts[1].trim(), parts[2].trim());
        tasks.addTask(eventTask);
        ui.showMessage("Got it. I've added this task:\n  " + eventTask);
        ui.showBorder();
    }

    private void handleDeleteCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        int index = getTaskIndex(userInput);
        Task removedTask = tasks.removeTask(index);
        ui.showMessage("Noted. I've removed this task:\n  " + removedTask);
        ui.showBorder();
    }

    private void handleFindCommand(String userInput, TaskList tasks, Ui ui) throws LunaException {
        if (userInput.trim().equals("find")) {
            throw new LunaException("Please specify a keyword to search for.");
        }

        String keyword = userInput.substring(5).trim();
        tasks.findTasks(keyword, ui);
    }
}
