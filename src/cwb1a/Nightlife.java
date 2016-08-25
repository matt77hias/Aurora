package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a nightlife activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Nightlife extends Activity
{
	// A personal rate for the amount of alcohol consumed during the nightlife activity.
    private long alcoholRate;
    
    /**
     * This method makes a nightlife (activity) object.
     * 
     * @param startDate
     *        The start date of the nightlife activity.
     * @param userId
     *        The userId who has done the nightlife activity.
     */
    public Nightlife(Date startDate, String userId) {
        super(startDate, userId, ActivityFactory.NIGHTLIFE);
        alcoholRate = 0;
    }
    
    /**
     * Creates a new nightlife object using the values of the Map for setting the fields.
     * Be warned, the values are never checked. 
     * 
     * @param properties 
     *        A Map containing: activity properties, "alcoholRate" with long.
     */
    public Nightlife(Map<String,Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.NIGHTLIFE);
    	alcoholRate = (Long) properties.get("alcoholRate");
    }
    
    /**
     * This method returns the properties of the nightlife activity.
     * 
     * return HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("alcoholRate", alcoholRate);
    	
    	return properties;
    }
    
    /**
     * This method returns the alcohol rate corresponding the nightlife activity.
     * 
     * @return The alcohol rate corresponding the nightlife activity.
     */
    public long getAlcoholRate() {
        return alcoholRate;
    }
    
    /**
     * This method changes the alcohol rate of the nightlife activity to the given value.
     * 
     * @param value
     *        The new value for the alcohol rate of the nightlife activity.
     */
    public void setAlcoholRate(long value) {
        alcoholRate = value;
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
    	long[][] temp1 = dataMap.get("Nightlife_alcoholRate");
    	temp1[numerator][0]=numerator;
	    temp1[numerator][1]=getDuration();
	    temp1[numerator][2]=getAlcoholRate();
	    dataMap.put("Nightlife_alcoholRate", temp1);
	    
	    return dataMap;
    }
}
