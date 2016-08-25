package cwb1a;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet creates achievements.
 * 
 * @author Aurora
 * @version Achievement
 *
 */
public class NewAchievementServlet extends HttpServlet {
	
	/**
	 * This method creates a new achievement and manages the database tasks. 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("new achievement executed");
		long schaalfactorWeek = 7 * 24 * 60 * 60 * 1000;
		long schaalfactorUur = 60 * 60 * 1000;
		String succes = "Achievement successfully created";
		String failure = "Achievement not created because of: ";
		
		
			String name = req.getParameter("achievementname");
			String description = req.getParameter("achievementdescription");
			long option = Long.decode(req.getParameter("option"));
			long amountToReach = (long) ( Double.valueOf(req.getParameter("amounttoreach")) * schaalfactorUur );
			long type = Long.decode(req.getParameter("type"));
			long number = Long.decode(req.getParameter("achievementnumber"));
			long duration = Long.decode(req.getParameter("achievementduration")) * schaalfactorWeek;
			
			Calendar calendar = Calendar.getInstance();
			Date startDate = calendar.getTime();
			
			Date stopDate = AchievementManager.getInstance().calculateStopDate(startDate, duration);
			
			System.out.println("stopdate:" + stopDate);
			System.out.println("duration:" + duration);
			System.out.println("amount to reach" + amountToReach);
			HashMap<Boolean,String> success = AchievementManager.getInstance().createAchievementController(name,
					number, description, amountToReach, option, startDate, stopDate, type);
			
			if(success.containsKey(true)) {
				resp.sendRedirect("/myachievements.jsp?message=" + succes);
			}
			else {
				failure = failure + success.get(false);
				resp.sendRedirect("/myachievements.jsp?message=" + failure);
				System.out.println(success.get(false));
			}	
	}

}
