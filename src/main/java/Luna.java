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
            } else if (userInput.startsWith("mark ")) {
                // Mark a task as done
                try {
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    Task task = tasks.get(taskNumber - 1);
                    task.markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + task);
                    System.out.println("____________________________________________________________");
                } catch (Exception e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Invalid task number. Please try again.");
                    System.out.println("____________________________________________________________");
                }
            } else if (userInput.startsWith("unmark ")) {
                // Mark a task as not done
                try {
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    Task task = tasks.get(taskNumber - 1);
                    task.markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + task);
                    System.out.println("____________________________________________________________");
                } catch (Exception e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Invalid task number. Please try again.");
                    System.out.println("____________________________________________________________");
                }
            } else {
                // Add user input to the list as a new task
                tasks.add(new Task(userInput));
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }

        // Close the scanner
        scanner.close();
    }
}
