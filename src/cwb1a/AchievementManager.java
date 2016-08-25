package cwb1a;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.EntityNotFoundException;


/**
 * This class takes control and manages the achievement 'administration'; meaning this class decides that a user has or hasn't obtained an achievement. 
 * 
 * @author Aurora
 * @version Phoenix
 *
 */
public class AchievementManager
{
	// Maximise flag. (achievement option)
	// Passing amountToReach
	public final static long MAXIMISE = 1;
	// Minimise flag. (achievement option)
	// Never passing amountToReach
	public final static long MINIMISE = 0;
	// Design pattern Singleton
	private static AchievementManager instance = new AchievementManager();
	
	/**
	 * @note The constructor is private. You are not allowed to make an object of this class.
	 */
    private AchievementManager() {
    }

    /**
     * This method returns the only possible operational instance of the AchievementManager class.
     * 
     * @return 	The only possible operational instance of the AchievementManager class.
     */
    public static AchievementManager getInstance() {
    	return instance;
    }
    
    /**
     * Returns a map with the possible options of an achievement.
     * @return
     */
    public Map<String, Long> getOptions() {
    	HashMap<String, Long> options = new HashMap<String,Long>();
    	options.put("maximise", MAXIMISE);
    	options.put("minimise", MINIMISE);
    	return options;
    }
    
    /**
     * This method updates the achievements of a given user.
     * 
     * @param 	user
     *        	The user whose achievements have to be updated.
     * @param   option
     *          The option corresponding to the purpose of the achievements.
     * @return 	The new achievements of the user.
     * @throws 	EntityNotFoundException
     *        	The entity doesn't exist in the database.
     */
    public ArrayList<Achievement> update(User user, long option) throws EntityNotFoundException {
		Date currentDate = Calendar.getInstance().getTime();
    	ArrayList<Achievement> updates = new ArrayList<Achievement>();
    	for(Achievement element : AchievementRegistry.getInstance().getAllOption(option))
    	{		
			boolean found = achievementObtainCheck(element, user);
			
			if(currentDate.before(element.getStopDate()) && option == MINIMISE)
			{
				found = true;
			}			
			
			if(found == false)
			{
				if(optionReached(element,getTotalAmount(element, user))==true)
				{
					user.addAchievementId(element.getId());
					updates.add(element);
					element.addUser(user);
					AchievementRegistry.getInstance().update(element);
						
				}
			}	
		}
    	UserRegistry.getInstance().update(user);
    	return updates;
	}
    
    /**
     * Controls if a user has already achieved a given achievement.
     * 
     * @param achievement
     *        The achievement to check.
     * @param user
     *        The user to check for.
     * @return True if the user has already obtained the given achievement. False if not.
     */
    private boolean achievementObtainCheck(Achievement achievement, User user) {
    	ArrayListMap<String> userIds = achievement.getUserList();
		Iterator<String> it = userIds.iterator();
		boolean found = false;
		while(it.hasNext() == true && found == false)
		{
			if(it.next().equals(user.getId()))
			{
				found = true;
			}
		}
		return found;
    }
    
    /**
     * Returns the total amount for a given user corresponding the event type of the achievement.
     * 
     * @param achievement
     *        The achievement which event type is needed in order to get the total amount.
     * @param user
     *        The user whose total amount has to be returned.
     * @return The total amount for a given user corresponding the event type of the given achievement.
     */
    private long getTotalAmount(Achievement achievement, User user) {
    	long amount = 0;
		for(Activity event : ActivityRegistry.getInstance().getAllActivityUserType(user, achievement.getEventType()))
		{
			if((event.getStopDate().before(achievement.getStopDate()) && event.getStartDate().after(achievement.getStartDate()))) {
    			amount = amount + event.getDuration();
    		}
		}
		return amount;
    }
    
    
    
    /*
     * Checks if a user obtains an new achievement. All the achievements will be controlled.
     * The achievements of type MAXIMISE: only control of the active ones.
     * The achievements of type MINIMISE: only control of the finished ones.
     * @param user
     *        The user to be controlled.
     */
    public void checkAndUpdate(User user) {
    	List<Achievement> achievementsMaximise = AchievementRegistry.getInstance().getAllOption(MAXIMISE);
    	achievementsMaximise = AchievementRegistry.getInstance().filterTime(achievementsMaximise, 0);
    	controlAchievementsUser(user, achievementsMaximise);
    	
    	List<Achievement> achievementsMinimise = AchievementRegistry.getInstance().getAllOption(MINIMISE);
    	achievementsMinimise = AchievementRegistry.getInstance().filterTime(achievementsMinimise, 1);
    	controlAchievementsUser(user, achievementsMinimise);
    	
    	try {
    		UserRegistry.getInstance().update(user);
    	}
    	catch (EntityNotFoundException e) {
    		System.out.println(e);
    	}
    	
    }
    
    /*
     * Controls if the user obtains a new achievement of the given list of achievements.
     * @param user
     *        The user to control
     * @param achievements
     *        A list with achievements.
     */
    private void controlAchievementsUser(User user, List<Achievement> achievements) {
    	for(Achievement lusAchievement : achievements) {
    		if(!containsUser(user, lusAchievement)) {
    			updateAchievement(user, lusAchievement);
    		}
    	}
    	
    }
    
    /*
     * Controls if an user already has collected the achievement.
     * 
     * @param user
     *        The user who may have collected the achievement
     * @param achievement
     *        The achievement the user may have collected
     * @return True when the user has already collected the achievement.
     */
    private boolean containsUser(User user, Achievement achievement) {
    	boolean gevonden = false;
    	Iterator<String> it = user.getAchievementsIds().iterator();
    	while(!gevonden && it.hasNext() ) {
    		if(it.next().equals(achievement.getId())) {
    			gevonden = true;
    		}
    	}
    	return gevonden;
    }
    
    /*
     * Controls if an user obtains a new achievement. First the total duration
     * of all the activities who are between the duration of the achievement are calculated.
     * When the achievement is obtained, the userId is added to the achievement and the achievement
     * is updated.
     * The achievementId is added to the user, but the user is NOT updated.
     * 
     * @param user
     *        The user who may obtain a new achievement.
     * @param achievement
     *        The achievement to control
     */
    private void updateAchievement(User user, Achievement achievement) {
    	List<Activity> activities = ActivityRegistry.getInstance().getAllActivityUserType(user, achievement.getEventType());
    	long duration = 0;
    	for(Activity lusActivity : activities) {
    		if(lusActivity.getStopDate().before(achievement.getStopDate())) {
    			if(lusActivity.getStartDate().after(achievement.getStartDate())) {
    				duration = duration + lusActivity.getDuration();
    			}
    		}
    	}
    	
    	boolean optainAchievement = false;
    	if(achievement.getOption() == MAXIMISE) {
    		if(duration >= achievement.getAmountToReach()) {
    			optainAchievement = true;
    		}
    	}
    	if(achievement.getOption() == MINIMISE) {
    		if(duration <= achievement.getAmountToReach()) {
    			optainAchievement = true;
    		}
    	}
    	
    	if(optainAchievement) {
    		user.addAchievementId(achievement.getId());
			achievement.addUser(user);
			try {	
				AchievementRegistry.getInstance().update(achievement);
			}
			catch (EntityNotFoundException e) {
				System.out.println(e);
			}
    	}
    }
    
        
	/**
	 * Checks if the given amount results in obtaining the achievement according the purpose of the achievement (minimise/maxismise).
	 * Warning: This method doesn't check the time span.
	 * 
	 * @param 	achievement
	 *        	The achievement containing the amount to reach or never reach.
	 * @param 	amount
	 *        	The amount to check.
	 * @return 	True if achieved.
	 */
	private boolean optionReached(Achievement achievement, long amount) {
		boolean reached = false;
		if(achievement.getOption() == MAXIMISE) {
			if(amount >= achievement.getAmountToReach()) {
				reached = true;
			}
		}
		else if(achievement.getOption() == MINIMISE) {
			if(amount <= achievement.getAmountToReach()) {
				reached = true;
			}
		}
		return reached;
	}
	
	/**
	 * Creates and stores an achievement object in the database if the start and stop date are conform with logical rules.
	 * 
	 * @pre		start and stop date didn't passed the date of method calling.
	 * 			| !startDate.before(currentDate) || !stopDate.before(currentDate)
	 * @pre		The stopdate is not before the startdate
	 * 			| getStartDate() < getStopDate()
	 * @param 	achievementName
	 *        	The name of the achievement
	 * @param 	achievementNumber
	 *        	The number of the achievement
	 * @param 	achievementDescription
	 *        	The description of the achievement
	 * @param 	amountToReach
	 *        	The amount to reach
	 * @param 	option
	 *        	The option corresponding to the purpose of the achievement
	 * @param 	startDate
	 *        	The date of the begin of the period
	 * @param 	stopDate
	 *        	The date of the end of the period
	 * @param 	eventType
	 *        	The type of the event
	 * @return 	A hashMap with in case of failure a description of the failure with false as corresponding key.
	 *         	In case of success the description is empty with true as corresponding key.
	 */
	public HashMap<Boolean, String> createAchievementController(String achievementName, long achievementNumber, String achievementDescription, long amountToReach, 
			long option, Date startDate, Date stopDate, long eventType) {
		HashMap<Boolean,String> requestMap = new HashMap<Boolean,String>();
		
		boolean accepted = true;
		String returnString = "";
		
		try{
			AchievementRegistry.getInstance().get(cleanString(achievementName));
			accepted = false;
			returnString = returnString + "\n" + "Failure because of an name duplication.";
		}
		catch(EntityNotFoundException E){
		}
		
		if(stopDate.before(startDate))
		{
			accepted = false;
			returnString = returnString + "\n" + "Failure because of an imaginary timespan.";		
		}
		
		Date currentDate = Calendar.getInstance().getTime();
		if(stopDate.before(currentDate))
		{
			accepted = false;
			returnString = returnString + "\n" + "Failure because of a passed stop date.";
		}
		
		if(accepted == true)
		{
			Achievement toStore = new Achievement(achievementName, achievementNumber, achievementDescription, amountToReach,option, startDate, stopDate, eventType);
			AchievementRegistry.getInstance().add(toStore);
		}
		
		requestMap.put(accepted, returnString);
		return requestMap;
	}
	
	 /**       
     * This method clean a String object by removing capital letters and removing empty spaces. 
     *    
     * @param 	request 
     * 			The String that has to be cleaned.      
     * @return 	The cleaned String.      
     */     
	public String cleanString(String request) {          
		String cleanedString = "";          
		for(int i=0; i<request.length(); i++) {             
			char k = request.charAt(i);             
			if(k != ' ')  {                  
				cleanedString += k;              
				}          
			}          
		return cleanedString.toLowerCase();      
	}
	
	/**
	 * Returns the stop date of a span according to a given start date and span duration.
	 * 
	 * @param 	startDate
	 *        	The start date of the span.
	 * @param 	duration
	 *        	The duration of the span.
	 * @return 	The stop date of the span.
	 */
	public Date calculateStopDate(Date startDate, long duration) {
		long temp = startDate.getTime() + duration;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(temp);
		Date date = cal.getTime();
		return date;
	}
}