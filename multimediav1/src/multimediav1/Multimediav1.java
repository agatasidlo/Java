package multimediav1;

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Multimediav1 extends Application implements Runnable {
    static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
                    stage=primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
			Scene scene = new Scene(root);
			stage.setFullScreen(true);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
            //ExecutorService pool = Executors.newFixedThreadPool(2);
            
            Runnable sock = new Multimediav1();
            Runnable window = new FXMLController();
            Thread sockThr = new Thread(sock);
            Thread windowThr = new Thread(window);
            sockThr.start();
            windowThr.start();
	}

    @Override
    public void run() {
        launch();
    }
}
