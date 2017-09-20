package server;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import application.BT_controller.threadType;
import common.Appointment;
import common.BtUser;
import common.BtUser.clinicType;
import common.BtUser.docType;

// System's data base
public class DB {
	
	Connection con;
	
	void initConnection(){
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			display("SQL connection succeed");
	    } catch (Exception ex) {/* handle the error*/}
	      
	    try {
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","student","student");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  // returns the system user if exists, null otherwise
	  BtUser getUser(String username, String password){
		  PreparedStatement pstmt;
		  try { // check if doctor
			pstmt = con.prepareStatement("SELECT * FROM doctors WHERE username = ?;");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next())
			{
				display("DB password: " + rs.getString("password") + " | input password: " + password);
				if(rs.getString("password").equals(password)){
					 display("exists");
					 display(docType.values()[0].toString());
					 BtUser user = new BtUser(rs.getString("firstName"),
							 				  rs.getString("lastName"),
							 			      rs.getInt("id"),
							 			      rs.getString("email"),
							 			      rs.getString("username"),
							 			      rs.getString("password"),
							 			      docType.values()[rs.getInt("type")],
							 			      clinicType.values()[rs.getInt("clinic")]);
					 return user;
				 }
			} 
			
			pstmt = con.prepareStatement("SELECT * FROM patients WHERE username = ?;");
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				display("DB password: " + rs.getString("password") + " | input password: " + password);
				if(rs.getString("password").equals(password)){
					 display("exists");
					 display(docType.values()[0].toString());
					 BtUser user = new BtUser(rs.getString("firstName"),
							 				  rs.getString("lastName"),
							 			      rs.getInt("id"),
							 			      rs.getString("email"),
							 			      rs.getString("username"),
							 			      rs.getString("password"),
							 			      null,
							 			      null);
					 return user;
				 }
			} 
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
			
		  
		  return null;
	  }
	  
	  BtUser getDoctor(int doctorId){
		  PreparedStatement pstmt;
		  try { 
			pstmt = con.prepareStatement("SELECT * FROM doctors WHERE id = ?;");
			pstmt.setInt(1, doctorId);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next())
			{
				 BtUser user = new BtUser(rs.getString("firstName"),
						 				  rs.getString("lastName"),
						 			      rs.getInt("id"),
						 			      rs.getString("email"),
						 			      rs.getString("username"),
						 			      rs.getString("password"),
						 			      docType.values()[rs.getInt("type")],
						 			      clinicType.values()[rs.getInt("clinic")]);
				 return user;
			} 
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
		
		  return null;
	 }	
	  
	  BtUser getPatient(int patientId){
		  PreparedStatement pstmt;
		  try { 
			pstmt = con.prepareStatement("SELECT * FROM patients WHERE id = ?;");
			pstmt.setInt(1, patientId);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next())
			{
				 BtUser user = new BtUser(rs.getString("firstName"),
						 				  rs.getString("lastName"),
						 			      rs.getInt("id"),
						 			      rs.getString("email"),
						 			      rs.getString("username"),
						 			      rs.getString("password"),
						 			      null,
						 			      null);
				 return user;
			} 
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
		
		  return null;
	 }	

	  public Vector<String> getDocList(){
		  PreparedStatement pstmt;
		  Vector<String> docList = new Vector<String>();
		  
		  try { 
			// create a mysql database connection
		      Class.forName("com.mysql.jdbc.Driver").newInstance();
			  display("SQL connection succeed");
		      con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","student","student");
			    
		      BtUser doctor;
			    String docName = "";
				pstmt = con.prepareStatement("SELECT * FROM doctors;");
				ResultSet rs = pstmt.executeQuery();
	
				while(rs.next())
				{
					 doctor = getDoctor(rs.getInt("id"));
					 docName = "Dr." + doctor.getFirstName() + " " + doctor.getLastName();
					 docList.add(docName);
				} 
			
			
		  }catch (Exception e)
		  {
			      System.err.println("Got an exception!");
			      System.err.println(e.getMessage());
			      return null;
		  }
		  return docList;
	  }
	  
	  
	  public boolean checkAndSetAppt(String docFirstName, String docLastName, int[] appDate, int[] appTime, int docId, int patId){
		  	PreparedStatement pstmt;
		    
		    try {
				pstmt = con.prepareStatement("SELECT * FROM appointments WHERE patId = ? AND hour = ? AND minutes = ? AND day = ? AND month = ? AND year = ? " +
		    		  "AND docId = ?;");
	
				pstmt.setInt (1, patId);
				pstmt.setInt (2, appTime[0]);
				pstmt.setInt (3, appTime[1]);
				pstmt.setInt (4, appDate[2]);
				pstmt.setInt (5, appDate[1]);
				pstmt.setInt (6, appDate[0]);
				pstmt.setInt (7, docId);
   
				ResultSet rs = pstmt.executeQuery();

				while(rs.next())
				{
					if (rs.getInt("free") == 0){
						return false;
					}
				}
				
		    } catch (SQLException e1) {
				// TODO Auto-generated catch block
				return false;
			}
		    
		    boolean setSuccess = false;
		    setSuccess = cancelAppointment(docFirstName, docLastName, appDate, appTime, docId, patId);
		    if(setSuccess){
		    	setSuccess = setAppointment(docFirstName, docLastName, appDate, appTime, docId, patId, 0);
		    }
		    
		    return setSuccess;
		    
		    
	  }
	  
	  
	  
	  // add an appointment to the appointments table
	  public boolean setAppointment(String docFirstName, String docLastName, int[] appDate, int[] appTime, int docId, int patId, int free){
		  try
		    {
		      // create a mysql database connection
		      Class.forName("com.mysql.jdbc.Driver").newInstance();
			  display("SQL connection succeed");
		      con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","student","student");
		      BtUser doctor = getDoctor(docId);

		      // the mysql insert statement
		      String query = " insert into appointments (day, month, year, hour, minutes, docId, patId, docType, free)"
		        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, appDate[2]);
		      preparedStmt.setInt (2, appDate[1]);
		      preparedStmt.setInt (3, appDate[0]);
		      preparedStmt.setInt (4, appTime[0]);
		      preparedStmt.setInt (5, appTime[1]);
		      preparedStmt.setInt (6, docId);
		      preparedStmt.setInt (7, patId);
		      preparedStmt.setInt (8, doctor.getId());
		      preparedStmt.setInt (9, free);

		      // execute the preparedstatement
		      preparedStmt.execute();
		      
		      
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		      return false;
		    }
					
		  return true;
	  }
	  
	  
	  
	  // add an appointment to the appointments table
	  public boolean cancelAppointment(String docFirstName, String docLastName, int[] appDate, int[] appTime, int docId, int patId){
		  try
		    {
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			  display("SQL connection succeed");
		      con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test","student","student");
	
		      // the mysql delete statement
		      String query = " delete from appointments "
		        + " WHERE patId = ? AND hour = ? AND minutes = ? AND day = ? AND month = ? AND year = ? " +
		    		  "AND docId = ?;";
		      // create the mysql delete preparedstatement
		      PreparedStatement pstmt = con.prepareStatement(query);
		      
			  pstmt.setInt (1, patId);
			  pstmt.setInt (2, appTime[0]);
			  pstmt.setInt (3, appTime[1]);
			  pstmt.setInt (4, appDate[2]);
			  pstmt.setInt (5, appDate[1]);
			  pstmt.setInt (6, appDate[0]);
			  pstmt.setInt (7, docId);
			  pstmt.execute();

		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		      return false;
		    }
					
		  return true;
	  }
	  
	  public Vector<Appointment> getDocApp(int doctorId, int[] aDate){
		    PreparedStatement pstmt;
		    Vector<Appointment> docAppts = new Vector<Appointment>();
		    int[] aTime = new int[2];
		    BtUser doctor = getDoctor(doctorId);
		    BtUser patient;
		    if(doctor == null){
		    	// doctor does not exist
		    	return null;
		    }
		    
		    try {
				pstmt = con.prepareStatement("SELECT * FROM appointments WHERE docId = ? " +
											" And day = ? AND month = ? AND year = ?;");
				pstmt.setInt(1, doctorId);
				pstmt.setInt(2, aDate[2]);
				pstmt.setInt(3, aDate[1]);
				pstmt.setInt(4, aDate[0]);
				ResultSet rs = pstmt.executeQuery();

				while(rs.next())
				{
					aDate[0] = rs.getInt("year");
					aDate[1] = rs.getInt("month");
					aDate[2] = rs.getInt("day");
					aTime[0] = rs.getInt("hour");
					aTime[1] = rs.getInt("minutes");
					
					patient = getPatient(rs.getInt("patId"));
					String patName = patient.getFirstName() + " " + patient.getLastName();
					String docName = doctor.getFirstName() + " " + doctor.getLastName();
					
					//(int[] aDate, int[] aTime, String pName, int pId, String dName, int dId,  
					//   String clinicN, boolean freeApp)
					try {
						docAppts.add(new Appointment(aDate,		
													 aTime, 
													 patName, 
													 rs.getInt("patId"), 
													 docName,
													 doctorId,
													 doctor.getClinic().toString(),
													 doctor.getSpecialty(),
													 false));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					display("testin clinic: " + doctor.getClinic().toString());
				} 
		    } catch (SQLException e1) {
				// TODO Auto-generated catch block
				return null;
			}
		    return docAppts;
	  }
	  
	  // string[] = {date, time, docName, patName, docType}
	  //freeAppts =1 (free appointments) , 0 (occupied appointments)

	  public Vector<String[]> getDocTypeApp(int docT, int freeAppts){
		    PreparedStatement pstmt;
		    Vector<String[]> docAppts = new Vector<String[]>();
		   

		    try {
				pstmt = con.prepareStatement("SELECT * FROM appointments WHERE docType = ? AND free = ?;");
				pstmt.setInt(1, docT);
				pstmt.setInt(2, freeAppts);
				ResultSet rs = pstmt.executeQuery();
				//String dTypeStr = docType.values()[docT].toString();
				
				while(rs.next())
				{
				    int[] aDate = new int[3];
				    int[] aTime = new int[2];
					aDate[0] = rs.getInt("year");
					aDate[1] = rs.getInt("month");
					aDate[2] = rs.getInt("day");
					aTime[0] = rs.getInt("hour");
					aTime[1] = rs.getInt("minutes");
					String docIdStr = Integer.toString(rs.getInt("docId"));
					
					BtUser doctor  =  getDoctor(rs.getInt("docId"));
					BtUser patient = getPatient(rs.getInt("patId"));

					
					String patName;
					String docName; 
					if(doctor == null){
						docName = null;
					}
					else{
						docName = doctor.getFirstName() + " " + doctor.getLastName();
					}
					
					if(patient == null){
						patName = null;
					}
					else{
						patName = patient.getFirstName() + " " + patient.getLastName();
					}
					
					//(int[] aDate, int[] aTime, String pName, int pId, String dName, int dId,  
					//   String clinicN, boolean freeApp)
						
						String temp = "";
						if(aDate[1] < 10){
							temp = aDate[2] + ".0" + aDate[1]; 
						}
						else{
							temp = aDate[2] + "." + aDate[1];
						}
						
						if(aDate[0] < 10){
							temp += ".0" + aDate[0];
						}
						else{
						   temp += "." + aDate[0];
						}
						
						String[] strArr = new String[5];
						strArr[0] = temp;
						if(aTime[0] < 10){
							temp = "0" +aTime[0] + ":";
						}
						else{
							temp = aTime[0] + ":";
						}
						
						if(aTime[1] < 10){
							temp += "0" + aTime[1];
						}
						else{
							temp += aTime[1];
						}
						strArr[1] = temp;
						strArr[2] = docName;
						strArr[3] = patName;
						//strArr[4] = dTypeStr;
						strArr[4] = docIdStr;
						docAppts.add(strArr);
					
				} 
				return docAppts;
		    } catch (SQLException e1) {
				// TODO Auto-generated catch block
				return null;
			}
	  }
	  

	  public Vector<String[]> getPatientApp(int patientId){
		    PreparedStatement pstmt;
		    Vector<String[]> patAppts = new Vector<String[]>();
		    BtUser doctor;
		    BtUser patient = getPatient(patientId);
		    if(patient == null){
		    	// doctor does not exist
		    	return null;
		    }
		    
		    try {
				pstmt = con.prepareStatement("SELECT * FROM appointments WHERE patId = ? ;");
				pstmt.setInt(1, patientId);
				ResultSet rs = pstmt.executeQuery();

				while(rs.next())
				{
					    int[] aDate = new int[3];
					    int[] aTime = new int[2];
						aDate[0] = rs.getInt("year");
						aDate[1] = rs.getInt("month");
						aDate[2] = rs.getInt("day");
						aTime[0] = rs.getInt("hour");
						aTime[1] = rs.getInt("minutes");
						
						patient = getPatient(rs.getInt("patId"));
						doctor =  getDoctor(rs.getInt("docId"));
						
						String patName;
						String docName; 
						if(doctor == null){
							docName = null;
						}
						else{
							docName = doctor.getFirstName() + " " + doctor.getLastName();
						}
						
						if(patient == null){
							patName = null;
						}
						else{
							patName = patient.getFirstName() + " " + patient.getLastName();
						}
						
						//(int[] aDate, int[] aTime, String pName, int pId, String dName, int dId,  
						//   String clinicN, boolean freeApp)
							//String docTypeStr = (docType.values()[rs.getInt("docType")]).toString();
							String temp = "";
							if(aDate[1] < 10){
								temp = aDate[2] + ".0" + aDate[1]; 
							}
							else{
								temp = aDate[2] + "." + aDate[1];
							}
							
							if(aDate[0] < 10){
								temp += ".0" + aDate[0];
							}
							else{
							   temp += "." + aDate[0];
							}
							
							String[] strArr = new String[5];
							strArr[0] = temp;
							if(aTime[0] < 10){
								temp = "0" +aTime[0] + ":";
							}
							else{
								temp = aTime[0] + ":";
							}
							
							if(aTime[1] < 10){
								temp += "0" + aTime[1];
							}
							else{
								temp += aTime[1];
							}
							strArr[1] = temp;
							strArr[2] = docName;
							strArr[3] = patName;
							//strArr[4] = docTypeStr;
							strArr[4] = Integer.toString(rs.getInt("docId"));
							patAppts.add(strArr);
						 
				} 
		    } catch (SQLException e1) {
				// TODO Auto-generated catch block
				return null;
			}
		    return patAppts;
	  }
	  

	  
	  public void display(String message) 
	  {
	    System.out.println("DB: " + message);
	  }
}
