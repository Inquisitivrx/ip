package luna;

import java.util.Scanner;

/**
 * Handles user interaction by displaying messages and receiving input.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Initializes the UI with a scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public String showWelcome() {
        return "Hello! I'm Luna!\nWhat can I do for you?";
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be shown.
     */
    public String showError(String message) {
        return "Error: " + message;
    }

    /**
     * Displays a task message.
     *
     * @param message The task-related message.
     */
    public String showTask(String message) {
        return " " + message;
    }

    /**
     * Displays a general message.
     *
     * @param message The message to be shown.
     */
    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + message);
    }

    /**
     * Displays a border for formatting.
     */
    public void showBorder() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads user input from the console.
     *
     * @return The user's input as a string.
     */
    public String getUserInput() {
        return scanner.nextLine();
    }
}
