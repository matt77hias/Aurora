package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a sleep activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Sleep extends Activity
{
	// A personal rate for the clear mind feeling after the sleep activity.
    private long wakeUpRate;
    
    /**
     * Creates a sleep (activity) object.
     * 
     * @param startDate
     *        The start date of the sleep activity.
     * @param userId
     *        The userId of the user who has done the sleep activity.
     */
    public Sleep(Date startDate, String userId) {
        super(startDate, userId, ActivityFactory.SLEEP);
        wakeUpRate = 0;
    }
    
    /**
     * Creates a Sleep activity using the values of the Map to set the fields.
     * Be warned, the values are not checked.
     * 
     * @param properties
     *        The Map containing the keys: for creating an activity, "wakeUpRate" with long.
     */
    public Sleep(Map<String, Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.SLEEP);
    	wakeUpRate = (Long) properties.get("wakeUpRate");
    }
    
    /**
     * This method returns the properties of the sleep activity.
     * 
     * return HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("wakeUpRate", wakeUpRate);
    	
    	return properties;
    }
    
    /**
     * This method returns the wake up rate of the sleep activity.
     * 
     * @return The wake up rate of the sleep activity.
     */
    public long getWakeUpRate() {
        return wakeUpRate;
    }
    
    /**
     * This method changes the wake up rate of the sleep activity to the given value.
     * 
     * @param value
     *        The new value for the wake up rate of the sleep activity.
     */
    public void setWakeUpRate(long value) {
        wakeUpRate = value;
    }
    
    /**
     * Returns map with a 2D long arrays of the following form [number of the activity (type dependent); duration of the activity; rating or other long value (type dependent)]
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
    	long[][] temp1 = dataMap.get("Sleep_wakeUpRate");    		
    	temp1[numerator][0]=numerator;
    	temp1[numerator][1]=getDuration();
   		temp1[numerator][2]=getWakeUpRate();
	    dataMap.put("Sleep_wakeUpRate", temp1);
	    
	    return dataMap;
    }  
}