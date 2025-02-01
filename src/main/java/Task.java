import java.io.IOException;

abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public abstract String toFileString();

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


    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }
}
