package luna;

import luna.task.Event;
import luna.task.Deadline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LunaTest {

    @Test
    public void testDeadlineCreationAndToString() {
        Deadline deadline = new Deadline("return book", "2/12/2019 1800");
        String expected = "[D][ ] return book (by: Dec 2 2019, 6:00 pm)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void testDeadlineFileStringFormat() {
        Deadline deadline = new Deadline("return book", "2/12/2019 1800");
        String expected = "D | 0 | return book | 2/12/2019 1800";
        assertEquals(expected, deadline.toFileString());
    }

    @Test
    public void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Deadline("return book", "2019-12-02");
        });
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    public void testDeadlineMarkAsDone() {
        Deadline deadline = new Deadline("return book", "2/12/2019 1800");
        deadline.markAsDone();
        assertTrue(deadline.isDone());
        String expected = "[D][X] return book (by: Dec 2 2019, 6:00 pm)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void testEventCreationAndToString() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        String expected = "[E][ ] project meeting (from: Mon 2pm to: 4pm)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void testEventFileStringFormat() {
        Event event = new Event("project meeting", "5/11/2023 1400", "5/11/2023 1600");
        String expected = "E | 0 | project meeting | 5/11/2023 1400 | 5/11/2023 1600";
        assertEquals(expected, event.toFileString());
    }

    @Test
    public void testEventMarkAsDone() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        event.markAsDone();
        assertTrue(event.isDone());
        String expected = "[E][X] project meeting (from: Mon 2pm to: 4pm)";
        assertEquals(expected, event.toString());
    }

}
