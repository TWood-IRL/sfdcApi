package com.sf.api.examples;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sf.api.session.PartnerSession;
import com.sf.api.session.RecordTypes;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;


public class TestClass {

	private static SaveResult[] result ; 
	@Before
	public  void setUp() {
		for(int i = 0 ; i < 10 ; i ++ ){
			SObject account = new SObject("Account") ;
			account.setSObjectField("Name", "Test Account " + i );
			account.setSObjectField("RecordTypeId", RecordTypes.getId("Account", "Broker"));
			result = ArrayUtils.addAll( PartnerSession.insert(new SObject[] {account}), result)  ; 
		}
		
	}
	
	@Test
	public  void doingTestingStuff(){
		for(int i = 0 ; i < 10 ; i ++ ){
			System.out.println(result[i].getId()); 
		}
		
	}
	@After
	public void tearDown(){ //removing the data after the test has finished this can be implemented differently if needed 
		for(SaveResult record : result) {
			PartnerSession.delete(new String[]{record.getId()}) ; 
		}
	}
}
