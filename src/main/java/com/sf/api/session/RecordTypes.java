package com.sf.api.session ;

import com.sf.api.properties.PropertiesFile;

/**
 * Class for retreiving record types based on the org passed in environment
 * @author Thomas Woodhouse
 *
 */
 public class  RecordTypes {
	//Combination of SObject-Name

	
	
	protected String id ; 
	protected String theRecordType ; 
	
	PropertiesFile props = PartnerSession.getProps() ;  
	static RecordTypeDataProvider recordTypeDataProvider = RecordTypeDataProvider.getInstance() ; 
	
	RecordTypes(String theRecordType){
		this.theRecordType = theRecordType ; 
		//initialize(theRecordType) ; 
	}
	
	public String getId(){
		return this.id ; 
	}
	
//	No Longer needed - populated through RecordTypeDataprovider noww 
//	private void initialize(String theRecordType) {
//		this.id = (props.getProperties().getProperty(theRecordType) != null ) ? props.getProperties().getProperty(theRecordType): null  ; 
//	}
	protected void setId(String id){
		this.id = id ;  
	}

	public static RecordTypes AccountBroker(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
