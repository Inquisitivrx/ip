package luna.task;

import java.io.IOException;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Checks if the task is completed.
     *
     * @return {@code true} if the task is done, otherwise {@code false}.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Converts the task to a file-compatible string format.
     *
     * @return A formatted string representing the task.
     */
    public abstract String toFileString();

    /**
     * Parses a task from a file string representation.
     *
     * @param fileLine The string representation of a task from a file.
     * @return A Task object.
     * @throws IOException If the file data is corrupted or invalid.
     */
    public static Task fromFileString(String fileLine) throws IOException {
        String[] parts = fileLine.split(" \\| ");
        if (parts.length < 3) {
            throw new IOException("Corrupted data: " + fileLine);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        try {
            switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) todo.markAsDone();
                    return todo;
                case "D":
                    if (parts.length < 4) throw new IOException("Corrupted deadline data");
                    Deadline deadline = new Deadline(description, parts[3]);
                    if (isDone) deadline.markAsDone();
                    return deadline;
                case "E":
                    if (parts.length < 5) throw new IOException("Corrupted event data");
                    Event event = new Event(description, parts[3], parts[4]);
                    if (isDone) event.markAsDone();
                    return event;
                default:
                    throw new IOException("Unknown task type: " + type);
            }
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid date format in stored data: " + e.getMessage());
        }
    }

    /**
     * Returns a string representation of the task.
     *
     * @return The task string.
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }
}
