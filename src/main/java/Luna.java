import java.util.ArrayList;
import java.util.Scanner;

public class Luna {
    public static void main(String[] args) {
        // List to store tasks
        ArrayList<Task> tasks = new ArrayList<>();
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
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            } else if (userInput.startsWith("todo ")) {
                // Add a ToDo task
                String description = userInput.substring(5).trim();
                Task task = new Todo(description);
                tasks.add(task);
                printTaskAdded(task, tasks.size());
            } else if (userInput.startsWith("deadline ")) {
                // Add a Deadline task
                String[] parts = userInput.substring(9).split(" /by ");
                if (parts.length == 2) {
                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks.add(task);
                    printTaskAdded(task, tasks.size());
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Invalid format. Use: deadline [description] /by [time]");
                    System.out.println("____________________________________________________________");
                }
            } else if (userInput.startsWith("event ")) {
                // Add an Event task
                String[] parts = userInput.substring(6).split(" /from | /to ");
                if (parts.length == 3) {
                    Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    tasks.add(task);
                    printTaskAdded(task, tasks.size());
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Invalid format. Use: event [description] /from [start] /to [end]");
                    System.out.println("____________________________________________________________");
                }
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(" I'm sorry, I don't understand that command.");
                System.out.println("____________________________________________________________");
            }
        }

        // Close the scanner
        scanner.close();
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
