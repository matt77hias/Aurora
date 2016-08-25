package cwb1a.testCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cwb1a.Activity;
import cwb1a.ActivityRegistry;
import cwb1a.College;
import cwb1a.Sleep;
import cwb1a.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Tests if the DataEngine works properly. The tests uses some methods from the google apps engine for simulation the google database.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class ActivityRegistryTest {
    
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Before
	
	public void setUp() throws Exception {
		helper.setUp();		
	}
	
	public void tearDown() {
		helper.tearDown();
	}
	
	/**
	 * Tests if a college is correctly added and returned.
	 * Tests if getAll works properly.
	 */
	@Test
	public void addCollege() {
		Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 28);
        cal1.set(Calendar.MONTH, 10-1);
        cal1.set(Calendar.YEAR, 2011);
        cal1.set(Calendar.HOUR_OF_DAY, 16);
        cal1.set(Calendar.MINUTE, 44);
        cal1.set(Calendar.SECOND, 0);
        Date startDate = cal1.getTime();
        
        cal1.set(Calendar.SECOND, 1);
		Date startDate2 = cal1.getTime();
        
        cal1.set(Calendar.HOUR_OF_DAY, 18);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Date stopDate = cal1.getTime();
				
		User userTest = new User("cbw1", "1",1,"password");
		Activity collegeTest = new College(startDate, userTest.getId(), null);
		collegeTest.setStopDate(stopDate);
		
		
		Sleep sleepTest = new Sleep(startDate2, userTest.getId());
		sleepTest.setStopDate(stopDate);
		sleepTest.setWakeUpRate(10);
		
		ActivityRegistry activityRegistryTest = ActivityRegistry.getInstance();
		activityRegistryTest.add(collegeTest);
	
		assertEquals(true, activityRegistryTest.getAllActivitiesUser(collegeTest.getUserId()).size() == 1);
		activityRegistryTest.add(sleepTest);

		assertEquals(2, activityRegistryTest.getAll().size());
		
	}

}
