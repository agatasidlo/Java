package multimediav1;

import java.io.DataInputStream;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable, Runnable {
    //static boolean opressed = false;
    static MediaPlayer mediaPlayer;

    @Override
    public void run() {
        ServerSocket ss;
            Socket s;
            DataInputStream dis;
            try {
		ss = new ServerSocket (6663);
		s = ss.accept();
		dis = new DataInputStream (s.getInputStream());
                String str;
                str = (String)dis.readUTF();
                while (!str.equals("ESC")) {
                    str = (String)dis.readUTF();
                    //System.out.println("Client action: "+ str);
                    switch (str) {
                        case "LEFT": {
                            duration = mediaPlayer.getMedia().getDuration(); 
                            duration = mediaPlayer.getCurrentTime().add(Duration.millis(-5000));
                            mediaPlayer.seek(duration);
                            break;
                        }
                        case "RIGHT": {
                            duration = mediaPlayer.getMedia().getDuration(); 
                            duration = mediaPlayer.getCurrentTime().add(Duration.millis(5000));
                            mediaPlayer.seek(duration);
                            break;
                        }
                        case "SPACE": {
                            if (mediaPlayer.getStatus() == Status.PLAYING) {
                                mediaPlayer.pause();
                            }
                            else if (mediaPlayer.getStatus() == Status.DISPOSED) {
                                mediaPlayer.play();
                            }
                            else {
                                //System.out.println(mediaPlayer.getCurrentTime());
                                //System.out.println();
                                mediaPlayer.play();
                            }
                            break;
                        }
                        case "O": {

//                            String listOfMovies = new String();
//                            Alert alert = new Alert (AlertType.NONE);
//                            
//                            alert.setTitle("Movie List");
//                            alert.setContentText("Wyswietlana tresc");
//                            alert.setContentText("Dodatek");
//                            alert.showAndWait();
                            
//                            Dialog dialog = new Dialog<>();
//                            dialog.setTitle("Movie list");
//                            dialog.setContentText("Movies here");
//                            dialog.show();
                            break;
                        }
                    }
                }
                dis.close();
                s.close();
                ss.close();
            } catch (Exception e) {System.out.println(e);}
    }
    
    
	@FXML
	private MediaView mediaView;
	static Duration duration;
	
	private String filePath;
	
	@FXML
private void openFile () {
		FileChooser fileChooser = new FileChooser ();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file *.mp4", "*.mp4");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(null);
		filePath = file.toURI().toString();
				
		if (filePath != null) {
			Media media = new Media (filePath);
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			
			//width and height of movie is flexible (same that media's w and h)
			DoubleProperty width = mediaView.fitWidthProperty();
			DoubleProperty height = mediaView.fitHeightProperty();
			width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
			
			
			mediaPlayer.play();
		}
	}
	
	@Override
	public void initialize (URL url, ResourceBundle bd) {
		
	}
}
