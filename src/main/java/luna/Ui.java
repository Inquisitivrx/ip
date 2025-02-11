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
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm luna.Luna");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message when loading tasks fails.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty list.");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be shown.
     */
    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" OOPS!!! " + message);
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a task message.
     *
     * @param message The task-related message.
     */
    public void showTask(String message) {
        System.out.println(" " + message);
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
