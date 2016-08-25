package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a practice activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Practice extends LearningTime
{
	
	// A personal rate for the usefulness of the college activity.
    private long usefulnessRate;
    // A personal rate for the preparation feeling of the college activity.
    private long preparationFeelingRate;
    
    /**
     * This method makes a practice (activity) object.
     * 
     * @param startDate
     *        The start date of the practice activity.
     * @param userId
     *        The userId of the user who has done the practice activity.
     * @param courseId
     *        The courseId of the course for which the practice activity is intended.
     */
    public Practice(Date startDate, String userId, String courseId) {
        super(startDate, userId, ActivityFactory.PRACTICE, courseId);
        usefulnessRate = 0;
        preparationFeelingRate = 0;
    }
    
    /**
     * Creates a new Practice object using the values of the Map to set the fields.
     * Be warned their is no control.
     * 
     * @param properties 
     *        The Map containing the keys: the keys for making an activity, "course" witch Course, "usefullnessRate" with long, "preparationFeelingRate" with long.
     */
    public Practice(Map<String, Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.PRACTICE);
    	usefulnessRate = (Long) properties.get("usefulnessRate");
    	preparationFeelingRate = (Long) properties.get("preparationFeelingRate");
    }
    
    /**
     * This method returns the properties of the practice activity.
     * 
     * return HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("usefulnessRate", usefulnessRate);
    	properties.put("preparationFeelingRate", preparationFeelingRate);
    	
    	return properties;
    }
    
    
    /**
     * This method returns the usefulness rate of the practice activity.
     * 
     * @return The usefulness rate of the practice activity.
     */
    public long getUsefulnessRate() {
        return usefulnessRate;
    }

    /**
     * This method changes the usefulness rate of the practice activity to the given value.
     * 
     * @param value 
     *        The new value for the usefulness rate of the practice activity.
     */
    public void setUsefulnessRate(long value) {
        usefulnessRate = value;
    }
    
    /**
     * This method returns the preparation feeling rate of the practice activity.
     * 
     * @return The preparation feeling rate of the activity.
     */
    public long getPreparationFeelingRate() {
        return preparationFeelingRate;
    }
    
    /**
     * This method changes the preparation feeling rate of the practice activity to the given value.
     * 
     * @param value
     *        The new value for the preparation feeling rate of the practice activity.
     */
    public void setPreparationFeelingRate(long value) {
        preparationFeelingRate = value;
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
    	long[][] temp1 = dataMap.get("Practice_usefulnessRate");
    	temp1[numerator][0]=numerator;
	    temp1[numerator][1]=getDuration();
	    temp1[numerator][2]=getUsefulnessRate();
	    dataMap.put("Practice_usefulnessRate", temp1);
	    
	    long[][] temp2 = dataMap.get("Practice_preparationFeelingRate");
    	temp2[numerator][0]=numerator;
	    temp2[numerator][1]=getDuration();
	    temp2[numerator][2]=getPreparationFeelingRate();
	    dataMap.put("Practice_preparationFeelingRate", temp2);
	    
	    return dataMap;
    }
}