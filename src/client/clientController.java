package client;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Vector;

import application.BT_controller;
import common.*;
import common.BtUser.docType;
import common.NetworkMessage.msgType;
import ocsf.client.*;

public class clientController extends AbstractClient 
{
	  //Instance variables **********************************************
	  /**
	   * The interface type variable.  It allows the implementation of 
	   * the display method in the client.
	   */
	private boolean isDoctor;
	private int isLoggedIn = 0; // -1 retrying to log in, 0 not logged in, 1 logged in
	private String username;
	private String password;
	private BT_controller UI = new BT_controller();
	public static BtUser systemUser; 
	
	
	  
	  //Constructors ****************************************************
	  
	  /**
	   * Constructs an instance of the chat client.
	   *
	   * @param host The server to connect to.
	   * @param port The port number to connect on.
	   * @param clientUI The interface type variable.
	 * @throws IOException 
	   */
	  
	public clientController(String host, int port, BT_controller userInterface) throws IOException 
	  {
	    super(host, port); //Call the superclass constructor   
	    UI = userInterface;
		openConnection();
	  }

	  
	  //Instance methods ************************************************
	    
	  /**
	   * This method handles all data that comes in from the server.
	   *
	   * @param msg The message from the server.
	   */
	  public void handleMessageFromServer(Object msg) 
	  {
	    display("recived " + ((NetworkMessage)msg).dataToString());
		  
		NetworkMessage nMsg = (NetworkMessage)msg;
	  	switch(nMsg.getType()){
	  		case LOGIN:
	  			handleLogin(nMsg);
	  			break;
	  		case SET_APPOINTMENT:
	  			handleSetAppointment(nMsg);
	  			break;
	  		case CANCEL_APPOINTMENT:
	  			handleCancelAppointment(nMsg);
	  			break;
	  		case GET_PAT_APP:
	  			handleGetPatAppRsp(nMsg);
	  			break;
	  		case GET_DOC_TYPE_APP:
	  			handleDocTypeApp(nMsg);
	  			break;
	  			
	  		default: 
	  			// do nothing
		}
	    
	  }
	  
	  // handle the message from the server regarding login
	  // message data: {username, password, authorized, isDoctor}
	  private void handleLogin(NetworkMessage msg){
		  String[] loginData = (String[]) msg.getData();
		  BtUser retObj = msg.getRetObj();

		  
		  if(loginData[2] != null){
			  if(loginData[2].equalsIgnoreCase("yes")){
				  // user exists (login was authorized) - change to relevant window
				  // (patient window or doctor window).
				  isLoggedIn = 1;
				  if(retObj != null){
					  systemUser = (BtUser)msg.getRetObj();
				  }
				  else{
					  isLoggedIn = 0; //login error
				  }
				  if(loginData[3].equalsIgnoreCase("yes")){
					  isDoctor = true;
				  }
				  else{
					  isDoctor = false;
				  }
			  }
		  }
		  UI.loginAuthorized(isLoggedIn, isDoctor);
		  
		  
	  }
	  
	  private void handleGetPatAppRsp(NetworkMessage msg){
		  Vector<String[]> apptsStr = (Vector<String[]>)msg.getData();
		  Vector<BT_Appointment> appts = new Vector<BT_Appointment>();
		  for(int i=0; i<apptsStr.size(); i++){
			  // string[] = {date, time, docName, patName, docType}
			  appts.add(new BT_Appointment(apptsStr.get(i)[0], apptsStr.get(i)[1], apptsStr.get(i)[2], 
					  	apptsStr.get(i)[3],/* apptsStr.get(i)[4],*/ Integer.parseInt(apptsStr.get(i)[4])) );
		  }
		  UI.dispAppointments(appts);	
	  }
	  
	  
	  private void handleDocTypeApp(NetworkMessage msg){
		  Vector<String[]> apptsStr = (Vector<String[]>)msg.getData();
//		  Vector<String> uniqueDocs = new Vector<String>();
//		  for(int i=0; i<apptsStr.size(); i++){
//			  if(!(uniqueDocs.contains(apptsStr.get(i)[2]))){
//				  uniqueDocs.add(apptsStr.get(i)[2]);
//			  }
//		  }
		  Vector<BT_Appointment> appts = new Vector<BT_Appointment>();
			  //show doctor's appointments
			  for(int i=0; i<apptsStr.size(); i++){
				  // string[] = {date, time, docName, patName, docType}
				  appts.add(new BT_Appointment(apptsStr.get(i)[0], apptsStr.get(i)[1], apptsStr.get(i)[2], 
						  	apptsStr.get(i)[3],/* apptsStr.get(i)[4],*/ Integer.parseInt(apptsStr.get(i)[4])) );
			  }
			  UI.dispAppointments(appts);	  
	  }
	  
	  private void handleSetAppointment(NetworkMessage msg){
		  boolean success = (boolean) msg.getData();
		  UI.setAppointmentRsp(success);
	  }
	  
	  public void setChosenAppt(BT_Appointment appt){
		  
		  String[] mData = {appt.getDateT(), appt.getTimeT(), appt.getDocT(), Integer.toString(appt.getDocId()), Integer.toString(systemUser.getId())};
		  NetworkMessage msg = new NetworkMessage(msgType.SET_APPOINTMENT, mData);
		  try {
			display("Message sent: " + msg.dataToString());
			sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
	  }

	  private void handleCancelAppointment(NetworkMessage msg){
		  boolean success = (boolean) msg.getData();
		  UI.cancelAppointmentRsp(success);
	  }
	  
	 
	  
	  
	  public void sendCancelApp(BT_Appointment appt){
		  
		  String[] mData = {appt.getDateT(), appt.getTimeT(), appt.getDocT(), Integer.toString(appt.getDocId()), Integer.toString(systemUser.getId())};
		  NetworkMessage msg = new NetworkMessage(msgType.CANCEL_APPOINTMENT, mData);
		  try {
			display("Message sent: " + msg.dataToString());
			sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public void sendGetPatTypeApp(){
		  int mData = systemUser.getId();
		  
		  NetworkMessage msg = new NetworkMessage(msgType.GET_PAT_APP, mData);
		  try {
				display("Message sent: " + msg.dataToString());
				sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  
	  
	  
	  
	  /**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  public void handleMessageFromClientUI(String message)
	  {
	    try
	    {
	    	sendToServer(message);
	    }
	    catch(IOException e)
	    {
	      display("Could not send message to server.  Terminating client.");
	      quit();
	    }
	  }
	  
	  /**
	   * This method terminates the client.
	   */
	  public void quit()
	  {
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }
	  
	  /**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	  public void display(String message) 
	  {
	    System.out.println("client: " + message);
	  }
	  
	  

	  //Instance methods ************************************************

	 
	  public void sendLoginReq(String user, String pass){
		  username = user;
		  password = pass;
		  String[] mData = {username, password, null, null};
		  NetworkMessage loginMsg = new NetworkMessage(msgType.LOGIN, mData);
		  try {
			display("Message sent: " + loginMsg.dataToString());
			sendToServer(loginMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  }
	  
	  public void sendGetDocTypeApp(docType type){
		  int[] mData = {type.ordinal(), 1};
		  if(systemUser.getIsDoctor()){
			  mData[1] = 0;
		  }
		  
		  
		  NetworkMessage msg = new NetworkMessage(msgType.GET_DOC_TYPE_APP, mData);
		  try {
				display("Message sent: " + msg.dataToString());
				sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  
	  
	  
	  
	}
	//End of clientController class
