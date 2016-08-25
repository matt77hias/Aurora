package cwb1a;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * This class represents a job activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Job extends Activity
{
	// A personal rate of the fatigue at the end of the job activity.
	private long fatigueRate;
	
    /**
     * This method makes a job (activity) object.
     * 
     * @param startDate The start date of the job activity.
     * @param userId The userId of the user who has done the job activity.
     */
	public Job(Date startDate, String userId) {
        super(startDate, userId, ActivityFactory.JOB);
        fatigueRate = 0;
    }
    
	/**
	 * Creates a new Job using the values of the Map.
	 * Be warned, the values are never been checked.
	 * 
	 * @param properties The Map with keys: the one for creating an activity, "fatigueRate" with long.
	 */
    public Job(Map<String,Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.JOB);
    	fatigueRate = (Long) properties.get("fatigueRate");
    }
    
    /**
     * This method returns the properties of the job activity.
     * 
     * @return HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("fatigueRate", fatigueRate);
    	
    	return properties;
    }
    
    /**
     * This method returns the fatigue rate of the job activity.
     * 
     * @return The fatigue rate of the job activity.
     */
    public long getFatigueRate() {
    	return fatigueRate;
    }
    
    
    /**
     * This method changes the fatigue rate of the job activity to the given value.
     * 
     * @param value The new value of the fatigue rate of the job activity.
     */
    public void setFatigueRate(long value) {
    	fatigueRate = value;
    }
    
    /**
     * Returns a map with 2D long arrays of the following form [number of the activity (type dependent); duration of the activity; rating or other long value (type dependent)]
     * with the Activity_fieldname as key. The return map is an update of the given map.
     * More than one key for certain activityTypes is possible.
     * 
     * @param 	dataMap 
     * 			The map with the data in the form of a 2D long array with the Activity_fieldname as key.
     * @param 	numerator 
     * 			The number of the activity (type dependent)
     * @return 	The update of the given map.
     */
    public Map<String, long[][]> visitorBubble(Map<String, long[][]> dataMap, int numerator) {
    	long[][] temp1 = dataMap.get("Job_fatigueRate");
    	
    		temp1[numerator][0]=numerator;
    		temp1[numerator][1]=getDuration();
    		temp1[numerator][2]=getFatigueRate();
    		dataMap.put("Job_fatigueRate", temp1);
	    
	    return dataMap;
    }
}