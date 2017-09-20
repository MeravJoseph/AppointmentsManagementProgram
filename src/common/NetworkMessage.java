package common;

import java.util.Vector;

public class NetworkMessage extends Object implements java.io.Serializable{
	
	public static enum msgType { LOGIN, SET_APPOINTMENT, CANCEL_APPOINTMENT, GET_PAT_APP, GET_DOC_TYPE_APP };
	
	private msgType type;
	private Object data;
	private BtUser retObj;
	
	public NetworkMessage(msgType mType, Object mData)
	{
		type = mType;
		data = mData;
	}
	
	public NetworkMessage(NetworkMessage msg)
	{
		this.type = msg.type;
		this.data = msg.data;
		this.retObj = msg.retObj;
	}
	
	public Object getData(){
		return data;
	}
	
	public void setData(Object mData){
		data = mData;
	}
	
	public msgType getType(){
		return type;
	}
	
	public void setType(msgType mType){
		type = mType;
	}
	
	public BtUser getRetObj(){
		return retObj;
	}
	
	public void setRetObj(BtUser obj){
		retObj = obj;
	}
	
	
	public String dataToString(){
		String dataStr = "";
	  	switch(type){
	  		case LOGIN:
	  			String[] loginData = (String[]) data;
	  			dataStr = "user: " + loginData[0] + " | password: " + loginData[1];
	  			dataStr += " | authorized: " + loginData[2] + " | doctor: " + loginData[3];
	  			break;
	  		
	  		default: 
	  	}
	  	
	  	return dataStr;
	}
	
}
