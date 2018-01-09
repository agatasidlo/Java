package application;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static Socket s;
	public static DataOutputStream dout;
	
	public static void openSocket() {
		try {
			s = new Socket ("IP number", 6663);					//here it must be IP number of server
			dout = new DataOutputStream (s.getOutputStream());
		}
		catch (Exception e) {System.out.println(e);}
	}
	
	public static void closeSocket() {
		try {
			dout.close();
			s.close();
		} catch (Exception e) {System.out.println(e);}
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("FXML.fxml"));
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle (KeyEvent event) {
					try {
					switch (event.getCode()) {
					case O: {
						dout.writeUTF("O");
						/*try {
							ServerSocket ss = new ServerSocket (6662);
							Socket serw = ss.accept();
							DataInputStream dis = new DataInputStream (serw.getInputStream());
							String str = (String)dis.readUTF();
							while (!str.equals("bye")) {
								System.out.println("Clients message:"+str);
								str = (String)dis.readUTF();
							}
							dis.close();
							serw.close();
							ss.close();
						}
						catch (Exception e) {System.out.println(e);}*/
						break;
					}
					case LEFT: /*System.out.println("Left arrow clicked");*/ dout.writeUTF("LEFT"); break;
					case RIGHT: dout.writeUTF("RIGHT"); break;
					case SPACE: dout.writeUTF("SPACE"); break;
					case ESCAPE: dout.writeUTF("ESC"); primaryStage.close(); break; //close all program
					}
					} catch (Exception e) {System.out.println(e);}
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		openSocket();
		launch(args);
		closeSocket();
	}
}
