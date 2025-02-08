package luna;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm luna.Luna");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty list.");
    }

    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" OOPS!!! " + message);
        System.out.println("____________________________________________________________");
    }

    public void showTask(String message) {
        System.out.println(" " + message);
    }

    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + message);
    }

    public void showBorder() {
        System.out.println("____________________________________________________________");
    }

    public String getUserInput() {
        return scanner.nextLine();
    }
}
