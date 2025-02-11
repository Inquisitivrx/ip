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
    private final Ui ui;
    private final Parser parser;

    /**
     * Constructs a Luna instance.
     * Initializes the UI, storage, parser, and loads tasks from file.
     *
     * @param filePath The file path to store and retrieve tasks.
     */
    public Luna(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (LunaException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Luna chatbot.
     * Continuously reads user input, processes commands, and handles errors.
     */
    public void run() {
        ui.showWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String userInput = ui.getUserInput();
            try {
                isRunning = parser.processCommand(userInput, tasks, ui, storage);
            } catch (LunaException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The entry point of the Luna chatbot application.
     * Creates an instance of Luna and starts the chatbot.
     */
    public static void main(String[] args) {
        new Luna("data/tasks.txt").run();
    }
}