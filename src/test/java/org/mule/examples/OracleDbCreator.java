/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.examples;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class OracleDbCreator {
	private static final Logger log = Logger.getLogger(OracleDbCreator.class);
	private String databaseUrl;
	private String pathToSqlScript;
	
	public OracleDbCreator(String databaseName, String pathToProperties, String pathToSqlScript){
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream(pathToProperties));
		} catch (Exception e) {
			log.error("Error occured while reading mule.test.properties", e);
		}
		this.pathToSqlScript = pathToSqlScript;
		this.databaseUrl = props.getProperty("database.url");
					
	}
			
	public void setUpDatabase() {
		
		System.out.println("******************************** Populate MySQL DB **************************");
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			
			// Get a connection
			conn = DriverManager.getConnection(databaseUrl);
			Statement stmt = conn.createStatement();
			
//			FileInputStream fstream = new FileInputStream(pathToSqlScript);
//			DataInputStream in = new DataInputStream(fstream);
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			String strLine;
//			StringBuffer createStatement = new StringBuffer();
//			// Specify delimiter according to sql file
//			while ((strLine = br.readLine()) != null) {
//				if (strLine.length() > 0) {
//					strLine.replace("", "");
//					createStatement.append(strLine);
//				}
//			}
//			br.close();
			stmt.executeUpdate("CREATE TABLE CUSTOMERS (	ID NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ,	FIRST_NAME VARCHAR2(20),	LAST_NAME VARCHAR2(20),	PHONE_NUMBER VARCHAR2(20) )");
//			stmt.executeUpdate("REM INSERTING into SYSTEM.CUSTOMERS SET DEFINE OFF;");
//			stmt.executeUpdate("ALTER TABLE SYSTEM.CUSTOMERS MODIFY (ID NOT NULL ENABLE);");
//			
			System.out.println("Success");
			
			
		} catch (SQLException ex) {
		    // handle any errors
		    log.error("SQLException: " + ex.getMessage());
		    log.error("SQLState: " + ex.getSQLState());
		    log.error("VendorError: " + ex.getErrorCode());
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
	
	public void tearDownDataBase() {
		System.out.println("******************************** Delete Tables from MySQL DB **************************");
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(databaseUrl);
		
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE CUSTOMERS");
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
}
