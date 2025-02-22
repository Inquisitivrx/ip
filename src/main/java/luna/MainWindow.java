package luna;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Luna luna;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image lunaImage = new Image(this.getClass().getResourceAsStream("/images/bot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Luna instance */
    public void setLuna(Luna l) {
        luna = l;
        String welcomeMessage = "Hello! I'm Luna!\nWhat can I do for you?";
        dialogContainer.getChildren().addAll(DialogBox.getLunaDialog(welcomeMessage, lunaImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Luna's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = luna.getResponse(input);

        assert response != null;
        assert !response.trim().isEmpty();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLunaDialog(response, lunaImage)
        );
        userInput.clear();

        if (Objects.equals(input, "bye")) {
            Platform.exit();
        }
    }
}
