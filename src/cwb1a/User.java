package cwb1a;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a user. This class has two constructors, a manual one that is called when a user is created
 * and another one to communicate with the database.  
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class User implements Data
{
	// The kind of the user
	private final static String KIND = "User";
	// The username of the user
    private String userName;
    // The student/teacher number of the user
    private String userNumber;
    // The type of the user
    // userType = 1 for active users; userType = 2 for passive users; userType = 3 for moderators
    private long userType;
    // The password of the user
    // Requires at least 8 and no more than 14 characters (userEngine responsibility)
    private String userPassword;
    // The id of the user
    private String userId;
    //The current Activity the user is executing. If the user isn't doing an Activity, this is null.
    private String currentActivityId;
    // The list of collected achievements.
    private ArrayListMap<String> achievementIds;
    
    /**
     * Creates makes an user object.
     * 
     * @param userName
     *        The username of the user (has to be unique in order to not overwrite older entries)
     * @param userNumber
     *        The student/teacher number of the user
     * @param userType
     *        The type of the user.
     * @param userPassword
     *        The password of the user.
     */
    public User(String userName, String userNumber, long userType, String userPassword)
    {
    	this.userName = userName;
    	this.userNumber = userNumber;
    	this.userType = userType;
    	this.userPassword = userPassword;
    	
    	userId = userName;
    	achievementIds = new ArrayListMap<String>();
    	
    }
    
    /**
     * Creates a new User using the properties from the Map. Be warned, the properties are never checked.
     * 
     * @param properties
     *        A Map containing the properties with the names of the fields as key.
     */
    public User(Map<String, Object> properties) {
    	this.userName = (String) properties.get("userName");
    	this.userNumber = (String) properties.get("userNumber");
    	this.userType = (Long) properties.get("userType");
    	this.userPassword = (String) properties.get("userPassword");
    	this.currentActivityId = (String) properties.get("currentActivityId");
    	
    	achievementIds = new ArrayListMap<String>(properties);
    	
    	userId = userName;
	}
    
    /**
     * Returns the id of the user.
     * 
     * @return The id of the user.
     */
    public String getId() {
    	return userId;
    }
    
    /**
     * Returns the kind of the user used by the database.
     * 
     * @return The kind of the user.
     */
    public static String staticGetKind() {
    	return KIND;
    }
    
    /**
     * Returns the kind of the user used by the database.
     * 
     * @return The kind of the user.
     */
    public String getKind() {
    	return KIND;
    }
    
	/**
     * This method returns the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Returns the properties of the user in a HasMap with keys the property names.
     * 
     * @return a Map with the properties of the user.
     */
    @Override
    public HashMap<String,Object> getProperties() {
    	HashMap<String,Object> properties = new HashMap<String,Object>(achievementIds.getProperties());
    	properties.put("userName", userName);
    	properties.put("userNumber",userNumber);
    	properties.put("userType",userType);
    	properties.put("userPassword", userPassword);
    	properties.put("currentActivityId", currentActivityId);
    	
    	return properties;
    	
    }
    
    /**
     * This method returns the student/teacher number of the user.
     * 
     * @return The student/teacher number of the user.
     */
    public String getUserNumber() {
        return userNumber;
    }
    
    /**
     * This method returns the type of the user.
     * userType = 1 for active users
     * userType = 2 for passive users
     * userType = 3 for moderators
     * 
     * @return The type of the user.
     */
    public long getType() {
        return userType;
    }
   
    /**
     * This method returns the password of the user.
     * 
     * @return The password of the user.
     */
    public String getUserPassword() {
        return userPassword;
    }
    
    
    /**
     * This method changes the password of the user to the given password.
     * 
     * @param newUserPassword
     *        The new password of the user.
     */
    public void setUserPassword(String newUserPassword) {
        userPassword = newUserPassword;
    }
    
    
    /**
     * This method compares two users. They are equal when their usernames (= id's) are the same.
     * 
     * @param user
     *        The user to compare with.
     * @return True when they are equal; false if not.
     */
    public boolean equals(User user) {
    	return userId.equals(user.getId());
    }
    
    /**
     * Returns a collection of the id's of the obtained achievements of this user.
     * 
     * @return The collection of the id's of the obtained achievements of this user.
     */
    public List<String> getAchievementsIds() {
    	return achievementIds;
    }
    
    /**
     * Changes currentActivityId to a given value.
     * 
     * @param activityId
     *        The new value for the activityId.
     */
    public void setCurrentActivityId(String activityId) {
    	currentActivityId = activityId;
    }
    
    /**
     * Returns the id of the currentActivity.
     * 
     * @return The id of the currentActivity
     */
    public String getCurrentActivityId() {
    	return currentActivityId;
    }
    
    /**
     * Adds an achievementId to the achievement collection of the user.
     * 
     * @param achievementId The id that has to be added
     */
    public void addAchievementId(String achievementId) {
    	achievementIds.add(achievementId);
    }
}