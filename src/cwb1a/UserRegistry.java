package cwb1a;

import com.google.appengine.api.datastore.*;

import java.util.List;
import java.util.ArrayList;

/**
 * The UserEngine uses the design pattern singleton. This class communicates with the database of google. It is very important that you remember that
 * only properties of users will be saved. When you asks for an user, the userEngine searches the properties in the database and creates with
 * those properties a new user object.
 * 
 * @author Aurora
 * @version Griffin
 */
public class UserRegistry {
	
	//The parent key for collecting all users.
	private final static Key allUsers = KeyFactory.createKey(User.staticGetKind(),"allUsers");
	
	//Design pattern singleton.
	private final static UserRegistry instance = new UserRegistry();
	
	/**
	 * There is no possibility of creating an object of this class.
	 */
	private UserRegistry() {
		
	}
	
	/**
	 * This method returns the only possible operational instance of the UserEngine class.
	 * 
	 * @return The only possible operational instance of the UserEngine class.
	 */
	public static UserRegistry getInstance() {
		return instance;
	}
	
	public void add(User user) {
		Entity entity = new Entity(user.getKind(), user.getId(), allUsers);
		DatabaseCommunicator.getInstance().put(entity, user.getProperties());
	}
	
	/**
	 * Gives the user that corresponds with the given userName.
	 * 
	 * @param userId
	 *        The id of the user.
	 * @return The user corresponding with the given id.
	 * @throws EntityNotFoundException
	 *         When the user is not found.
	 */
	public User get(String userId) throws EntityNotFoundException {
		Key userKey = KeyFactory.createKey(allUsers, User.staticGetKind(), userId);
		return new User(DatabaseCommunicator.getInstance().get(userKey).getProperties());
	}
	
	/**
	 * Removes an user from the database.
	 * 
	 * @param userName
	 *        The username of the user that will be removed.
	 */
	public void delete(String userId) {
		Key userKey = KeyFactory.createKey(allUsers, User.staticGetKind(), userId);
		DatabaseCommunicator.getInstance().delete(userKey);
	}

	/**
     * Verifies if the proposal for the username is unique.
     * 
     * @param proposal
     *        The new username that will be checked for uniqueness.
     * @return True if the proposal is unique. False in case of existence.
     */
    public boolean verifyRegistration(String proposal) {
    	try {
    		User found = get(proposal);
    	}
    	catch (EntityNotFoundException e ) {
    		return true;
    	}
    	return false;
       
    }

    /**
     * Verifies of the given login correspond with the given password.
     * 
     * @param login
     *        The username.
     * @param attempt
     *        The attempt for the password.
     * @throws EntityNotFoundException
     *         When the user is not found.
     * @return True if there's a positive match. False if not.
     */
    public boolean verifyPassword (String login, String attempt) throws EntityNotFoundException {        
    	User user = get(login);
    	return user.getUserPassword().equals(attempt);
    }
    
    /**
     * Returns a list of all the users.
     * 
     * @return A list of all the users.
     */
	public List<User> getAll() {
		ArrayList<User> users = new ArrayList<User>();
		for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allUsers, User.staticGetKind())) {
			users.add(new User(lusEntity.getProperties()));
		}
		return users;
	}

	/**
	 * Updates the given user in the database.
	 * 
	 * @param user
	 *        The user that has to be updated in the database.
	 * @throws EntityNotFoundException
	 *         The entity (given user) isn't found in the database.
	 */
	public void update(User user) throws EntityNotFoundException {
		Key userKey = KeyFactory.createKey(allUsers, user.getKind(), user.getId());
		DatabaseCommunicator.getInstance().put((DatabaseCommunicator.getInstance().get(userKey)), user.getProperties());
	} 
	
	/**
	 * Cleans the whole user part of the database.
	 * Be aware of the consequences.
	 */
	public void cleaner() {
		for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allUsers, User.staticGetKind())) {
			User temp = new User(lusEntity.getProperties());
			String tempId = temp.getId();
			delete(tempId);
		}
	}
	
	/**
	 * This is a method to get the amount of users stored in the database.
	 * 
	 * @return The amount of users stored in the database.
	 */
	public long getSize() {
		return getAll().size();
	}
    
}