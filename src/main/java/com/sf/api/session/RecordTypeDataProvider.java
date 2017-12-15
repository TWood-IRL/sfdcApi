package com.sf.api.session;

import java.util.logging.Logger;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
/**
 * This class was iniatially for when the RecordTypes Class was a enum to provide the value, not really to relevant now 
 * 
 * @author Thomas Woodhouse
 *
 */
 class RecordTypeDataProvider {
	private static RecordTypeDataProvider instance; 
	private static final Logger LOG = Logger.getLogger(RecordTypeDataProvider.class.getName()) ; 

	private RecordTypeDataProvider(Class<RecordTypes> clazz) {
		RecordTypes[] enums = clazz.getEnumConstants() ; 
		QueryResult result = queryRecordTypes() ;
		
		for (RecordTypes recordType : enums){
			for(SObject record : result.getRecords()){
				if ( recordType.theRecordType.split("-")[0].equalsIgnoreCase(record.getField("SobjectType").toString())  && recordType.theRecordType.split("-")[1].equalsIgnoreCase(record.getField("DeveloperName").toString() )) {
					recordType.setId(record.getId());
				}
			}
		}
	}
	protected  static synchronized RecordTypeDataProvider getInstance(){
		if(instance== null )
			instance = new RecordTypeDataProvider(RecordTypes.class) ; 
		return instance ;
	}
	private static synchronized QueryResult queryRecordTypes(){
		try {
			return PartnerSession.getInstance().query("SELECT ID, DeveloperName, SobjectType from RecordType") ;
		} catch (ConnectionException e) {
			LOG.warning("Error performing query for the recordtypes");
		} 
		return null ; 
	}
}
