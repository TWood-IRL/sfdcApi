package com.sf.api.examples;

import java.sql.ResultSet;

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
		SObject account = new SObject("Account") ;
		account.setSObjectField("Name", "Test Account ");
		account.setSObjectField("RecordTypeId", RecordTypes.getRecordType("Account", "Customer"));
		result = PartnerSession.insert(new SObject[] {account}) ; 
		
	}
	
	@Test
	public  void doingTestingStuff(){
		
		System.out.println(result[0].getId());
	}
	
}
