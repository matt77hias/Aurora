package cwb1a;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * This class takes control of the register and logging on part of the Aurora project.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class EntranceController {
	
	// Design pattern Singleton
	private static EntranceController instance = new EntranceController();
	
	/**
	 * @note Not allowed to create objects from this class.
	 */
	private EntranceController() {
		
	}
	
	/**
     * This method returns the only possible operational instance of the EntranceController class.
     * 
     * @return The only possible operational instance of the EntranceController class.
     */
	public static EntranceController getInstance() {
		return instance;
	}
	
	/**
	 * adds a new user to the system
	 * @param id 
	 *        The id of the user
	 * @param usernumber
	 *        The number of the user
	 * @param password
	 *        The password of the user
	 * @param courses
	 *        The list with courses of the user
	 * @return True if the id is unique and the user is successfully added. False if not.
	 */
	public boolean register(String id, String usernumber, String password, List<Division> courses) {
		boolean verifyRegistration = UserRegistry.getInstance().verifyRegistration(id);
		if(verifyRegistration) {
			User user = new User(id, usernumber, 1, password);
			UserRegistry userRegistry = UserRegistry.getInstance();
			userRegistry.add(user);
		
			for(Division course : courses) {
				course.addUserToDivision(user);
				try  {
					DivisionRegistry.getInstance().update(course);
				}
				catch (EntityNotFoundException e) {
					;
				}
			}
		}
		
		return verifyRegistration;
		
	}
	
	/**
	 * Controls if a user can successfully login.
	 * 
	 * @param id
	 *        The id of the user
	 * @param password
	 *        The password of the user.
	 * @return True if positive match between user and password. False if not.
	 */
	public boolean login(String id, String password) {
		try {
			return UserRegistry.getInstance().verifyPassword(id,password);
		}
		catch(EntityNotFoundException e) {
			return false;
		}
	}

}
