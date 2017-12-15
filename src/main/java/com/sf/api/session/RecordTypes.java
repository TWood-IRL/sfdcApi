package com.sf.api.session ;

import com.sf.api.properties.PropertiesFile;

/**
 * Class for retreiving record types based on the org passed in environment
 * @author Thomas Woodhouse
 *
 */
 public class  RecordTypes {
	//Combination of SObject-Name

	/*
	 * Initially was 
	 *  public enum RecordTypes {
	 *  
	 *   AccountCustomer("Account-Customer"), 

	 */
	
	protected String id ; 
	protected String theRecordType ; 
	
	PropertiesFile props = PartnerSession.getProps() ;  
	static RecordTypeDataProvider recordTypeDataProvider = RecordTypeDataProvider.getInstance() ; 
	
	RecordTypes(String theRecordType){
		this.theRecordType = theRecordType ; 
	}
	
	public String getId(){
		return this.id ; 
	}
	

	protected void setId(String id){
		this.id = id ;  
	}

	

	
}
