package luna;

import luna.task.TaskList;
import luna.exception.LunaException;

/**
 * The main class for the Luna chatbot application.
 * Handles initialization, command parsing, and execution of tasks.
 */
public class Luna {

    private final Storage storage;
    private TaskList tasks;
    private final Parser parser;

    /**
     * Constructs a Luna instance.
     * Initializes the UI, storage, parser, and loads tasks from file.
     *
     * @param filePath The file path to store and retrieve tasks.
     */
    public Luna(String filePath) {
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (LunaException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Luna chatbot.
     * Continuously reads user input, processes commands, and handles errors.
     */
    public String getResponse(String input) {
        try {
            String response = parser.processCommand(input, tasks, storage);
            storage.saveTasks(tasks.getTasks());
            return "Processed: " + input + "\n" + response;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}