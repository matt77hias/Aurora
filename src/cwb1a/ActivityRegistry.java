package cwb1a;

import com.google.appengine.api.datastore.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The ActivityRegistry uses the design pattern singleton. This class communicates with the database of google.
 * It is very important that you remember that only properties of activities will be saved.
 * When you ask for an activity, the ActivityRegistry searches the properties in the database and creates with
 * those properties a new activity object corresponding the type of the activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class ActivityRegistry {
	
	
	//The parent key for all activities in the database.
	private Key allActivities = KeyFactory.createKey(Activity.staticGetKind(), "allActivities");
	
	//Design pattern singleton.
	private static ActivityRegistry instance = new ActivityRegistry();
	
	/**
	 * @note The constructor is private. You are not allowed to make an object of this class.
	 */
	private ActivityRegistry() {
	}
	
	/**
	 * This method returns the only possible operational instance of the ActivityRegistry class.
	 * 
	 * @return 	The only possible operational instance of the ActivityRegistry class.
	 */
	public static ActivityRegistry getInstance() {
		return instance;
	}
	
	/**
	 * Adds an activity to the the database.
	 * 
	 * @param 	activity 
	 * 			The activity that is to be added to the database.
	 */
	public void add(Activity activity) {
		Entity entity = new Entity(createKey(activity));
		DatabaseCommunicator.getInstance().put(entity,activity.getProperties());
	}
	
	/**
	 * Returns the activity with the given id.
	 * 
	 * @param 	activityId 
	 * 			The id of the activity.
	 * @param 	userId 
	 * 			The id of the user.
	 * @return 	The activity with the given id.
	 * @throws 	EntityNotFoundException 
	 * 			The activity is not found.
	 */
	public Activity get(String activityId, String userId) throws EntityNotFoundException {
		Key userKey = KeyFactory.createKey(allActivities, Activity.staticGetKind(), userId);
		Key activityKey = KeyFactory.createKey(userKey, Activity.staticGetKind(), activityId);
		return ActivityFactory.createActivity(DatabaseCommunicator.getInstance().get(activityKey).getProperties());
	}
	
	/**
	 * Returns all the activities of the given user.
	 * 
	 * @param 	userId 
	 * 			The id of the user whose activities have to be collected.
	 * @return 	All the activities of the given user.
	 */
	public List<Activity> getAllActivitiesUser(String userId) {
		Key userKey = KeyFactory.createKey(allActivities, Activity.staticGetKind(), userId);
		
		return EntityToActivity(DatabaseCommunicator.getInstance().getAll(userKey, Activity.staticGetKind()));
	}
	
	/**
	 * Deletes the activity with the given id, only when the activity is stored without a
	 * user as key.
	 * 
	 * @param 	activityId 
	 * 			The id of the activity.
	 */
	public void delete(Activity activity) {
		DatabaseCommunicator.getInstance().delete(createKey(activity));
	}

	
	/**
	 * Returns all the activities stored in the database.
	 * 
	 * @return 	All the activities stored in the database.
	 */
	public List<Activity> getAll() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.addAll(EntityToActivity(DatabaseCommunicator.getInstance().getAll(allActivities, Activity.staticGetKind())));
		return activities;	
	}
	
	/**
	 * Replaces a list of entities by a list of corresponding activities.
	 * 
	 * @param 	entities 
	 * 			The entities that have to be replaced by the corresponding activities.
	 * @return 	The corresponding activities.
	 */
	private List<Activity> EntityToActivity(List<Entity> entities) {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		for(Entity lusEntity : entities) {
			activities.add(ActivityFactory.createActivity(lusEntity.getProperties()));
		}
		return activities;
	}
	
	/**
	 * Creates a key for the activity for database communication and storage.
	 * 
	 * @param 	activity 
	 * 			The activity for which a key has to be created.
	 * @return 	Key for the given activity.
	 */
	private Key createKey(Activity activity) {
		Key userKey = KeyFactory.createKey(allActivities, activity.getKind(), activity.getUserId());
		return KeyFactory.createKey(userKey, activity.getKind(), activity.getId());
	}
	
	/**
	 * Returns all the active activities at the moment of calling this method.
	 * An active activity is not fully fledged, meaning that start and stop date are the same.
	 * 
	 * @return 	All the active activities at the moment of calling this method.
	 */
	public List<Activity> getActiveActivity() {
		ArrayList<Activity> activeActivities = new ArrayList<Activity>();
		for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allActivities, Activity.staticGetKind())) {
			Activity temp = ActivityFactory.createActivity(lusEntity.getProperties());
			if(temp.getStartDate().equals(temp.getStopDate()))
			{
				activeActivities.add(temp);
			}
		}
		return activeActivities;
	}
	
	/**
	 * Returns a list with all the activities of a user of a specific activityType.
	 * 
	 * @param 	user 
	 * 			The user whose specific activities that have to be found.
	 * @param 	kind 
	 * 			The type of the activity.
	 * @return 	A list with all the activities of the given user and of the given type.
	 */
	public List<Activity> getAllActivityUserType(User user, long type) {
		List<Activity> allActivitiesUser = getAllActivitiesUser(user.getId());
		ArrayList<Activity> activitiesUser = new ArrayList<Activity>();
		Iterator<Activity> it = allActivitiesUser.iterator();
		
		while(it.hasNext()) { 
			Activity temp = it.next();
			if(temp.getType() == type)
			{
				activitiesUser.add(temp);
			}
		}
		return activitiesUser;
	}
	
	/**
	 * Updates the given activity in the database.
	 * 
	 * @param 	activity 
	 * 			The activity that has to be updated.
	 * @throws 	EntityNotFoundException 
	 * 			The activity isn't found in the database.
	 */
	public void update(Activity activity) throws EntityNotFoundException {
		Key activitiesUserKey = KeyFactory.createKey(allActivities, activity.getKind(), activity.getUserId());
		Key activityKey = KeyFactory.createKey(activitiesUserKey, activity.getKind(), activity.getId());
		DatabaseCommunicator.getInstance().put((DatabaseCommunicator.getInstance().get(activityKey)), activity.getProperties());
	} 
	
	/**
	 * Cleans the activity part of the database, meaning that every stored activity is removed from the database.
	 * Be concerned of the consequences.
	 */
	public void cleaner() {
		for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allActivities, Activity.staticGetKind())) {
			delete(ActivityFactory.createActivity(lusEntity.getProperties()));
		}
	}
	
	/**
	 * Returns the amount of activities stored in the database.
	 * 
	 * @return 	The amount of activities stored in the database.
	 */
	public long getSize() {
		return getAll().size();
	}
}