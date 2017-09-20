package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import server.DB;

import java.io.IOException;

import client.*;
import common.Appointment;
import common.BT_Appointment;
import common.BtUser.docType;
import common.PAppointment;



public class BT_controller implements Initializable , Runnable{

	public static enum threadType {LOGIN_REQ, LOGIN_RESP, LOGOUT, DOC_LIST_REQ};
	
    @FXML
    private Pane loginPane;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField = new TextField();

    @FXML
    private Button loginBtn;
    
    @FXML
    private Text loginError = new Text();

    @FXML
    private ChoiceBox<String> chooseDoc = new ChoiceBox<String>();
    
    @FXML
    private ChoiceBox<String> chooseDocType = new ChoiceBox<String>();

    @FXML
    private Button patOkBtn = new Button();

    @FXML
    private AnchorPane patientDialog = new AnchorPane();
    
    @FXML
    private TableView<BT_Appointment> apptsTable;

    @FXML
    private TableColumn<BT_Appointment, String> dateCol = new TableColumn("Date");

    @FXML
    private TableColumn<BT_Appointment, String> timeCol = new TableColumn("Time");

    @FXML
    private TableColumn<BT_Appointment, String> doctorCol = new TableColumn("Doctor");
    
    @FXML
    private TableColumn<BT_Appointment, String> patientCol = new TableColumn("Patient");
    
    static int i =0;
    final ObservableList<BT_Appointment> apptsData = FXCollections.observableArrayList();

    @FXML
    private Text appSetError = new Text();
    
    @FXML
    private Button setBtn = new Button();


    @FXML
    private Button cancelBtn  = new Button();
    
    
    int port = 5555;
    String host = "127.0.0.1";
    
    private clientController client;
    private static Object[] threadData;
    
    BT_Appointment selectedAppt;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
			client = new clientController(host, port, this);
			for(int i = 0; i<docType.values().length; i++){
				chooseDocType.getItems().add(docType.values()[i].toString());
			}

			DB dataB = new DB();
			Vector<String> docList = new Vector<String>();
			docList = dataB.getDocList();
			for(int i = 0; i<docList.size(); i++){
				chooseDoc.getItems().add(docList.get(i));
			}
			
		    dateCol.setCellValueFactory(new PropertyValueFactory<BT_Appointment,String>("dateT"));
		    timeCol.setCellValueFactory(new PropertyValueFactory<BT_Appointment,String>("timeT"));
		    doctorCol.setCellValueFactory(new PropertyValueFactory<BT_Appointment,String>("docT"));
		    patientCol.setCellValueFactory(new PropertyValueFactory<BT_Appointment,String>("patT"));
			
//		    dateCol.setCellValueFactory(new PropertyValueFactory<PAppointment,String>("firstBla"));
//		    timeCol.setCellValueFactory(new PropertyValueFactory<PAppointment,String>("lastName"));
		    
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    public void run() {
		int threadNum = Integer.parseInt(Thread.currentThread().getName());
		switch(threadType.values()[threadNum]){
			case LOGIN_REQ:
				// invoke client's login request from server
				client = (clientController)threadData[2];
				String username = threadData[0].toString();
				String password = threadData[1].toString();
				client.sendLoginReq(username, password);
				break;
			case LOGIN_RESP:
				Platform.runLater(new Runnable(){
					@Override
				    public void run() {
						String isAuthorized = (String)threadData[0];
						if(isAuthorized != null){ 
							// user exist. check type and update UI 
							String isDoctor = (String)threadData[1];
							if(isDoctor.equals("isDoctor")){
								Main.switchStageLoginToDoctor();
							}
							else{ // user is a patient
								Main.switchStageLoginToPatient();
							}
						}
						else{ // user does not exists
							Text loginError = (Text)threadData[1];
							loginError.setText("Login has failed. One or more of the detailes is incorrect.");
							System.out.println("got here txt");
						}
				    }
				});
				break;
			 case LOGOUT:
				 Platform.runLater(new Runnable(){
						@Override
					    public void run() {
							//TODO: solve text cleaning
							userNameField = new TextField();
							
					    	//passwordField = ((TextField)threadData[1]);
					    	//reset text fields 
							
					    	Main.setExitStage();
					    }
				 });
			 default:
				// do nothing
		}
		 
 	}
    
    
    
    public void docListReq(){
    	threadData = new Object[1];
    	threadData[0] = client;
    	try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// creating and calling the thread
    	String threadName = Integer.toString(threadType.DOC_LIST_REQ.ordinal());
    	Thread t=new Thread(new BT_controller(),threadName);
    	t.start();	
    	
    }
    
    
    @FXML
    /** asks the client to send message to the server to check if user 
     * exists and its details
     * 
     */
    void loginToSystem(ActionEvent event) {
    	// loading needed data to the thread
    	threadData = new Object[3];
    	threadData[0] = userNameField.getText();
    	threadData[1] = passwordField.getText();
    	threadData[2] = client;
    	try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// creating and calling the thread
    	String threadName = Integer.toString(threadType.LOGIN_REQ.ordinal());
    	Thread t=new Thread(new BT_controller(),threadName);
    	t.start();	
    	
    }
    
    /** invoke window switch if login is authorized, otherwise invokes message
    *   print of wrong identification details
    */
    public void loginAuthorized(int isAuthorized, boolean isDoctor){
    	String threadName = Integer.toString(threadType.LOGIN_RESP.ordinal());
    	threadData = new Object[2];
    	if(isAuthorized == 1){
    		// update screen according to user type
    		threadData[0] = "authorized";
    		if(isDoctor){
    			threadData[1] = "isDoctor";
    		}
    		else{
    			threadData[1] = "isPatient";
    		}
    	}
    	else if(isAuthorized == -1){
    		loginError.setText("user is already logged in");
    	}
    	else{
    		// update error text object
    		threadData[0] = null;
    		threadData[1] = loginError; 
    	}
    	
    	Thread t=new Thread(new BT_controller(),threadName);
    	t.start();	
    	
    }
    
    
    public void setAppointmentRsp(boolean appWasSet){
    	if(appWasSet){
    		appSetError.setText("The appointment was set");
    	}
    	else{
    		appSetError.setText("Error. please try again later");
    	}
    }
    
    
    public void cancelAppointmentRsp(boolean appWasCanceled){
    	if(appWasCanceled){
    		appSetError.setText("The appointment was canceled");
    	}
    	else{
    		appSetError.setText("Error. please try again later");
    	}
    }
    
    // logout of the system
    @FXML
    void logout(ActionEvent event) {
    	// creating and calling the thread
    	threadData = new Object[2];
    	threadData[0] = userNameField;
    	threadData[1] = passwordField; 
    	String threadName = Integer.toString(threadType.LOGOUT.ordinal());
    	Thread t=new Thread(new BT_controller(),threadName);
    	t.start();	
    	
    }


    @FXML
    void getAppointmentReq(ActionEvent event){
    	patOkBtn.setVisible(true);
    	chooseDocType.setVisible(true);
    	setBtn.setVisible(true);
    	cancelBtn.setVisible(false);
    	try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(client.systemUser.getIsDoctor()){
    		// get Doctor's appointments
    		try {
    			client.openConnection();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		client.sendGetDocTypeApp(client.systemUser.getSpecialty());
    	}
    	else{ // patient appointment setting
	    	if(chooseDocType.getValue() == null){
	    		// client hasn't chose a speciaty yet - display Specialty options
	    		chooseDocType.setOpacity(100);
	    		patOkBtn.setOpacity(100);
	    	}
	    	else if(chooseDoc.getValue() == null){
	    		// chose speciality type
	    		String type = chooseDocType.getValue();
	    		docType chosenType = null;
	    		switch(type){
	    		case "Family":
	    			chosenType = docType.Family;
	    			break;
	    		case "Cardiology":
	    			chosenType = docType.Cardiology;
	    			break;
	    		case "Orthopedic":
	    			chosenType = docType.Orthopedic;
	    			break;
	    		case "OBGYN":
	    			chosenType = docType.OBGYN;
	    			break;
	    		case "Otorhinolaryngology":
	    			chosenType = docType.Otorhinolaryngology;
	    			break;
	    		case "Gastroeneterolgy":
	    			chosenType = docType.Gastroeneterolgy;
	    			break;
	    		case "Rheumatology":
	    			chosenType = docType.Rheumatology;
	    			break;
	    		case "Eurology":
	    			chosenType = docType.Eurology;
	    			break;
	    		}
	    		
	    		client.sendGetDocTypeApp(chosenType);
	    	}
    	}
    	
    }
    
    @FXML
    void setAppointmentReq(ActionEvent event){
    	patOkBtn.setVisible(true);
    	chooseDocType.setVisible(true);
    	setBtn.setVisible(true);
    	cancelBtn.setVisible(false);
    	chooseDocType.setVisible(true);
    	try {
			  client.openConnection();
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		client.setChosenAppt(selectedAppt);
    }
    
    @FXML
    void cancelAppointmentReq(ActionEvent event){
    	patOkBtn.setVisible(false);
    	chooseDocType.setVisible(false);
    	setBtn.setVisible(false);
    	cancelBtn.setVisible(true);
    	apptsTable.setVisible(true);
    	appSetError.setText(" ");
    	try {
			  client.openConnection();
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		client.sendGetPatTypeApp();
    }
    
    @FXML
    void cancelChosenAppointment(ActionEvent event){
    	patOkBtn.setVisible(false);
    	chooseDocType.setVisible(false);
    	setBtn.setVisible(false);
    	cancelBtn.setVisible(true);
    	apptsTable.setVisible(true);
    	appSetError.setText(" ");
    	try {
			  client.openConnection();
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }

		client.sendCancelApp(selectedAppt);
    }
    
    
    public void dispAppointments(Vector <BT_Appointment> appointments){
    	apptsData.clear();
    	for(int i=0; i<appointments.size(); i++){
    		apptsData.add(appointments.get(i));
    	}
    	  	
    	apptsTable.setItems(apptsData);
    	apptsTable.setVisible(true);
    	
    	
    	apptsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BT_Appointment>(){
    		@Override
    		public void changed(ObservableValue<? extends BT_Appointment> observable, BT_Appointment oldValue, 
    				BT_Appointment newValue){
    			selectedAppt = newValue;
    		}
    	});
		
    }
    
    
    
    void dispExistingAppointments(){
    	
    }
}
