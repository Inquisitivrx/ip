import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Luna {
    private static final String FILE_PATH = "./data/luna.txt";

    public static void main(String[] args) {
        ArrayList<Task> tasks = loadTasks();
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Luna");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            try {
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println("____________________________________________________________");
                } else if (userInput.startsWith("mark ")) {
                    int taskIndex = getTaskIndex(userInput, tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    saveTasks(tasks);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks.get(taskIndex));
                    System.out.println("____________________________________________________________");
                } else if (userInput.startsWith("unmark ")) {
                    int taskIndex = getTaskIndex(userInput, tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    saveTasks(tasks);
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks.get(taskIndex));
                    System.out.println("____________________________________________________________");
                } else if (userInput.startsWith("todo ")) {
                    String description = userInput.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new LunaException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(description));
                    saveTasks(tasks);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (userInput.startsWith("deadline ")) {
                    String[] parts = userInput.substring(9).split(" /by ");
                    if (parts.length < 2) {
                        throw new LunaException("Invalid deadline format. Use: deadline <description> /by <time>");
                    }
                    tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
                    saveTasks(tasks);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (userInput.startsWith("event ")) {
                    String[] parts = userInput.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new LunaException("Invalid event format. Use: event <description> /from <start> /to <end>");
                    }
                    tasks.add(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    saveTasks(tasks);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (userInput.startsWith("delete ")) {
                    int taskIndex = getTaskIndex(userInput, tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    saveTasks(tasks);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removedTask);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else {
                    throw new LunaException("I'm sorry, but I don't recognize that command.");
                }
            } catch (LunaException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }

    private static int getTaskIndex(String userInput, int size) throws LunaException {
        try {
            int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= size) {
                throw new LunaException("Task index is out of range.");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new LunaException("Invalid task index. Please provide a valid number.");
        }
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs(); // Ensure directory exists
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks; // Return empty list if file does not exist
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Task task = Task.fromFileString(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
