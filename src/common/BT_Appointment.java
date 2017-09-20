package common;

import javafx.beans.property.SimpleStringProperty;


public class BT_Appointment {
	
//	int[] appDate = new int[3]; // [0] = year, [1] = month, [2] = day
//	int[] appTime = new int[2]; // [0] = hour, [1] = minutes 
//	String patientName;
//	int patientId;
//	String doctorName;
//	int doctorId;
//	String clinic;
//	docType doctorType;
//	boolean isFree;
	
    private final SimpleStringProperty dateT;
    private final SimpleStringProperty timeT;
    private final SimpleStringProperty docT;
    private final SimpleStringProperty patT;
    //private final String docType;
    private final int docId;

    public BT_Appointment(String date, String time, String doc, String patN, /*String dType,*/ int dId) {
        this.dateT = new SimpleStringProperty(date);
        this.timeT = new SimpleStringProperty(time);
        this.docT = new SimpleStringProperty(doc);
        this.patT = new SimpleStringProperty(patN);
        //docType = dType;
        docId = dId;
       
    }
 
    public String getDateT() {
        return dateT.get();
    }
    public void setDateT(String date) {
        dateT.set(date);
    }
        
    public String getTimeT() {
        return timeT.get();
    }
    public void setTimeT(String time) {
        timeT.set(time);
    }
    
    public String getDocT() {
        return docT.get();
    }
    public void setDocT(String doctor) {
        docT.set(doctor);
    }
    
    public String getPatT() {
        return patT.get();
    }
    public void setPatT(String patient) {
    	patT.set(patient);
    }
    
    public int getDocId() {
        return docId;
    }
    
        
}