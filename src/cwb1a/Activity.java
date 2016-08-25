package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * This class represents a general activity of an active user. This activity is considered as a fully fledged
 * activity when the start and stop date of the activity are not the same.
 * The class has a manual constructor and a constructor compatible with database communication.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public abstract class Activity implements Data, AchievementGuardian
{

	// The kind used by the database
	private static final String KIND = "Activity";
    
	// The initial date of the activity
	private Date startDate;
    // The end date of the activity
    private Date stopDate;
    // The initial date of the pause.
    private Date pauseStartDate;
    // The duration of the total pause during the activity
    private long pauseDuration;
    // The type of the activity.
    // Necessary for reconstructing the activity from the database by the ActivityRegistry.
    private long activityType;
    // The ID of the user who executes the activity
    private String userId;
    // The ID of the activity
    private String activityId;
        
    /**
     * Makes a new activity
     * 
     * @param 	startDate 
     * 			The start date of the activity.
     * @param 	userId 
     * 			The userId of the user who creates the activity.
     * @param 	activityType 
     * 			The type of the activity.
     */
    public Activity(Date startDate, String userId, long activityType)  {
        this.startDate = startDate;
        // Preset (update when activity is done, is necessary)
        this.stopDate = startDate;
        this.pauseStartDate = null;
        this.userId = userId;
        this.activityType = activityType;
        
        pauseDuration = 0;
        
        // Unique id
        activityId = userId + startDate.getTime();
    }
    
    /**
     * Makes a new activity using the values of the map. 
     * Be careful that the keys correspond with the field names of the activity. There is no control.
     * 
     * @param 	properties 
     * 			The map with the properties, having keys: "startDate" with Date, "stopDate" with Date, "userId" with String, "activityType" with long, "activityId" with String, "pauseDuration" with long.
     */
    public Activity(Map<String,Object> properties) {
    	startDate = (Date) properties.get("startDate");
    	stopDate = (Date) properties.get("stopDate");
    	userId = (String) properties.get("userId");
    	activityType = (Long) properties.get("activityType");
    	pauseDuration = (Long) properties.get("pauseDuration");
    	pauseStartDate = (Date) properties.get("pauseStartDate");
    	
    	activityId = userId + startDate.getTime();
    }
    
    /**
     * Returns the kind of the activity, used by the google database.
     * 
     * @return 	The kind of the activity.
     */
    public static String staticGetKind() {
    	return KIND;
    }
   
    /**
     * Returns the kind of the activity, used by the google database.
     * 
     * @return 	The kind of the activity.
     */
    public String getKind() {
    	return KIND;
    }
    
    /**
     * Returns the fields of the activity in a HashMap, necessary for the ActivityRegistry.
     * 
     * @return 	A HashMap filled with the fields of the activity and the key as the name of the field.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = new HashMap<String,Object>();
    	properties.put("startDate", startDate);
    	properties.put("stopDate", stopDate);
    	properties.put("userId", userId);
    	properties.put("activityType", activityType);
    	properties.put("activityId",activityId);
    	properties.put("pauseDuration", pauseDuration);
    	properties.put("pauseStartDate", pauseStartDate);
    	
    	return properties;
    }
    
    /**
     * Verifies if the activity and the given activity are equal.
     * 
     * @param 	activity 
     * 			The activity to compare with.
     * @return 	True when the activities have the same id. 
     */
    public boolean equals(Activity activity) {
    	return activityId.equals(activity.getId());
    }
     
    /**
     * Returns the start date of the activity.
     * 
     * @return 	The start date of the activity.
     */
    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * Returns the stop date of the activity.
     * 
     * @return 	The stop date of the activity.
     */
    public Date getStopDate() {
        return stopDate;
    }
    
    /**
     * Changes the start date of the activity to the given value.
     * 
     * @param 	date 
     * 			The new value for the start date of the activity.
     */
    public void setStartDate(Date date) {
    	startDate = date;
    }
    
    /**
     * Changes the stop date of the activity to the given value.
     * 
     * @param 	date 
     * 			The new value for the stop date of the activity.
     */
    public void setStopDate(Date date) {
    	stopDate = date;
    }
    
    /**
     * Returns the duration of the activity in thousands of a second.
     * 
     * @return 	The duration of the activity in thousands of a second.
     * 
     */
    public long getDuration() {
    	return stopDate.getTime() - startDate.getTime() - pauseDuration;
    }
    
    /**
     * Sets the user of the activity.
     * 
     * @param 	user 
     * 			The user to be set.
     */
    public void setUserId(String userId) {
    	this.userId = userId;
    }
    
    /**
     * Returns the id of the user who executes the activity.
     * 
     * @return 	The id of the user who executes the activity.
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Returns the type of the activity.
     * 
     * @return 	The type of the activity.
     */
    public long getType() {
    	return activityType;
    }
    
    /**
     * This method returns the total duration of the pause taken during the activity.
     * 
     * @return 	The total duration of the pause taken during the activity.
     */
    public long getPauseDuration() {
    	return pauseDuration;
    }

    /**
     * Changes the type of the activity to the given value.
     * 
     * @param 	activityType 
     * 			The new value for the type of the activity.
     */
    protected void setActivityType(long activityType){
    	this.activityType = activityType;
    }
    
    /**
     * Returns the id of the activity. The id is created with a combination of the userId and the startTime.
     * 
     * @return 	the id of the activity.
     */
    public String getId()  {
    	return activityId;
    }
    
    /**
     * Changes the pause duration of the activity to the given value.
     * 
     * @param 	pauseDuration 
     * 			The new value for pause duration of the activity.
     */
    public void setPauseDuration(long pauseDuration) {
    	this.pauseDuration = pauseDuration;
    }
    
    /**
     * Sets the start date of the pause duration.
     * @param 	date 
     * 			The start date of the pause
     */
    public void setPauseStartDate(Date date) {
    	pauseStartDate = date;
    }
    
    /**
     * returns the date of the pauseStartDate.
     * @return 	The date of the start of the pause
     */
    public Date getPauseStartDate() {
    	return pauseStartDate;
    }
    
    /**
     * Method to add milliseconds to the pause duration for the total pause duration.
     * @param 	pauseDuration 
     * 			the amount in milliseconds to add.
     */
    public void addDuration(long pauseDuration) {
    	this.pauseDuration = this.pauseDuration + pauseDuration;
    	
    }
    
    /**
     * Returns map with a 2D long arrays of the following form [number of the activity (type dependent); duration of the activity; rating or other long value (type dependent)]
     * with the Activity_fieldname as key. 
     * 
     * @note	More than one key for certain activityTypes is possible.
     * @param 	dataMap 
     * 			The map with the data in the form of a 2D long array with the Activity_fieldname as key.
     * @param 	numerator 
     * 			The number of the activity (type dependent)
     * @return 	The update of the given map.
     */
    abstract Map<String, long[][]> visitorBubble(Map<String, long[][]> dataMap, int numerator);
   
}
