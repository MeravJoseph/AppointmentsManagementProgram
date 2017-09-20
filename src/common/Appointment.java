package common;

import java.time.LocalDate;

import common.BtUser.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {

	
	int[] appDate = new int[3]; // [0] = year, [1] = month, [2] = day
	int[] appTime = new int[2]; // [0] = hour, [1] = minutes 
	String patientName;
	int patientId;
	String doctorName;
	int doctorId;
	String clinic;
	docType doctorType;
	boolean isFree;
	
	
	private final SimpleStringProperty dateT;
	private final SimpleStringProperty timeT;
	private final SimpleStringProperty docT;
	private final SimpleStringProperty patT;
	
	
	public Appointment(int[] aDate, int[] aTime, String pName, int pId, String dName, int dId,  
					   String clinicN, docType dType, boolean freeApp){
		appDate = aDate;
		appTime = aTime;
		patientName = pName;
		patientId = pId;
		doctorName = dName;
		doctorId = dId;
		clinicN = clinic;
		doctorType = dType;
		isFree = freeApp;
		
		String content = appDate[2] + "." + appDate[1] + "." + appDate[0];
		dateT = new SimpleStringProperty(content);
		content = appTime[0] + ":" + appTime[1];
		timeT = new SimpleStringProperty(content);
		content = "Dr " + dName;
		docT = new SimpleStringProperty(content);
		patT = new SimpleStringProperty(patientName);
		
	}
	
	public Appointment(String dt, String tm, String docN, String patN){
		dateT = new SimpleStringProperty(dt);
		timeT = new SimpleStringProperty(tm);
		docT = new SimpleStringProperty(docN);
		patT = new SimpleStringProperty(patN);
	}
	
	
	
	public int[] getAppDate(){
		return appDate;
	}
	
	public int[] getAppTime(){
		return appTime;
	}
	
	public String getPatientName(){
		return patientName;
	}
	
	public int getPatientId(){
		return patientId;
	}
	
	public String getDoctorName(){
		return doctorName;
	}
	
	public int getDoctorId(){
		return doctorId;
	}
	
	public String getClinic(){
		return clinic;
	}
	
	public docType getDocType(){
		return doctorType;
	}
	
	public boolean getAppIsFree(){
		return isFree;
	}
	
	public String getDateT(){
		return dateT.get();
	}
	
	public String getTimeT(){
		return timeT.get();
	}
	
	public String getDocT(){
		return docT.get();
	}
	
	public String getPatT(){
		return patT.get();
	}
	
}
