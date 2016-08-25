package cwb1a;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import com.google.appengine.api.datastore.EntityNotFoundException;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * This servlet serves to register a new user. 
 * 
 * @author Aurora
 * @version Griffin
 */
public class registerServlet extends HttpServlet
{
	/**
	 * The servlet request the username, password and usernumber of the student that wants to register. It also
	 * prints the courses that can be selected. Then the servlet checks if the username is free, if so a new user
	 * with the given properties is created and a message is shown. Than the user is added to all the selected
	 * courses. If the username is taken, the system also shows a message. 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
			{
				String id = req.getParameter("username");
				String password = req.getParameter("password");
				String usernumber = req.getParameter("usernumber");
				String boodschap = null;
				System.out.println(req.getParameter("selectedCourses"));
				
				List<Division> courses = DivisionRegistry.getInstance().getAllType(DivisionFactory.COURSE);
				Iterator<Division> it = courses.iterator();
				while(it.hasNext() ) {
					Division division = it.next();
					String courseId = req.getParameter(division.getId());
					if(courseId == null) {
						it.remove();
					}
					
				}
				
				boolean test = EntranceController.getInstance().register(id, usernumber, password, courses);
				if(test==true)
				{
					boodschap = "You've been successfully registered.";
				}
				
				else
				{
					boodschap = "This username is already in use.";
				}	
				resp.sendRedirect("login.jsp?boodschap=" + boodschap);
				
			}
	
	/**
	 * Controls if the username is unique. (Isn't already stocked in the database.)
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String proposal = request.getParameter("userId");
		boolean unique = UserRegistry.getInstance().verifyRegistration(proposal);		
		PrintWriter out = response.getWriter();
		out.println(unique);
	}
	
}