package cwb1a.testCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cwb1a.User;
import cwb1a.UserRegistry;

/**
 * Tests if the userEngine works properly. The tests uses some methods from the google apps engine for simulation the google database.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class UserRegistryTest {
	
	private final LocalServiceTestHelper helper =       
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
	}

	public void tearDown() {
		helper.tearDown();
	}
	
	/**
	 * controls if a user is successfully added to and returned from the database.
	 */
	@Test
	public void addUser() {
		User userTest = new User("cbw1", "1",1,"password");
		UserRegistry userEngineTest = UserRegistry.getInstance();
		userEngineTest.add(userTest);
		try {
			User userDatabase = userEngineTest.get(userTest.getUserName());
			assertEquals(true, userDatabase.equals(userTest));
		}
		catch (EntityNotFoundException e) {
			fail("user not collected from the database");
		}
	}
	
	/**
	 * Tests if the values of the user successfully are updated in the database.
	 */
	@Test
	public void updateUser() {
		User userTest = new User("cbw2", "1",1,"password");
		UserRegistry userEngineTest = UserRegistry.getInstance();
		userEngineTest.add(userTest);
		userTest.setUserPassword("changedPassword");
		
		try {
			userEngineTest.update(userTest);
			User userDatabase = userEngineTest.get(userTest.getUserName());
			assertEquals(true, userTest.equals(userDatabase));
		}
		catch (EntityNotFoundException e) {
			fail("user not collected from the database");
		}
	}
	
	/**
	 * Tests if the method verifyRegistration returns true when the username is unique.
	 */
	@Test
	public void verifyRegistration () {
		User userTest = new User("cbw3", "1",1,"password");
		UserRegistry userEngineTest = UserRegistry.getInstance();
		userEngineTest.add(userTest);
		assertEquals(true, userEngineTest.verifyRegistration("nieuweNaam"));
		assertEquals(false, userEngineTest.verifyRegistration("cbw3"));
	}
	
	/**
	 * Tests if the password is accepted when it is correct, and the method the corresponding user returns.
	 */
	@Test
	public void verifyPassword () {
		User userTest = new User("cbw4", "1",1,"password");
		UserRegistry userEngineTest = UserRegistry.getInstance();
		userEngineTest.add(userTest);
		try {
			assertEquals(true, userEngineTest.verifyPassword("cbw4", "password"));
			assertEquals(false, userEngineTest.verifyPassword("cbw4", "falsepassword"));
		}
		catch (EntityNotFoundException e) {
			fail("user not collected from the database");
		}
	}
}
