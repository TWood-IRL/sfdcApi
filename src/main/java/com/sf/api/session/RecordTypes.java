package com.sf.api.session ;

import java.util.HashMap;
import java.util.Map;

import com.sf.api.properties.PropertiesFile;

/**
 * Class for retreiving record types id based on the org passed in environment... we found in 5 different orgs had multiple different record type ids for the same recordType org to org.. used this to combat that 
 * Had this before as a enum 
 * but that was due to our own org circumstances making a class instead .. and intialize as singleton to keep static .. values arent going to change once we have them initialized 
 * @author Thomas Woodhouse
 *
 */
 public class RecordTypes {
//		// 		Object 	 //DeveloperName   //initiallize this once and use it to store all the values 

	 protected  static Map<String , Map<String, String>> recordTypeMap = new HashMap<String, Map<String,String>>() ; 
	 
	 //once the class is initialized our RecordTypeDataProvider will provide the values we then need for the map correctly 
	 static RecordTypeDataProvider recordTypeDataProvider = RecordTypeDataProvider.getInstance() ;

	 /**
	  * 
	  * @param The SObject name if custom object append on the __c 
	  * @param developerName the developer Name (API name ) of the record type
	  * @return
	  */
	 public static String getId(String obj, String developerName){
		 return recordTypeMap.get(obj).get(developerName).toString() ; 
	 } 
	 
	 protected static void setMap( Map<String , Map<String, String>> updateMap){
		 recordTypeMap = updateMap ; 
		 
	 }  
//	//Combination of SObject-Name
//
//	/*
//	 * Initially was 
//	 *  public enum RecordTypes {
//	 *  					Object - RecordType .. used this then in the recordType data Provider class then 
//	 *   AccountCustomer("Account-Customer"), 
//
//	 */
//	
//	protected String id ; 
//	protected String theRecordType ; 
//	
//	PropertiesFile props = PartnerSession.getProps() ;  
//	static RecordTypeDataProvider recordTypeDataProvider = RecordTypeDataProvider.getInstance() ; 
//	
//	RecordTypes(String theRecordType){
//		this.theRecordType = theRecordType ; 
//	}
//	
//	public String getId(){
//		return this.id ; 
//	}
//	
//
//	protected void setId(String id){
//		this.id = id ;  
//	}
//
//	public static Object getRecordType(String string, String string2) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

	
}
