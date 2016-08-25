package cwb1a;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.EntityNotFoundException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * This servlet tracks an activity.
 * 
 * @author Aurora
 * @version Griffin
 */

public class TrackingServlet extends HttpServlet
{
	/**
	 * This method tracks an activity chosen by the user. 
	 * @Throws 	Throws an IOexeption when there is no activity chosen.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String activityType = req.getParameter("activity");
		String userId = req.getParameter("userId");
				
		try {
				User user = UserRegistry.getInstance().get(userId);
				Date currentTime = Calendar.getInstance().getTime();
				Activity activity = TrackingController.getInstance().initialiseTrackingStandard(user,activityType,currentTime);
				if(activity == null)
				{
					String learningType = req.getParameter("LearningType");
					String courseId = req.getParameter("course");
					activity = TrackingController.getInstance().initialiseTrackingLearning(user,learningType,currentTime, courseId);
				}
				TrackingController.getInstance().initialiseUpdate(activity, user);
		}
		
		catch (EntityNotFoundException e) {
		}
		resp.sendRedirect("myevents.jsp");
	}
}