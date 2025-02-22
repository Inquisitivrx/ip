package luna;

import luna.task.Deadline;
import luna.task.Event;
import luna.task.Task;
import luna.task.TaskList;
import luna.task.Todo;
import luna.exception.LunaException;

/**
 * The {@code Parser} class is responsible for parsing and processing user commands.
 */
public class Parser {

    /**
     * Processes user input and executes the corresponding command.
     *
     * @param userInput The command entered by the user.
     * @param tasks The list of tasks.
     * @return A response message based on the command execution.
     * @throws LunaException If the input is invalid or an unknown command is entered.
     */
    public String processCommand(String userInput, TaskList tasks) throws LunaException {
        String[] inputParts = userInput.split(" ", 2);
        String command = inputParts[0].toLowerCase();

        switch (command) {
            case "list":
                return tasks.listTasks();

            case "mark":
                return handleMarkCommand(inputParts, tasks);

            case "unmark":
                return handleUnmarkCommand(inputParts, tasks);

            case "todo":
                return handleTodoCommand(inputParts, tasks);

            case "deadline":
                return handleDeadlineCommand(inputParts, tasks);

            case "event":
                return handleEventCommand(inputParts, tasks);

            case "delete":
                return handleDeleteCommand(inputParts, tasks);

            case "find":
                return handleFindCommand(inputParts, tasks);

            default:
                throw new LunaException("I'm sorry, but I don't recognize that command. Try 'list', 'todo', 'event', etc.");
        }
    }

    /**
     * Extracts the task index from the command arguments.
     *
     * @param inputParts The split input array containing the command and argument.
     * @return The zero-based index of the task in the list.
     * @throws LunaException If the index is missing or invalid.
     */
    private int getTaskIndex(String[] inputParts) throws LunaException {
        if (inputParts.length < 2) {
            throw new LunaException("Missing task index. Please provide a valid number.");
        }

        try {
            int index = Integer.parseInt(inputParts[1]) - 1;
            if (index < 0) {
                throw new LunaException("Task index must be a positive number.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new LunaException("Invalid task index. Please enter a valid number.");
        }
    }

    private String handleMarkCommand(String[] inputParts, TaskList tasks) throws LunaException {
        int index = getTaskIndex(inputParts);
        tasks.markTaskDone(index);
        return "Nice! I've marked this task as done:\n  " + tasks.getTask(index);
    }

    private String handleUnmarkCommand(String[] inputParts, TaskList tasks) throws LunaException {
        int index = getTaskIndex(inputParts);
        tasks.markTaskNotDone(index);
        return "OK, I've marked this task as not done yet:\n  " + tasks.getTask(index);
    }

    private String handleTodoCommand(String[] inputParts, TaskList tasks) throws LunaException {
        if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
            throw new LunaException("The description of a todo cannot be empty. Usage: todo <description>");
        }

        Task todoTask = new Todo(inputParts[1].trim());
        tasks.addTask(todoTask);
        return "Got it. I've added this task:\n  " + todoTask;
    }

    private String handleDeadlineCommand(String[] inputParts, TaskList tasks) throws LunaException {
        if (inputParts.length < 2) {
            throw new LunaException("Invalid deadline format. Usage: deadline <description> /by <date/time>");
        }

        String[] parts = inputParts[1].split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new LunaException("Invalid deadline format. Usage: deadline <description> /by <date/time>");
        }

        Task deadlineTask = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(deadlineTask);
        return "Got it. I've added this task:\n  " + deadlineTask;
    }

    private String handleEventCommand(String[] inputParts, TaskList tasks) throws LunaException {
        if (inputParts.length < 2) {
            throw new LunaException("Invalid event format. Usage: event <description> /from <start> /to <end>");
        }

        String[] parts = inputParts[1].split(" /from ", 2);
        if (parts.length < 2) {
            throw new LunaException("Invalid event format. Missing '/from'. Usage: event <description> /from <start> /to <end>");
        }

        String[] timeParts = parts[1].split(" /to ", 2);
        if (timeParts.length < 2) {
            throw new LunaException("Invalid event format. Missing '/to'. Usage: event <description> /from <start> /to <end>");
        }

        Task eventTask = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
        tasks.addTask(eventTask);
        return "Got it. I've added this task:\n  " + eventTask;
    }

    private String handleDeleteCommand(String[] inputParts, TaskList tasks) throws LunaException {
        int index = getTaskIndex(inputParts);
        Task removedTask = tasks.removeTask(index);
        return "Noted. I've removed this task:\n  " + removedTask;
    }

    private String handleFindCommand(String[] inputParts, TaskList tasks) throws LunaException {
        if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
            throw new LunaException("Please specify a keyword to search for. Usage: find <keyword>");
        }

        return tasks.findTasks(inputParts[1].trim());
    }
}
