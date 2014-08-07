package org.mule.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;

public class AmazonTest extends FunctionalTestCase
{
	private static final String MAPPINGS_FOLDER_PATH = "./mappings";
	private static final Object REPLY = "[{firstname=John, lastname=Doe, phone=12003246879}, {firstname=Mary, lastname=Jane, phone=9879842316}]";
	private static OracleDbCreator DB = null;
	
    @Override
    protected String getConfigResources()
    {
        return "amazon.xml";
    }

    @BeforeClass
    public static void init(){
    	System.setProperty("s3.accessKey", "AKIAJHD7D7Q3AHNTVTPA ");
    	System.setProperty("s3.secretKey", "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");
    	System.setProperty("s3.bucket", "mule-miro-demo");
    	System.setProperty("s3.filename", "customers.csv");
    	System.setProperty("oracle.host", "localhost");
    	System.setProperty("oracle.user", "system");
    	System.setProperty("oracle.password", "root");
    	System.setProperty("oracle.instance", "orcl");
    	
    	DB = new OracleDbCreator(null, "./src/test/resources/mule.test.properties", "./src/main/resources/export.sql");    	
    	DB.setUpDatabase();
    }
    
    @Test
    public void testDataMapper() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "GET");
        MuleMessage result = client.send("http://localhost:8081/", StringUtils.EMPTY, props);
        assertNotNull(result);
        assertFalse(result.getPayload() instanceof NullPayload);
        assertEquals(REPLY, result.getPayloadAsString());
    }
        
	@Override
	protected Properties getStartUpProperties() {
		Properties properties = new Properties(super.getStartUpProperties());

		String pathToResource = MAPPINGS_FOLDER_PATH;
		File graphFile = new File(pathToResource);

		properties.put(MuleProperties.APP_HOME_DIRECTORY_PROPERTY,
				graphFile.getAbsolutePath());

		return properties;
	}

	@AfterClass
	public static void tearDown(){
		DB.tearDownDataBase();
	}
}
