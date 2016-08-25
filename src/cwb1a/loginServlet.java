package cwb1a;

import javax.servlet.http.*;

import java.io.IOException;

/** This servlet is used to login to the Aurora system. 
 * 
 * @author Aurora
 * @version Griffin
 *
 */

public class loginServlet extends HttpServlet
{
	/**
	 * The servlet requests the username and password from the jsp file. Then it checks if the username already 
	 * exists and if the username and password match. If this is not the case, the servlet sends a message that the
	 * username or password is incorrect.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
			{
				String id = req.getParameter("username");
				String password = req.getParameter("password");
				String url = req.getParameter("url");

				String boodschap = null;

								
				
					boolean test = EntranceController.getInstance().login(id,password);
					if(test==true)
					{
						resp.sendRedirect(url);
						req.getSession().setAttribute("id", id);
						
					}
				
					else
					{
						boodschap = "The username or password you entered is incorrect.";
						resp.sendRedirect("login.jsp?boodschap=" + boodschap);
					}
					
					
				}				
		}	
