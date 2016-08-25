package cwb1a;

import java.util.Date;

/**
 * This class controls that the events of which users could obtain achievements have fixed implementations
 * of the methods of this class.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public interface AchievementGuardian {
	
	/**
	 * Returns the duration of the event.
	 * 
	 * @return 	the duration of the event.
	 */
	public abstract long getDuration();
	
	/**
	 * Returns the start date of the event.
	 * 
	 * @return 	The start date of the event.
	 */
	public abstract Date getStartDate();
	
	/**
	 * Returns the stop date of the event.
	 * 
	 * @return 	The stop date of the event.
	 */
	public abstract Date getStopDate();
	
	/**
	 * Returns the type of the event.
	 * 
	 * @return 	The type of the event.
	 */
	public abstract long getType();
}
