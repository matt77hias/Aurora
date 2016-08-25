package cwb1a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * The AchievementRegistry uses the design pattern singleton. This class communicates with the database of google.
 * It is very important that you remember that only properties of achievements will be saved.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class AchievementRegistry
{

        //The parent key for all achievements in the database.
        private Key allAchievements = KeyFactory.createKey(Achievement.staticGetKind(), "allAchievements");
                
        //design pattern singleton.     
        private static AchievementRegistry instance = new AchievementRegistry();
                
        /**
    	 * @note The constructor is private. You are not allowed to make an object of this class.
    	 */
        private AchievementRegistry() {
        }
        
        /**
         * This method returns the only possible operational instance of the AchievementRegistry class.
         * 
         * @return The only possible operational instance of the AchievementRegistry class.
         */
        public static AchievementRegistry getInstance() {
        	return instance;
        }
        
        /**
    	 * Adds an achievement to the the database.
    	 * 
    	 * @param 	achievement 
    	 * 			The achievement that is to be added to the database.
    	 */
        public void add(Achievement achievement) {               
            Entity entity = new Entity(achievement.getKind(), achievement.getId(), allAchievements);
            DatabaseCommunicator.getInstance().put(entity,achievement.getProperties());
        }
        
        /**
    	 * Returns the achievement with the given id.
    	 * 
    	 * @param 	achievementId 
    	 * 			The id of the achievement.
    	 * @return 	The achievement with the given id.
    	 * @throws 	EntityNotFoundException 
    	 * 			The achievement is not found.
    	 */
        public Achievement get(String achievementId) throws EntityNotFoundException {
            Key achievementKey = KeyFactory.createKey(allAchievements, Achievement.staticGetKind(), achievementId);
            return new Achievement(DatabaseCommunicator.getInstance().get(achievementKey).getProperties());
        }
        
        /**
    	 * Returns all the achievements stored in the database.
    	 * 
    	 * @return All the achievements stored in the database.
    	 */
        public List<Achievement> getAll() {
            ArrayList<Achievement> achievements = new ArrayList<Achievement>();
            for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allAchievements, Achievement.staticGetKind())) {
            	achievements.add(new Achievement(lusEntity.getProperties()));
            }
            return achievements;
        }        
        
        /**
         * Returns all the achievements that aren't expired, meaning they've a stop date after the date of method calling.
         * 
         * @return All the achievements that aren't expired.
         */
        public List<Achievement> getActiveAchievements() {
                
        	ArrayList<Achievement> activeAchievements = new ArrayList<Achievement>();
        	Date currentDate = Calendar.getInstance().getTime();
        	for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allAchievements, Achievement.staticGetKind())) {
            	Achievement temp = new Achievement(lusEntity.getProperties());
            	if(currentDate.before(temp.getStopDate()))
            	{
            		activeAchievements.add(temp); 
            	}
        	}
        	
            return activeAchievements;
        }
        
        /**
         * Returns all the achievements of a specific option
         * @param type
         *        The option of the achievement
         * @return A list with all the achievements of a specific option.
         */
        public List<Achievement> getAllOption(long option) {
        	ArrayList<Achievement> achievementsType = new ArrayList<Achievement>();
        	for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allAchievements, Achievement.staticGetKind())) {
            	Achievement temp = new Achievement(lusEntity.getProperties());
            	if(temp.getOption() == option) {
            		achievementsType.add(temp);
            	}
        	}
        	
            return achievementsType;	
        }
        
        /**
         * Returns a list of the achievements filtered on time
         * @param achievements a list with achievements
         * @param filterOption The option:  0: active
         * 									1: finished (or any other number)
         * @return
         */
        public List<Achievement> filterTime(List<Achievement> achievements, int filterOption) {
        	Date currentDate = Calendar.getInstance().getTime();
        	
        	Iterator<Achievement> it = achievements.iterator();
        	
        	while(it.hasNext()) {
        		Achievement temp = it.next();
        		if(filterOption == 0) {
        			if(currentDate.after(temp.getStopDate())) {
        				it.remove();
        			}
        		}
        		else {
        			if(currentDate.before(temp.getStopDate())) {
        				it.remove();
        			}
        		}
        	}
        	
        	return achievements;
        }
        
        /**
    	 * Removes the given achievement.
    	 * 
    	 * @param 	achievement
    	 *        	The achievement that has to be removed.
    	 */
    	public void delete(Achievement achievement) {
    		Key achievementKey = KeyFactory.createKey(allAchievements, achievement.getKind(), achievement.getId());
    		DatabaseCommunicator.getInstance().delete(achievementKey);
    	}
    	
    	/**
    	 * Updates the given achievement in the database.
    	 * 
    	 * @param 	achievement
    	 *        	The achievement that has to be updated.
    	 * @throws 	EntityNotFoundException
    	 *         	The achievement isn't found.
    	 */
    	public void update(Achievement achievement) throws EntityNotFoundException {
    		Key achievementKey = KeyFactory.createKey(allAchievements, achievement.getKind(), achievement.getId());
    		DatabaseCommunicator.getInstance().put((DatabaseCommunicator.getInstance().get(achievementKey)), achievement.getProperties());
    	}
    	
    	/**
    	 * Cleans the achievement part of the database, meaning that every stored achievement is removed from the database.
    	 * Be concerned of the consequences.
    	 */
    	public void cleaner() {
    		for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allAchievements, Achievement.staticGetKind())) {
    			Achievement temp = new Achievement(lusEntity.getProperties());
    			delete(temp);
    		}
    	}
    	
    	/**
    	 * Returns the amount of achievements stored in the database.
    	 * 
    	 * @return 	The amount of achievements stored in the database.
    	 */
    	public long getSize() {
    		return getAll().size();
    	} 
}