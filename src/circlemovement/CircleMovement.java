package circlemovement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CircleMovement extends Application {
    
    @Override
    public void start(Stage stage) {
        CircleMover cm = new CircleMover();

        Scene scene = new Scene(cm, 600, 600);

        stage.setScene(scene);

        stage.setTitle("Parapatetic Circles");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
