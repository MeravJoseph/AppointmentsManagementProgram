package server;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import common.*;
import common.BtUser.clinicType;
import common.BtUser.docType;
import common.NetworkMessage.msgType;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 */
public class DataServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  static public Vector<BtUser> logedUsers;
  
  DB dataBase = new DB();
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public DataServer(int port) 
  {
    super(port);
    dataBase.initConnection();
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {		
	  	NetworkMessage nMsg = (NetworkMessage)msg;
	    display("Message received: " + (nMsg.dataToString() + " from " + client));
	  	switch(nMsg.getType()){
	  		case LOGIN:
	  			authorizeLogin(nMsg, client);
	  			break;
	  		case SET_APPOINTMENT:
	  			setUserAppt(nMsg, client);
	  			break;
	  		case CANCEL_APPOINTMENT:
	  			cancelPatAppts(nMsg, client);
	  			break;
	  		case GET_PAT_APP:
	  			getPatientAppts(nMsg, client);
	  			break;
	  		case GET_DOC_TYPE_APP:
	  			getDocTypeAppts(nMsg, client);
	  			break;
	  		default: 
	  			// do nothing
	  	}
  }

  
  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("server: " + message);
  }
  
  /** 
   * @param msg
   * @param client
   * 
   * checks if a given user exist in the DB and returns its instance to the
   * client if exists or null otherwise.
   */
  // message data: {username, password, authorized, isDoctor}
  public void authorizeLogin(NetworkMessage msg, ConnectionToClient client){
	  
	  // preparing the response
	  NetworkMessage rspMsg = new NetworkMessage(msg);
	  String[] loginData = (String[]) rspMsg.getData();
	  BtUser user = dataBase.getUser(loginData[0], loginData[1]);
	  rspMsg.setData(loginData);
	  rspMsg.setRetObj(user);
	  
	  if(user != null){
		  loginData[2] = "yes"; //user exists and is authorized user
		  if(user.getIsDoctor()){
			  loginData[3] = "yes"; // user is a doctor
		  }
		  else{
			  loginData[3] = "no"; // user is a patient
		  }
	  }


	  
	  try {
		  	display("Message sent: " + rspMsg.dataToString());
			((ConnectionToClient) client).sendToClient(rspMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  
  
  public void getPatientAppts(NetworkMessage msg, ConnectionToClient client){
	// preparing the response
		  NetworkMessage rspMsg = new NetworkMessage(msg);
		  int patientId = (int) rspMsg.getData();
		  Vector<String[]> appts = dataBase.getPatientApp(patientId);
		  rspMsg.setType(msgType.GET_PAT_APP);
		  rspMsg.setData(appts);

		  try {
			  	display("Message sent: " + rspMsg.dataToString());
				((ConnectionToClient) client).sendToClient(rspMsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
  }
  
  public void getDocTypeAppts(NetworkMessage msg, ConnectionToClient client){
	  
	  // preparing the response
	  NetworkMessage rspMsg = new NetworkMessage(msg);
	  int[] data = (int[]) rspMsg.getData();
	  Vector<String[]> appts = dataBase.getDocTypeApp(data[0], data[1]);
	 
	  rspMsg.setData(appts);

	  try {
		  	display("Message sent: " + rspMsg.dataToString());
			((ConnectionToClient) client).sendToClient(rspMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  

  public void cancelPatAppts(NetworkMessage msg, ConnectionToClient client){
 	  boolean canceled = false;
 	  // preparing the response
 	  NetworkMessage rspMsg = new NetworkMessage(msg);
 	  String[] data = (String[]) rspMsg.getData();
 	  
 	  String docName = data[2];
 	  String[] str = docName.split("\\s+");
 	  String docFirstName = str[0];
 	  String docLastName = str[1];
 	  String[] dateStr = data[0].split("\\.");
 	  int[] date = {Integer.parseInt(dateStr[2]), Integer.parseInt(dateStr[1]),Integer.parseInt(dateStr[0])};
 	  String[] timeStr = data[1].split(":");
 	  int[] time = {Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1])};
 	  int docId = Integer.parseInt(data[3]);
 	  int patId = Integer.parseInt(data[4]);
 	  display("time: "+time[0]+":"+time[1]+ " date "+ date[2]+"."+date[1]+"."+date[0]);
 	  canceled = dataBase.cancelAppointment(docFirstName, docLastName, date, time, docId, patId);
 	  if(canceled){
 		  dataBase.setAppointment(docFirstName, docLastName, date, time, docId, -1, 1);
 	  }
 	
 	  
 	  rspMsg.setData(canceled);
 	  rspMsg.setType(msgType.CANCEL_APPOINTMENT);

 	  try {
 		  	display("Message sent: " + rspMsg.dataToString());
 			((ConnectionToClient) client).sendToClient(rspMsg);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
   }
   
   
  
 public void setUserAppt(NetworkMessage msg, ConnectionToClient client){
	  boolean schedualed = false;
	  // preparing the response
	  NetworkMessage rspMsg = new NetworkMessage(msg);
	  String[] data = (String[]) rspMsg.getData();
	  
	  String docName = data[2];
	  String[] str = docName.split("\\s+");
	  String docFirstName = str[0];
	  String docLastName = str[1];
	  String[] dateStr = data[0].split("\\.");
	  int[] date = {Integer.parseInt(dateStr[2]), Integer.parseInt(dateStr[1]),Integer.parseInt(dateStr[0])};
	  String[] timeStr = data[1].split(":");
	  int[] time = {Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1])};
	  int docId = Integer.parseInt(data[3]);
	  int patId = Integer.parseInt(data[4]);
	  display("time: "+time[0]+":"+time[1]+ " date "+ date[2]+"."+date[1]+"."+date[0]);
	  schedualed = dataBase.checkAndSetAppt(docFirstName, docLastName, date, time, docId, patId);
	  
	  rspMsg.setData(schedualed);
	  rspMsg.setType(msgType.SET_APPOINTMENT);

	  try {
		  	display("Message sent: " + rspMsg.dataToString());
			((ConnectionToClient) client).sendToClient(rspMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	dataBase.closeConnection();
    System.out.println("Server has stopped listening for connections.");
  }
  
  

  //Class methods ***************************************************
 
  
  
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    DataServer sv = new DataServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
