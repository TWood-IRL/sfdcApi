package com.sf.api.session; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import com.sf.api.properties.PropertiesFile;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.Email;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.SendEmailResult;
import com.sforce.soap.partner.UpsertResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
/**
 * Used to establish connection and perform functions within Salesforce 
 * ie. Update, delete, insert etc .
 * Querys and results
 * @author ahv15
 * 
 * Need to refactor logging to use log4j
 *
 */
public class PartnerSession {
	private static PartnerConnection connection;
	private static final Logger LOG = Logger.getLogger(PartnerSession.class.getName()) ; 
	private static  String org = "" ; 
	private static PartnerSession instance; 

	private static final String PROD_AUTH = "https://login.salesforce.com/services/Soap/u/41.0/"  ; 
	private static final String SANDBOX_AUTH = "https://test.salesforce.com/services/Soap/u/41.0/"  ; 

	
	private static PropertiesFile props = new PropertiesFile() ;
	

 	private PartnerSession() {
		org = PropertiesFile.org ;
		File apiLog = new File("./APILoggingFile.log") ;
		String endPoint = (props.getType()  == "SANDBOX" ) ? SANDBOX_AUTH : PROD_AUTH ; 
		if(apiLog.exists()) // if the file exists clear contents
		{
			try {
				Files.delete(apiLog.toPath());
			} catch (IOException e) {
				LOG.warning("Error Deleting existing API Log File !!");
			}
			
		}
	    ConnectorConfig config = new ConnectorConfig();
	    config.setUsername(props.getUsername());
	    config.setPassword(props.getPassword());
	    config.setTraceMessage(true);
	    config.setPrettyPrintXml(true);
	    config.setAuthEndpoint(endPoint);
	    
	    try {
			config.setTraceFile(apiLog.getAbsolutePath());
		} catch (FileNotFoundException e) {
			
		}


	    try {     
	    	LOG.info("API Performing Connection ! to " +org);
	    	 connection = Connector.newConnection(config);
	    	 
	    	 LOG.info("API Connnected Successfully ! - Refer to Log for more information - " + apiLog.getAbsolutePath());
	      } catch (ConnectionException e1) {
	    	  LOG.warning("API Connection UnSuccessfull ! - Check properties file \nExiting Application\n" +e1 );
	    	  
	    	  System.exit(1);
	      }  
	}
	public static synchronized PartnerConnection getInstance(){
		if(instance== null )
			instance = new PartnerSession() ; 
		return PartnerSession.connection ; 	
	}
	
	
	/**
	 * Pass the query 
	 * Return the first record  
	 * Needs to be cast to expected object before retreiving fields from the record
	 * 
	 * ie 
	 * 
	 * (Account) theAccount
	 * 
	 * (User) theUser
	 * 
	 * Will always append on LIMIT 1 
	 * @param theQuery
	 * @param theObject
	 * @return
	 */
	public static synchronized SObject[] query(String theQuery){
	//	SObject record = null  ; 
		QueryResult result = null;
		try {
			 result=   getInstance().query(theQuery  );
			
			LOG.info("Query Succesfull !");
			//record = result.getRecords()[0] ;
			
		} catch (ConnectionException e) {
			LOG.warning("Query UnSuccesfull !");
		}
		
		return result.getRecords();
	}
	
	public static synchronized SaveResult[] update(SObject[] theUpdate  ){
		//Add the SObject to an array 
		//update the array
		SaveResult[] result = null ;
		try {
			result = getInstance().update(theUpdate) ;
			
			 int index = 0 ; 
			 for(SaveResult item : result){
				 if(item.getErrors().length != 0 )
				 {
					 LOG.warning("Update was unsuccessful on !" + item.getErrors()[0] ) ; 
					 throw new Exception("Exception was thrown") ;
				 }
				 else{
					 LOG.info("Updated : " + item.getId() +" Object : " + theUpdate[index].getType());
				 }
				 index++ ;  
			 }
		} catch (ConnectionException e) {
			LOG.warning("Update UnSuccesfull !");
		}	
		catch (Exception e) {
			LOG.warning("Update UnSuccesfull !");
		}	
		return result ; 
	}
	/**
	 * Pass single id of record you wish to delete
	 * 
	 * @param ids
	 * @return 
	 */
	public static synchronized DeleteResult[] delete( String[] theArray){
		
		DeleteResult[] result = null ;
		try {
			 result = getInstance().delete(theArray) ;
			 return result ;
		} catch (ConnectionException e) {
			LOG.warning("Delete UnSuccesfull !" + e.toString() );
		}
		
		return result;	
	}
	public static synchronized UpsertResult[] upsert(String externalIdField, SObject[] sObjects){
		UpsertResult[] result = null ; 
		try {
			 result = getInstance().upsert(externalIdField, sObjects);
			 int index = 0 ; 
			 for(UpsertResult item : result){
				 if(item.getErrors().length != 0 )
				 {
					 LOG.warning("Upsert was unsuccessful on !" + item.getErrors()[0] ) ; 
					 throw new Exception("Exception was thorwn") ;
				 }
				 else{
					 LOG.info("Upserted : " + item.getId() +" Object : " + sObjects[index].getType());

				 }
				 index++ ;  
			 }
		} catch (ConnectionException e) {
			LOG.warning("upsert UnSuccesfull !" + e.toString() );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result ; 
	}
	public synchronized static SaveResult[] insert(SObject[] sObjects){
	 SaveResult[] result = null ; 

		try {
			 result = getInstance().create(sObjects) ;
			 int index = 0 ; 
			 for(SaveResult item : result){
				 if(item.getErrors().length != 0 )
				 {
					 LOG.warning("Insert was unsuccessful ! : " + item.getErrors()[0]) ; 
					 throw new Exception("Exception was thorwn") ;
				 }else{
					 LOG.info("Inserted : " + item.getId() +" Object : " + sObjects[index].getType());

				 }				 
				 index++ ; 
			 }
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result ;
	}
	public SendEmailResult[] sendEmail(Email[] emails) {			
			SendEmailResult[] result = null ;
			try {
				result = 	getInstance().sendEmail(emails) ;
			//etClass(). return result ;
			} catch (ConnectionException e) {
				LOG.warning("Email UnSuccesfull !" + e.toString() );
			}
			return result;	
		}
	
	
	 
	public static PropertiesFile getProps() {
		return props ;
	}
	public static void disconnect() {
		try {
			getInstance().logout();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		LOG.info("Ending Session !");
	}
}

