public class Luna {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

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

    public static void main(String[] args) {
        new Luna("data/tasks.txt").run();
    }
}