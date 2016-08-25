package cwb1a;
	
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * This class represents an achievement. An achievement could be obtained by a user in a set period (defined by start and stop date)
 * for passing or not passing a set limit corresponding with the set option, related to a set type of event.
 * 
 * @author Aurora
 * @version Griffin
 *
 */public class Achievement implements Data
	{
		// The kind used by the database
		private static final String KIND = "Achievement";
		
		// The name of the achievement.
		private String achievementName;
		// The number of the achievement
		private long achievementNumber;
		// The description of the achievement
		private String achievementDescription;
		// The id of the achievement
		private String achievementId;
		
		// The amount to reach. (duration in thousands of a second)
		private long amountToReach;
		// The option corresponding to the purpose of the achievement.
		private long option;
		// The date of the begin of the period
		private Date startDate;
		// The date of the end of the period
		private Date stopDate;
		// The type of the event related to the achievement
		private long eventType;
		
		// The list of all the userId's
		private ArrayListMap<String> users;
		
		/**
		 * Creates an achievement object.
		 * 
		 * @param achievementName
		 *        The name of the achievement
		 * @param achievementNumber
		 *        The number of the achievement
		 * @param achievementDescription
		 *        The description of the achievement
		 * @param amountToReach
		 *        The amount to reach
		 * @param option
		 *        The option corresponding to the purpose of the achievement
		 * @param startDate
		 *        The date of the begin of the period
		 * @param stopDate
		 *        The date of the end of the period
		 * @param eventType
		 *        The type of the event
		 */
	    public Achievement(String achievementName, long achievementNumber, String achievementDescription, 
	    		long amountToReach, long option, Date startDate, Date stopDate, long eventType) {
	    	this.achievementName = achievementName;
	    	this.achievementNumber = achievementNumber;
	    	this.achievementDescription = achievementDescription;
	    	
	    	this.amountToReach = amountToReach;
	    	this.option = option;
	    	this.startDate = startDate;
	    	this.stopDate = stopDate;
	    	this.eventType = eventType;
	    	
	    	achievementId = AchievementManager.getInstance().cleanString(achievementName);
	    	
	    	users = new ArrayListMap<String>();
	    }
	    
	    /**
	     * Creates a new achievement using the values of the map. 
	     * 
	     * @note	Be careful that the keys correspond with the field names of the achievement. Their is no control.
	     * @param 	values 
	     * 			The map with the values, having keys: "achievementName" with String, "achievementNumber" with long, "achievementDescription" with String, "achievementId" with String, "amountToReach" with long, "option" with long, "startDate" with Date, "stopDate" with Date, "eventType" with long.
	     */
	    public Achievement(Map<String,Object> properties) {
	    	achievementName = (String) properties.get("achievementName");
	    	achievementNumber = (Long) properties.get("achievementNumber");
	    	achievementDescription = (String) properties.get("achievementDescription");
	    	achievementId = (String) properties.get("achievementId");
	    	
	    	amountToReach = (Long) properties.get("amountToReach");
	    	option = (Long) properties.get("option");
	    	startDate = (Date) properties.get("startDate");
	    	stopDate = (Date) properties.get("stopDate");
	    	eventType = (Long) properties.get("eventType"); 
	    	
	    	users = new ArrayListMap<String>(properties);
	    }
	    
	    /**
	     * This method returns the name of the achievement.
	     * 
	     * @return 	The name of the achievement.
	     */

	    public String getAchievementName() {
	    	return achievementName;
	    }
	    
	    /**
	     * Returns the number of the achievement.
	     * 
	     * @return  The number of the achievement.
	     */
	    public long getAchievementNumber() {
	    	return achievementNumber;
	    }
	    
	    /**
	     * Returns the description of the achievement.
	     * 
	     * @return  The description of the achievement.
	     */
	    public String getAchievementDescription() {
	    	return achievementDescription;
	    }
	    
	    /**
	     * Changes the name of the achievement to the given name.
	     * 
	     * @param newName 
	     *        The new name of the achievement.
	     */
	    public void getAchievementName(String newName) {
	    	achievementName = newName;
	    }
	    
	    /**
	     * Changes the description of the achievement to the given discription.
	     * 
	     * @param 	newDescription 
	     * 			The new description of the achievement.
	     */
	    public void setAchievementDescription(String newDescription) {
	    	achievementDescription = newDescription;
	    }
	    
	    /**
	     * Returns the id of the achievement.
	     * 
	     * @return 	The id of the achievement
	     */
	    public String getId() {
	    	return achievementId;
	    }
	    
	    /**
	     * Returns the kind of the achievement, used by the google database.
	     * 
	     * @return 	The kind of the achievement.
	     */
	    public String getKind() {
	    	return KIND;
	    }
	    
	    /**
	     * Static method that returns the kind of the achievement, used by the google database.
	     * 
	     * @return 	The kind of the achievement.
	     */
	    public static String staticGetKind() {
	    	return KIND;
	    }
	    
	    /**
	     * Returns the fields of the achievement in a HashMap.
	     * @note	this is necessary for the DataEngine 
	     * @return 	A HashMap filled with the fields of the achievement and the name of the fields as key.
	     */
	    public HashMap<String,Object> getProperties() {
	    	HashMap<String, Object> properties = new HashMap<String,Object>(users.getProperties());
	    	properties.put("achievementName", achievementName);
	    	properties.put("achievementNumber", achievementNumber);
	    	properties.put("achievementDescription", achievementDescription);
	    	properties.put("achievementId", achievementId);
	    	
	    	properties.put("amountToReach", amountToReach);
	    	properties.put("option", option);
	    	properties.put("startDate", startDate);
	    	properties.put("stopDate", stopDate);
	    	properties.put("eventType", eventType);
	    	
	    	return properties;
	    }
	    
	    /**
	     * Returns the type of the event.
	     * 
	     * @return 	The type of the event.
	     */
	    public long getEventType() {
	    	return eventType;
	    }
	    
	    /**
	     * Verifies the achievement with the given achievement.
	     * 
	     * @param 	achievement
	     *        	The achievement to compare with.
	     * @return 	True if they are the same when the id's of the achievement are the same.
	     */
	    public boolean equals(Achievement achievement) {
	    	return achievementId.equals(achievement.getId());
	    }
		
	    /**
	     * The amount to reach.
	     * 
	     * @return 	The amount to reach.
	     */
		public long getAmountToReach() {
			return amountToReach;
		}
		
		/**
		 * Returns the purpose of the achievement.
		 * 
		 *@note 	0 corresponds with never passing amountToReach
		 * 			1 corresponds with passing amountToReach
		 *@return 	The purpose of the activity.
		 */
		public long getOption() {
			return option;
		}

		/**
		 * Returns the start date of the achievement.
		 * 
		 * @return 	The start date of the achievement.
		 */
		public Date getStartDate() {
			return startDate;
		}
		
		/**
		 * Returns the end date of the achievement.
		 * 
		 * @return 	The end date of the achievement.
		 */
		public Date getStopDate() {
			return stopDate;
		}
		
		/**
		 * Adds a given user to the list of achievers.
		 * 
		 * @param 	user
		 *        	The user that has to be added to the list of achievers.
		 */
		public void addUser(User user) {
			users.add(user.getId());
		}
		
		/**
		 * Returns the list of all the users (id's) who achieved the achievement.
		 * 
		 * @return 	The list of all the users (id's) who achieved the achievement.
		 */
		public ArrayListMap<String> getUserList() {
			return users;
		}
}