package common;

import java.util.Vector;

// user of Briut Tova system (can be either a doctor or a patient
public class BtUser implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum docType { Family, Cardiology, Orthopedic, OBGYN, Otorhinolaryngology, Gastroeneterolgy , Rheumatology , Eurology };
	public static enum clinicType { RamotYitzhak, BenDor, TelHanan };
	
	
	String firstName;
	String lastName;
	int id;
	String email;
	String username;
	String password;
	docType specialty;  // doctor type (family, YBGN etc.)
	clinicType clinic;
	boolean isDoctor; // false = patient, true = doctor
	private Vector<Appointment> appointments = null;

	public BtUser(String firstN, String lastN, int pId, String mail, String userN, String pass, 
					docType spec, clinicType clin){
		firstName = firstN;
		lastName = lastN;
		id = pId;
		email = mail;
		username = userN;
		password = pass;
		specialty = spec;
		clinic = clin;

		if(specialty != null){
			isDoctor = true;
		}
		else{
			isDoctor = false;
		}
		
	}
	
	// get variables functions **********************************************
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public int getId(){
		return id;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public docType getSpecialty(){
		return specialty;
	}
	
	public clinicType getClinic(){
		return clinic;
	}
	
	public boolean getIsDoctor(){
		return isDoctor;
	}
	
	public Vector<Appointment> getAppointments(){
		return appointments;
	}
	
	
}
