package com.sf.api.session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
/**
 * This class was iniatially for when the RecordTypes Class was a enum to provide the value, not really to relevant now 
 * Making this instead a class with a method call to check record types 
 * 
 * @author Thomas Woodhouse
 *
 */
 
class RecordTypeDataProvider{
	
		private static RecordTypeDataProvider instance; 
		private static final Logger LOG = Logger.getLogger(RecordTypeDataProvider.class.getName()) ; 
	
		private RecordTypeDataProvider(Class<RecordTypes> clazz) throws NoSuchFieldException, SecurityException {
			QueryResult result = queryRecordTypes() ; // List of recordTypes 
			Map<String , Map<String, String>> recordTypeMap = new HashMap<String, Map<String,String>>() ;
			//Here we do our jiggery pokery to populate the RecordType class 
			for(SObject record : result.getRecords()){ //for each  recordType we want to sort them based on sobject and then on the developerName 
				if(recordTypeMap.containsKey(record.getField("SobjectType").toString())  ){ // the key exists add to existing ie. the Object
					 Map<String,String> theObject = 	recordTypeMap.get(record.getField("SobjectType")) ; 
					 theObject.put(record.getField("DeveloperName").toString(), record.getField("Id").toString()) ; //the developer Name and the Id as the value 				 
					 //add back in ..
					 recordTypeMap.put(record.getField("SobjectType").toString(), theObject) ; 
				}
				else{ // key doesnt exist
					 Map<String,String> theObject = 	new HashMap<String, String>() ; 
					 theObject.put(record.getField("DeveloperName").toString(), record.getField("Id").toString()) ;
					 recordTypeMap.put(record.getField("SobjectType").toString(), theObject) ; 
				}
			}
			RecordTypes.setMap(recordTypeMap);	
		}
		protected  static synchronized RecordTypeDataProvider getInstance(){
			if(instance== null )
				try {
					instance = new RecordTypeDataProvider(RecordTypes.class) ;
				} catch (Exception e) {
					LOG.warning("Error creating instance of RecordTypes");
					e.printStackTrace();
				} 
			return instance ;
		}
		private static synchronized QueryResult queryRecordTypes(){
			try {
				return PartnerSession.getInstance().query("SELECT ID, DeveloperName, SobjectType from RecordType order by SobjectType") ;
			} catch (ConnectionException e) {
				LOG.warning("Error performing query for the recordtypes");
			} 
			return null ; 
		}
}

//When the RecordType class was a enum 
//class RecordTypeDataProvider {
//	private static RecordTypeDataProvider instance; 
//	private static final Logger LOG = Logger.getLogger(RecordTypeDataProvider.class.getName()) ; 
//
//	private RecordTypeDataProvider(Class<RecordTypes> clazz) {
//		RecordTypes[] enums = clazz.getEnumConstants() ; 
//		QueryResult result = queryRecordTypes() ;
//		
//		for (RecordTypes recordType : enums){
//			for(SObject record : result.getRecords()){
//				if ( recordType.theRecordType.split("-")[0].equalsIgnoreCase(record.getField("SobjectType").toString())  && recordType.theRecordType.split("-")[1].equalsIgnoreCase(record.getField("DeveloperName").toString() )) {
//					recordType.setId(record.getId());
//				}
//			}
//		}
//	}
//	protected  static synchronized RecordTypeDataProvider getInstance(){
//		if(instance== null )
//			instance = new RecordTypeDataProvider(RecordTypes.class) ; 
//		return instance ;
//	}
//	private static synchronized QueryResult queryRecordTypes(){
//		try {
//			return PartnerSession.getInstance().query("SELECT ID, DeveloperName, SobjectType from RecordType") ;
//		} catch (ConnectionException e) {
//			LOG.warning("Error performing query for the recordtypes");
//		} 
//		return null ; 
//	}
//}
