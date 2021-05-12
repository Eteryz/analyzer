import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println(new URL("file:///"+Paths.get("").toAbsolutePath().toString()));
//        Parent root = FXMLLoader.load(new URL("file:///" + Paths.get("").toAbsolutePath().toString() +
//                "\\src\\main\\resources\\mainWindow.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("view\\mainWindow.fxml"));
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
