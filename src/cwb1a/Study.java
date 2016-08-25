package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a study activity. A study activity is a school activity beside
 * the formal colleges and practices.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Study extends LearningTime

{
	
    // A personal rate for the quality the activity contributes to the learning process of the course.
    private long qualityRate;
    // A personal rate for the amount (quantity) of work that's done for the learning process of the course.
    private long quantityRate;
    
    /**
     * Creates a study (activity) object.
     * 
     * @param startDate
     *        The start date of the study activity.
     * @param userId
     *        The userId of the user who has done the study activity.
     * @param course
     *        The courseId of the course for which the study activity is intended.
     */
    public Study(Date startDate, String userId, String courseId) {
        super(startDate, userId, ActivityFactory.STUDY, courseId);
        
        qualityRate = 0;
        quantityRate = 0;
    }
    
    /**
     * Creates a new Study object using the values of the Map to set the fields.
     * Be warned, the values are never checked.
     * 
     * @param properties
     *        A Map containing the keys: for creating an activity, "course" with Course, "qualityRate" with long, "quantityRate" with long.
     */
    
    public Study(Map<String, Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.STUDY);
    	qualityRate = (Long) properties.get("qualityRate");
    	quantityRate = (Long) properties.get("quantityRate");
    }
    
    
    /**
     * This method returns the properties of the study activity.
     * 
     * return HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("qualityRate", qualityRate);
    	properties.put("quantityRate", quantityRate);
    	
    	return properties;
    }

    
    /**
     * This method returns the quality rate of the study activity.
     * 
     * @return The quality rate of the study activity.
     */
    public long getQualityRate() {
        return qualityRate;
    }
    
    /**
     * This method changes the quality rate of the study activity to the given value.
     * 
     * @param value The new value of the quality rate of the study activity.
     */
    public void setQualityRate(long value) {
        qualityRate = value;
    }
    
    /**
     * This method returns the quantity rate of the study activity.
     * 
     * @return The quantity rate of the study activity.
     */
    public long getQuantityRate() {
        return quantityRate;
    }
    
    /**
     * This method changes the quantity rate of the study activity to the given value.
     * 
     * @param value
     *        The new value of the quantity rate of the study activity.
     */
    public void setQuantityRate(long value) {
        quantityRate = value;
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
    	long[][] temp1 = dataMap.get("Study_quantityRate");
    	temp1[numerator][0]=numerator;
	    temp1[numerator][1]=getDuration();
	    temp1[numerator][2]=getQuantityRate();
	    dataMap.put("Study_quantityRate", temp1);
	    
	    long[][] temp2 = dataMap.get("Study_qualityRate");
    	temp2[numerator][0]=numerator;
	    temp2[numerator][1]=getDuration();
	    temp2[numerator][2]=getQualityRate();
	    dataMap.put("Study_qualityRate", temp2);
	    
	    return dataMap;
    }
}