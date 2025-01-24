import java.util.ArrayList;
import java.util.Scanner;

public class Luna {
    public static void main(String[] args) {
        // List to store user inputs
        ArrayList<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Print a greeting message
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Luna");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            // Read user input
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                // Exit if user types "bye"
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                // Display all stored tasks
                System.out.println("____________________________________________________________");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            } else {
                // Add user input to the list and confirm
                tasks.add(userInput);
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }

        // Close the scanner
        scanner.close();
    }
}
