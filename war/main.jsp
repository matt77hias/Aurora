<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="cwb1a.AchievementManager" %>
<%@ page import="java.util.List" %>
<%@ page import="cwb1a.Achievement" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/> 
	<title> Main - Aurora </title>
	<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/hiddenStyle.css" type="text/css" media=all>
	<script language="javascript">	
	function visInvisOneButton(divID) { 
			if (document.getElementById(divID).style.display != "none"){		
				document.getElementById(divID).style.display = "none"; 
			}
			else {
				document.getElementById(divID).style.display = "block";
			}
		}
	</script>
</head>
<body>

<% if(session.getAttribute("id")==null)
        	{
        		String boodschap = "You need to login first.";
        		response.sendRedirect("login.jsp?boodschap=" + boodschap);
        	}
%> 

	<div id="menu">
		<%@ include file="menu.jsp" %>
	</div>
	
	
	<div id="timerContent">
		<%
			{
				User userMain = UserRegistry.getInstance().get((String) session.getAttribute("id"));
					if(userMain.getCurrentActivityId() != null) {
																						%>
							<div id="inputBox">
			   				<%@ include file="timer.jsp" %>
							</div>
			<%
		
					}
					else {
			%>
							<div id="inputBox">
							<%@ include file="TrackingChoice.jsp" %>
							</div>
			<%
			
					}
		
		List<Achievement> newAchievements = AchievementManager.getInstance().update(userMain, AchievementManager.MINIMISE);
		newAchievements.addAll( AchievementManager.getInstance().update(userMain, AchievementManager.MAXIMISE) );
		if(!newAchievements.isEmpty()) {
			%>
			<p>
				<a href="myachievements.jsp">Check out your new achievements!</a> 
			</p>
			
			<%
		}
	}
	%>
	
	</div>
	
	
	<div id="pageCloser">
		<ul id="bottomMenu">
			<li><a href="#" id="copyright" class="showLink" onClick="visInvisOneButton('copyrightDIV')"> © Aurora 2011 </a></li>
			<li><a href="#" id="about" class="showLink" onClick="visInvisOneButton('aboutDIV')"><img border="0" src="stylesheets/images/about.png" alt="About" width="10" height="10" /> About</a></li>
			<li><a href="#" id="disclaimer" class="showLink" onClick="visInvisOneButton('disclaimerDIV')"><img border="0" src="stylesheets/images/disclaimer.png" alt="Disclaimer" width="10" height="10" /> Disclaimer</a></li>
			<li><a href="#" id="privacy" class="showLink" onClick="visInvisOneButton('privacyDIV')"><img border="0" src="stylesheets/images/privacy.png" alt="Privacy" width="10" height="10" /> Privacy</a></li>
		</ul>
	</div>	
	
	<div id="aboutDIV" class="infoWindow" align="center">
		 <%@ include file="about.jsp" %>
	</div>
			
	<div id="disclaimerDIV" class="infoWindow" align="center">
		<%@ include file="disclaimer.jsp" %>
	</div>
	
	<div id="privacyDIV" class="infoWindow" align="center">
		<%@ include file="privacy.jsp" %>
	</div>

	<div id="copyrightDIV" class="infoWindow" align="center">
	</div>
</body>

</html>