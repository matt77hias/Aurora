package cwb1a;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * This servlet controls the timer of the tracking activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class TimerServlet extends HttpServlet {

	/**
	 * The TimerServlet requests the "pause" parameter from the timer.jsp file. If the pause parameter is true,
	 * the user wants to pause the timer. The servlet then puts a pauseStartDate in the activity object.
	 * If the pause parameter is false, the user has stopped taking a break. The servlet then updates the total
	 * pauseDuration and puts the PauseStartDate (in the activity object) to "null".	 * 
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String pause = request.getParameter("pause");
		String userId = (String) request.getSession().getAttribute("id");
		User user = getUser(userId);
		String currentActivity = user.getCurrentActivityId();		
		Activity activity = getActivity(userId,currentActivity);		

		if(pause.equals("true")==true)
		{
			Calendar calendar = Calendar.getInstance();
			Date currentDate = calendar.getTime();			
			activity.setPauseStartDate(currentDate);
		}
		
		else if(pause.equals("false")==true)
		{
			updatePauseDuration(activity);
			activity.setPauseStartDate(null);
			
			
		}
		
		updateActivity(activity);
	}
	
	/**
	 * Returns the user corresponding with the given user id.
	 * 
	 * @param userId
	 *        The id of the given user
	 * @return The user corresponding with the given id.
	 */
	private User getUser(String userId) {
		try
		{
			User user = UserRegistry.getInstance().get(userId);
			return user;
		}
		
		catch (EntityNotFoundException e)
		{
			return null;
		}

	}
	
	/**
	 * Returns the currentActivity of a user corresponding with the given user id and given achievement id.
	 * 
	 * @param userId
	 *        The id of the user.
	 * @param currentActivityId
	 *        The id of the current activity.
	 * @return The currentActivity of a user corresponding with the given user id and given achievement id.
	 */
	private Activity getActivity(String userId, String currentActivityId) {
		try
		{
			Activity activity = ActivityRegistry.getInstance().get(currentActivityId,userId);
			return activity;			
		}
		
		catch (EntityNotFoundException e)
		{
			return null;
		}

	}
	
	/**
	 * Updates the given activity.
	 * 
	 * @param activity
	 *        The activity that has to be updated.
	 */
	private void updateActivity(Activity activity) {
		try
		{
			ActivityRegistry.getInstance().update(activity);		
		}
		
		catch (EntityNotFoundException e)
		{

		}

	}
	
	/**
	 * Updates the pause duration of the given activity.
	 * 
	 * @param activity
	 *        The activity that has to be updated.
	 */
	private void updatePauseDuration(Activity activity) {
		Calendar calendar = Calendar.getInstance();
		Date pauseStopDate = calendar.getTime();
		Date pauseStartDate = activity.getPauseStartDate();
		
		long pauseDuration = pauseStopDate.getTime() - pauseStartDate.getTime();
		activity.addDuration(pauseDuration);
		updateActivity(activity);
	}
}

