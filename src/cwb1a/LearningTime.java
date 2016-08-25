package cwb1a;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the learning time of an activity. 
 * 
 * @author Aurora
 * @version Griffin
 *
 */

public abstract class LearningTime extends Activity {
		// The id of the course for which the learning activity is intended to be.
		private String courseId;
		
		/**
		 * Creates a new learning time (activity).
		 * 
		 * @param startDate 
		 *        The start date of the activity
		 * @param userId 
		 *        The userId of the user
		 * @param activityType 
		 *        The type of the activity
		 * @param courseId 
		 *        The id of the course that is selected
		 */
		public LearningTime(Date startDate, String userId, Long activityType ,String courseId) {
			super(startDate, userId, activityType);
			this.courseId = courseId;
		}
		
		 
	    /**
	     * Makes a new learning time (activity) using the values of the map. 
	     * Be careful that the keys correspond with the field names of the activity. There is no control.
	     * 
	     * @param 	values 
	     * 			The map with the values, having keys: "courseId" with String.
	     */
		public LearningTime(Map<String, Object> properties) {
			super(properties);
			courseId = (String) properties.get("courseId");
		}
		
		/**
	     * Returns the fields of the learning time (activity) in a HashMap, necessary for the ActivityRegistry.
	     * 
	     * @return 	A HashMap filled with the fields of the learning time (activity) and the key as the name of the field.
	     */
		public HashMap<String,Object> getProperties() {
			HashMap<String,Object> properties = super.getProperties();
			properties.put("courseId",courseId);
			return properties;
		}
		
		/**
	     * Returns the courseId.
	     * 
	     * @return The courseId of the course of the college.
	     */
	    public String getCourseId() {
	        return courseId;
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
	    abstract Map<String, long[][]> visitorBubble(Map<String, long[][]> dataMap, int numerator);
}
