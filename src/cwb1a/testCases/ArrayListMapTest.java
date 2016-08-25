package cwb1a.testCases;

import static org.junit.Assert.*;

import java.util.Map;


import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cwb1a.ArrayListMap;
import cwb1a.DatabaseCommunicator;
import cwb1a.Division;
import cwb1a.DivisionRegistry;
import cwb1a.Subdivision;
import cwb1a.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test if the arrayListMap class works properly.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class ArrayListMapTest {
	
	private final LocalServiceTestHelper helper =       
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	/**
	 * Tests if the class ArrayListMap 2 lists with diverent tags converts to one map
	 */
	@Test
	public void testChangeListMap() {
		ArrayListMap<String> testL = new ArrayListMap<String>("T");
		String test0 = "test0";
		String test1 ="test1";
		testL.add(test0);
		testL.add(test1);
		
		assertEquals(true,test0.equals(testL.getProperties().get("T0")) );
		
		ArrayListMap<String> testL2 = new ArrayListMap<String>();
		testL2.add(test1);
		
		Map<String,String> testM = testL.getProperties();
		testM.putAll(testL2.getProperties());
		
		assertEquals(true, testM.get("T0").equals(test0));
		assertEquals(true, testM.get("0").equals(test1));
	}
	
	/**
	 * Tests if a subdivision is correctly added to the database.
	 */
	@Test
	public void testSubdivision() {
		Subdivision general = new Subdivision("General", "General");
		Subdivision main1 = new Subdivision("Katholieke Universiteit Leuven","KUL");
		general.add(main1.getId());
		User usertest = new User("testuser","testuser", 1,"password");
		general.addUserToDivision(usertest);
		
		DivisionRegistry.getInstance().add(general);
		DivisionRegistry.getInstance().add(main1);
		
		System.out.println("properties from the subdivision");
		for (String key : general.getProperties().keySet()) {
			System.out.println(general.getProperties().get(key));
		}
		
		try {
			DatabaseCommunicator comm = DatabaseCommunicator.getInstance();
			Key allDivisions = KeyFactory.createKey(Division.staticGetKind(), "allDivisions");

			Key divisionKey = KeyFactory.createKey(allDivisions, Division.staticGetKind(), "General");
			
			Entity entity = comm.get(divisionKey);
			System.out.println("properties from the database");
			for(String key : entity.getProperties().keySet()) {
				System.out.println(entity.getProperty(key));
			}
			
			
			Subdivision test = (Subdivision) DivisionRegistry.getInstance().get("General");
			assertEquals(true, test.getSubdivisionList().size() == 1);
		}
		catch (EntityNotFoundException e) {
			fail("Exception:" + e);
		}
	}

}
