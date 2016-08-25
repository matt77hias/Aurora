package cwb1a;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates new activities depending on the activityType. It is generally used by the ActivityRegistry. After collecting the 
 * right properties, the ActivityRegistry must recreate the right activity. Their are some public fields, used as flags for the 
 * subclasses of activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class ActivityFactory {
	
	//College flag
	public final static long COLLEGE = 1;
	//Job flag
	public final static long JOB = 2;
	//Nightlife flag
	public final static long NIGHTLIFE = 3;
	//Practice flag
	public final static long PRACTICE = 4;
	//Sleep flag
	public final static long SLEEP = 5;
	//Sport flag
	public final static long SPORT = 6;
	//Study flag
	public final static long STUDY = 7;
	
	/**
	 * @note The constructor is private. You are not allowed to make an object of this class.
	 */
	private ActivityFactory() {
		
	}
	
	/**
	 * Creates the right type of activity, using a Map with the properties. Each if loop does exactly the same for
	 * each kind of activity. A new subkind is constructed and than it's casted to an Activity.
	 * 
	 * @param 	properties 
	 * 			The properties of the kind of activity. This is different for each kind.
	 * @return 	The right kind of activity corresponding the type of the given activity.
	 */
	public static Activity createActivity(Map<String,Object> properties) {
		Activity activity = null;
		if(properties.get("activityType").equals(COLLEGE)) {
			College college = new College(properties);
			activity = college;
		}
		
		else if(properties.get("activityType").equals(JOB)) {
			Job job = new Job(properties);
			activity = job;
		}
		
		else if(properties.get("activityType").equals(NIGHTLIFE)) {
			Nightlife nightlife = new Nightlife(properties);
			activity = nightlife;
		}
		
		else if(properties.get("activityType").equals(PRACTICE)) {
			Practice practice = new Practice(properties);
			activity = practice;
		}
		
		else if(properties.get("activityType").equals(SLEEP)) {
			Sleep sleep = new Sleep(properties);
			activity = sleep;
		}
		
		else if(properties.get("activityType").equals(SPORT)) {
			Sport sport = new Sport(properties);
			activity = sport;
		}

		else if(properties.get("activityType").equals(STUDY)) {
			Study study = new Study(properties);
			activity = study;
		}

		return activity;
		
	}
	
	/**
	 * Returns a map with the activityTypes.
	 * 
	 * @return Map with the activityTypes.
	 */
	public static Map<String,Long> getActivityTypes() {
		HashMap<String,Long> types = new HashMap<String,Long>();
		types.put("College", COLLEGE);
		types.put("Job", JOB);
		types.put("Nightlife", NIGHTLIFE);
		types.put("Practice", PRACTICE);
		types.put("Sleep", SLEEP);
		types.put("Sport", SPORT);
		types.put("Study", STUDY);
		return types;
	}
	
	/**
	 * Returns a map with the activityTypes, reversed mapping.
	 * 
	 * @return A map with the activityTypes, reversed mapping.
	 */
	public static Map<Long,String> getActivityTypesReversed() {
		HashMap<Long,String> types = new HashMap<Long,String>();
		types.put(COLLEGE,"College");
		types.put(JOB,"Job");
		types.put(NIGHTLIFE,"Nightlife");
		types.put(PRACTICE,"Practice");
		types.put(SLEEP,"Sleep");
		types.put(SPORT,"Sport");
		types.put(STUDY,"Study");
		return types;
	}
}