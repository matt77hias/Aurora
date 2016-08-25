package cwb1a;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet ends the tracking of an activity.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class TrackingFinished extends HttpServlet {
	
	/**
	 * Ends the tracking of the activity and manages the database tasks.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Date stopDate = Calendar.getInstance().getTime();
		String userId =  (String) req.getSession().getAttribute("id");
		TrackingController.getInstance().endUpdate(userId, stopDate);
		resp.sendRedirect("activityQuestions.jsp");
	}
}
