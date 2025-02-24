package luna;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Luna using FXML.
 */
public class Main extends Application {

    private final Luna luna = new Luna("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        assert stage != null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Luna");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLuna(luna);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
