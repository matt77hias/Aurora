package cwb1a;

import java.util.Date;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * This class controls the tracking progress; meaning: starting and stopping tracking, the timer during the tracking and the questionnaire after the tracking.
 * The general qualifications of this class creating new activities and updating users and activities.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class TrackingController {

	// Design pattern Singleton
	private static TrackingController instance = new TrackingController();
	
	/**
	 * @Note Not allowed to create objects of this class.
	 */
	private TrackingController() {
		
	}
	
	/**
     * This method returns the only possible operational instance of the TrackingController class.
     * 
     * @return 	The only possible operational instance of the TrackingController class.
     */
	public static TrackingController getInstance() {
		return instance;
	}
	
	/**
	 * Creates an activity (non learning types) object.
	 * 
	 * @param user
	 *        The owner of the activity.
	 * @param activityType
	 *        The type of the activity.
	 * @param currentTime
	 *        The start date of the activity.
	 * @return
	 */
	public Activity initialiseTrackingStandard(User user, String activityType, Date currentTime) {
		Activity activity = null;
		if(activityType.equals("Sleep")) {
			activity = new Sleep(currentTime, user.getId());
		}
		else if(activityType.equals("Night life")) {
			activity = new Nightlife(currentTime, user.getId());
		}
		else if(activityType.equals("Job")) {
			activity = new Job(currentTime, user.getId());
		}
		else if(activityType.equals("Sport")) {
			activity = new Sport(currentTime, user.getId());
		}
		return activity;
	}
	
	/**
	 * Creates an activity (learning types) object.
	 * 
	 * @param user
	 *        The owner of the activity.
	 * @param learningType
	 *        The type of the learning type activity.
	 * @param currentTime
	 *        The start date of the activity.
	 * @param courseId
	 *        The id of the course for which the activity is intended.
	 * @return
	 */
	public Activity initialiseTrackingLearning(User user, String learningType, Date currentTime, String courseId) {
		Activity activity = null;
		if(learningType.equals("College")) {
			activity = new College(currentTime, user.getId(), courseId );
		}
		else if(learningType.equals("Practice")) {
			activity = new Practice(currentTime, user.getId(), courseId);
		}
		else {
			activity = new Study(currentTime, user.getId(), courseId);
		}
		return activity;
	}
	
	/**
	 * Adds the activity to and updates the user in the database before the start of the tracking.
	 * 
	 * @param activity
	 *        The activity that has to be added to the database.
	 * @param user
	 *        The user who has to be updated in the database.
	 */
	public void initialiseUpdate(Activity activity, User user) {
		try{
			ActivityRegistry.getInstance().add(activity);
			user.setCurrentActivityId(activity.getId());
			UserRegistry.getInstance().update(user);
		}
		catch(EntityNotFoundException e){
			
		}
	}
	
	/**
	 * Updates the current activity of the given user and the given user in the database after the end of the tracking.
	 * 
	 * @param userId
	 *        The id of the user that has to be updated in the database.
	 * @param stopDate
	 *        The end date of the current activity of the user that has to be added to the current activity.
	 */
	public void endUpdate(String userId, Date stopDate) {
		try{
			String activityId = UserRegistry.getInstance().get(userId).getCurrentActivityId();
			
			Activity activity = ActivityRegistry.getInstance().get(activityId, userId);
			activity.setStopDate(stopDate);
			ActivityRegistry.getInstance().update(activity);
		
			User user = UserRegistry.getInstance().get(userId);
			UserRegistry.getInstance().update(user);
		}
		catch(EntityNotFoundException e){
			
		}
	}
	
	/**
	 * Updates the given user to the database after the questionlist of the tracking.
	 * 
	 * @param user
	 *        The user who has to be updated in the database.
	 */
	public void questionnaireUpdate(User user) {
		user.setCurrentActivityId(null);
		try{
			UserRegistry.getInstance().update(user);
		}
		catch(EntityNotFoundException e){
			
		}
	}

}
