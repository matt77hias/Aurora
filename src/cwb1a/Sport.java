package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a sport activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Sport extends Activity
{
	// A personal rate for the intensity of the sport activity
    private long intensityRate;
    
    /**
     * Creates a sport activity object.
     * 
     * @param startDate 
     *        The start date of the sport activity.
     * @param userId
     *        The userId of the user who has done the sport activity.
     */
    public Sport(Date startDate, String userId) {
        super(startDate, userId, ActivityFactory.SPORT);
        intensityRate = 0;
    }
    
    
    /**
     * Creates a new Sport object using the values of the Map to set the fields.
     * Be warned, the values are never checked.
     * 
     * @param properties
     *        A Map containing the keys: for creating an activity, "intensityRate" with long.
     */
    public Sport(Map<String, Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.SPORT);
    	intensityRate = (Long) properties.get("intensityRate");
    }
    
    /**
     * This method returns the properties of the sport activity.
     * 
     * return HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("intensityRate", intensityRate);
    	
    	return properties;
    }

    /**
     * This method returns the intensity rate of the sport activity.
     * 
     * @return The intensity rate of the sport activity.
     */
    public long getIntensityRate() {
        return intensityRate;
    }
    
    /**
     * This method changes the intensity rate of the sport activity to the given value.
     * 
     * @param value
     *        The new value for the intensity rate of the sport activity.
     */
    public void setIntensityRate(long value) {
        intensityRate = value;
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
    	long[][] temp1 = dataMap.get("Sport_intensityRate");
    	temp1[numerator][0]=numerator;
	    temp1[numerator][1]=getDuration();
	    temp1[numerator][2]=getIntensityRate();
	    dataMap.put("Sport_intensityRate", temp1);
	    
	    return dataMap;
    }
}