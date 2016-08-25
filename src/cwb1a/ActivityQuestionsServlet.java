package cwb1a;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * This servlet is used to ask questions to the users after he or she has ended an activity. The questions depend
 * on what kind of activity the user has just ended. After the user has filled in the questions, the answers are
 * processed here. For each subkind of Activity the corresponding fields are adjusted. 
 * 
 * @author Aurora
 * @version Grffin
 *
 */
public class ActivityQuestionsServlet extends HttpServlet {
	
	/**
	 * Offers the questions for after the tracking corresponding with the activity and manages the database tasks.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
			 String userId = (String) req.getSession().getAttribute("id");
			 try {
				 User user = UserRegistry.getInstance().get(userId);
				 Activity activity = ActivityRegistry.getInstance().get(user.getCurrentActivityId(), userId);
				 long type = activity.getType();
				 
				 if(type == ActivityFactory.COLLEGE) {
						College college = (College) activity;
						college.setUsefulnessRate(Long.decode(req.getParameter("option1")));
						college.setPostion(Long.decode(req.getParameter("option2")));
						ActivityRegistry.getInstance().update(college);
				 }
				 else if(type == ActivityFactory.JOB) {
					 Job job = (Job) activity;
					 job.setFatigueRate(Long.decode(req.getParameter("option1")));
					 ActivityRegistry.getInstance().update(job);
				 }
				 else if(type == ActivityFactory.NIGHTLIFE ) {
					 Nightlife nightlife = (Nightlife) activity;
					 nightlife.setAlcoholRate(Long.decode(req.getParameter("option1")));
					 ActivityRegistry.getInstance().update(nightlife);
				 }
				 else if(type == ActivityFactory.PRACTICE) {
					 Practice practice = (Practice) activity;
					 practice.setUsefulnessRate(Long.decode(req.getParameter("option1")));
					 practice.setPreparationFeelingRate(Long.decode(req.getParameter("option2")));
					 ActivityRegistry.getInstance().update(practice);
				 }

				 else if(type == ActivityFactory.SLEEP) {
					 Sleep sleep = (Sleep) activity;
					 sleep.setWakeUpRate(Long.decode(req.getParameter("option1")));
					 ActivityRegistry.getInstance().update(sleep);
				 }

				 else if(type == ActivityFactory.SPORT) {
					 Sport sport = (Sport) activity;
					 sport.setIntensityRate(Long.decode(req.getParameter("option1")));
					 ActivityRegistry.getInstance().update(sport);

				 }

				 else if(type == ActivityFactory.STUDY) {
					 Study study = (Study) activity;
					 study.setQualityRate(Long.decode(req.getParameter("option1")));
					 study.setQuantityRate(Long.decode(req.getParameter("option2")));
					 ActivityRegistry.getInstance().update(study);
						
				 }
				 TrackingController.getInstance().questionnaireUpdate(user);
				 resp.sendRedirect("/main.jsp");

			 }
			 catch (EntityNotFoundException e) {
				 resp.sendRedirect("/main.jsp");

			 }
			 
			 
		 }
}
