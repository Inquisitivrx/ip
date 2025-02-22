package luna;

import luna.task.Task;
import luna.exception.LunaException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance to manage file operations.
     *
     * @param filePath The file path to store task data.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return A list of tasks loaded from the file.
     * @throws LunaException If there is an issue reading the file.
     */
    public List<Task> loadTasks() throws LunaException {
        List<Task> tasks = new ArrayList<>();
        File file = getFile();

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromFileString(line));
            }
        } catch (IOException e) {
            throw new LunaException("Error reading task file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws LunaException If there is an issue writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws LunaException {
        File file = getFile();
        ensureParentDirectoryExists(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new LunaException("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Gets the file object for the specified file path.
     *
     * @return The File object.
     */
    private File getFile() {
        return new File(filePath);
    }

    /**
     * Ensures that the parent directory of the file exists.
     *
     * @param file The file whose parent directory should be checked.
     * @throws LunaException If directory creation fails.
     */
    private void ensureParentDirectoryExists(File file) throws LunaException {
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            try {
                Files.createDirectories(parentDirectory.toPath());
            } catch (IOException e) {
                throw new LunaException("Failed to create directory: " + e.getMessage());
            }
        }
    }
}
