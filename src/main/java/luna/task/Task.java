package luna.task;

import luna.exception.LunaException;

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
     * @throws LunaException If the file data is corrupted or invalid.
     */
    public static Task fromFileString(String fileLine) throws LunaException {
        final int MIN_TODO_PARTS = 3;
        final int MIN_DEADLINE_PARTS = 4;
        final int MIN_EVENT_PARTS = 5;

        String[] parts = fileLine.split(" \\| ");
        if (parts.length < MIN_TODO_PARTS) {
            throw new LunaException("Corrupted task data: " + fileLine);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < MIN_DEADLINE_PARTS) {
                    throw new LunaException("Corrupted deadline data: " + fileLine);
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < MIN_EVENT_PARTS) {
                    throw new LunaException("Corrupted event data: " + fileLine);
                }
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new LunaException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
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

    public String getDescription() {
        return description;
    }
}
