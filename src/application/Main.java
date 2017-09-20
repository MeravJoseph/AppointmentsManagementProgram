package application;
	
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import server.DataServer;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	public static Stage loginStage = new Stage();
	private static Stage doctorStage = new Stage();
	private static Stage patientStage = new Stage();
	private static Stage exitStage = new Stage();
	public static Stage currStage = new Stage();
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		 // constructing our scene
		 URL loginUrl = getClass().getResource("login_scene.fxml");
		 URL exitUrl = getClass().getResource("exit_scene.fxml");
		 URL doctorUrl = getClass().getResource("doctor_scene.fxml");
		 URL patientUrl = getClass().getResource("patient_scene.fxml");
		 AnchorPane loginPane = FXMLLoader.load( loginUrl );
		 AnchorPane exitPane = FXMLLoader.load( exitUrl );
		 AnchorPane doctorPane = FXMLLoader.load( doctorUrl );
		 AnchorPane patientPane = FXMLLoader.load( patientUrl );
		 
		 
		 Scene loginScene = new Scene( loginPane );
		 Scene exitScene = new Scene( exitPane );
		 Scene doctorScene = new Scene( doctorPane );
		 Scene patientScene = new Scene( patientPane );

		 // setting the stage
		 loginStage = primaryStage;
		 loginStage.setScene( loginScene );
		 exitStage.setScene( exitScene );
		 doctorStage.setScene( doctorScene );
		 patientStage.setScene( patientScene );
		 
		 loginStage.setTitle( "Briut Tova" );
		 exitStage.setTitle( "Briut Tova" );
		 doctorStage.setTitle( "Briut Tova" );
		 patientStage.setTitle( "Briut Tova" );
		 loginStage.show();
		 currStage = loginStage;
		 
	}
	
	public static void switchStageLoginToDoctor(){
		doctorStage.show();
		loginStage.close();
		currStage = doctorStage;
	}
	
	public static void switchStageLoginToPatient(){
		patientStage.show();
		loginStage.close();
		currStage = patientStage;
	}
	
	public static void setExitStage(){
		exitStage.show();
		currStage.close();
		currStage = exitStage;
	}
	
	


	
	public static void main(String[] args) {
		int port = 5555;
		//String host = "127.0.0.1";
		DataServer server = new DataServer(port);
		server.main(args);
		launch(args);
	    
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
