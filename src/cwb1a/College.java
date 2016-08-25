package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * This class represents a college activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class College extends LearningTime
{
    // A personal rate for the usefulness of the college activity.
    private long usefulnessRate;
    // A value for the sitting position in the auditory where the college activity took place.
    private long position;

  
    /**
     * Creates a college (activity) object. The usefulnessRate and position are instantiated as 0, because this 
     * will be filled in after the creation of a College. 
     * 
     * @param 	startDate 
     * 			The start date of the college activity.
     * @param 	user 
     * 			The user who has done the college activity.
     * @param 	course 
     * 			The course for which the college activity is intended.
     *
     */
    public College(Date startDate, String userId, String courseId) {
        super(startDate, userId, ActivityFactory.COLLEGE, courseId);
        usefulnessRate = 0;
        position = 0;
    }
    

    /**
     * Creates a college object using a Map for setting the properties. Used by the ActivityRegistry.
     * Be warned, their is no control if the keys do or don't match the name of the fields.
     * 
     * @param 	properties 
     * 			A Map containing the following keys: "courseId" with String, "usefulnessRate" with long, "position" with long.
     */
    public College(Map<String, Object> properties) {
    	super(properties);
    	setActivityType(ActivityFactory.COLLEGE);
    	usefulnessRate = (Long) properties.get("usefulnessRate");
    	position = (Long) properties.get("position");
    }

    /**
     * Returns the properties of the college activity.
     * 
     * @return 	HashMap with the properties as content and the name of the properties as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.put("usefulnessRate", usefulnessRate);
    	properties.put("position", position);
    	
    	return properties;
    }
    
    
    /**
     * Returns the usefulness rate of the college activity.
     * 
     * @return 	The usefulness rate of the college activity.
     */
    public long getUsefulnessRate() {
        return usefulnessRate;
    }

    /**
     * Changes the usefulness rate of the college activity to the given value.
     * 
     * @param 	value 
     * 			The new value for the usefulness rate of the college activity.
     */
    public void setUsefulnessRate(long value) {
        usefulnessRate = value;
    }

    /**
     * Returns the sitting position in the auditorium of the college activity.
     * 
     * @return 	The sitting position in the auditorium of the college activity.
     */
    public long getPosition() {
        return position;
    }
    
    /**
     * Changes the sitting position in the auditorium of the college activity to the given value.
     * 
     * @param 	value 
     * 			The new value for the sitting position in the auditorium of the college activity.
     */
    public void setPostion(long value) {
        position = value;
    }
    
    /**
     * Returns a map with 2D long arrays of the following form [number of the activity (type dependent); duration of the activity; rating or other long value (type dependent)]
     * with the Activity_fieldname as key. The return map is an update of the given map.
     * More than one key for certain activityTypes is possible.
     * This is method is primarily used by the GraphEngine. 
     * 
     * @param 	dataMap 
     * 			The map with the data in the form of a 2D long array with the Activity_fieldname as key.
     * @param 	numerator 
     * 			The number of the activity (type dependent)
     * @return 	The update of the given map.
     */
    public Map<String, long[][]> visitorBubble(Map<String, long[][]> dataMap, int numerator) {
    	long[][] temp1 = dataMap.get("College_usefulnessRate");
    	temp1[numerator][0]=numerator;
	    temp1[numerator][1]=getDuration();
	    temp1[numerator][2]=getUsefulnessRate();
	    dataMap.put("College_usefulnessRate", temp1);
	    
	    long[][] temp2 = dataMap.get("College_position");
    	temp2[numerator][0]=numerator;
	    temp2[numerator][1]=getDuration();
	    temp2[numerator][2]=getPosition();
	    dataMap.put("College_position", temp2);
	    
	    return dataMap;
    }
}