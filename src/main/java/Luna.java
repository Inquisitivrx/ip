import java.util.Scanner;

public class Luna {
    public static void main(String[] args) {
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
                // Exit if the user types "bye"
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else {
                // Echo the command entered by the user
                System.out.println("____________________________________________________________");
                System.out.println(" " + userInput);
                System.out.println("____________________________________________________________");
            }
        }

        // Close the scanner
        scanner.close();
    }
}
