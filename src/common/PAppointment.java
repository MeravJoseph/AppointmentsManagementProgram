package common;

import common.BtUser.docType;
import javafx.beans.property.SimpleStringProperty;


public class PAppointment {
	
	int[] appDate = new int[3]; // [0] = year, [1] = month, [2] = day
	int[] appTime = new int[2]; // [0] = hour, [1] = minutes 
	String patientName;
	int patientId;
	String doctorName;
	int doctorId;
	String clinic;
	docType doctorType;
	boolean isFree;
	
    private final SimpleStringProperty firstBla;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
 
	public PAppointment(int[] aDate, int[] aTime, String pName, int pId, String dName, int dId,  
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
			lastName= null;
			firstBla=null;
			email=null;
			}
    
    public PAppointment(String fName, String lName, String email) {
        this.firstBla = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
    }
 
    public String getFirstName() {
        return firstBla.get();
    }
    public void setFirstName(String fName) {
    	firstBla.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String fName) {
        lastName.set(fName);
    }
    
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String fName) {
        email.set(fName);
    }
        
}