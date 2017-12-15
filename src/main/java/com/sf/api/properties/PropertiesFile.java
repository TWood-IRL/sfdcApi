package com.sf.api.properties ; 
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
/**
 * Reads from properties file dependant on environment passed 
 * @author ahv15
 * 
 * Need to refactor logging to use log4j
 * 
 * 
 * 
 * Might need to change to be static to only have one instance of properties file at any given time 
 * 
 *
 */
public  class PropertiesFile {
	
	private static String username = "" ; 
	private static String password = "" ;
	public static String org  = "default" ; // = "dev" ; //default to dev  
	InputStream input = null;
	private String type = "" ; 
	static Properties prop = new Properties();
	private static final Logger LOG = Logger.getLogger(PropertiesFile.class.getName()) ; 

	public PropertiesFile(String properties){
		
		LoadProperties(properties);
		
	}
	public PropertiesFile() {
		org = (System.getProperty("environment") != null ) ? System.getProperty("environment") : org ; 
		if(org == null ){
			System.exit(-1); ; 
		}
		LoadProperties();
	}
	private void LoadProperties() {
		//Properties prop = new Properties();
		try {	
			input = PropertiesFile.class.getClassLoader().getResourceAsStream(String.format("./%s.properties" , org ) )  ;

			// load a properties file
			prop.load(input) ;

			// get the property value and print it out
			username = prop.getProperty("USERNAME");
			password = prop.getProperty("PASSWORD");
			type = prop.getProperty("TYPE");
			
		} catch (Exception ex) {
			LOG.warning("Failed to load the properties file, is it created ?"
					+ String.format("\nCheck in com.sf.api/src/resources directory for: %s.properties ",org ) 
					+ "\nExiting Application "
					+ "\nError: " + ex);
			System.exit(-1);
		} 
		
	}
	public String getUsername(){
		return username ;
	}
	public String getPassword(){
		return password; 
	}
	public String getType() {
		return type;
	}
	private static void LoadProperties(String properties) {

		try {

			//input = new FileInputStream(properties + ".properties" );

			// load a properties file
			prop.load(PropertiesFile.class.getClassLoader().getResourceAsStream(properties + ".properties")) ;

			// get the property value and print it out
			username = prop.getProperty("USERNAME");
			password = prop.getProperty("PASSWORD");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		
	}	
	public  Properties getProperties() {
		return prop ; 
	}
	
}
