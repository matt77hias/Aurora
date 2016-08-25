<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="cwb1a.User" %>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.Achievement" %>
<%@ page import="cwb1a.AchievementRegistry" %>
<%@ page import="java.util.List" %>
<%@ page import="cwb1a.AchievementManager" %>
<%@ page import="java.util.Map" %>
<%@ page import="cwb1a.ActivityFactory" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My achievements - Aurora</title>
<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>
<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>
<link rel=StyleSheet href="stylesheets/hiddenStyle.css" type="text/css" media=all>
	<script language="javascript" type="text/javascript" src="jquery.idTabs.min.js"></script>

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

	<%
	if(session.getAttribute("id")==null) {
   		String boodschap = "You need to login first.";
   		response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "myachievements.jsp");
    }
	%>
	
	<div id="menu">
		<%@ include file="menu.jsp" %>
	</div>

	<%
	long schaalfactorUur = 1000 * 60;
	{
		//updates the achievements of the user.
		User user = UserRegistry.getInstance().get((String) session.getAttribute("id"));
		AchievementManager.getInstance().checkAndUpdate(user);
	}
	%>
	
	<div id="content" >
			
	<div class="whereImNow">
		<u><h5>My Achievements</h5></u>
	</div>
	<ul>
	<li>
	<p> Achievements collected </p>

	<table class="table_gegevens">
		<tr>
			<th>Achievement name
			</th>
			<th>Achievement description
			</th>
			<th>Start date
			</th>
			<th>Stop date
			</th>
			<th>amount to reach (minutes)
			</th>
			<th>Amount of users collected the achievement
			</th>
		</tr>
	<%
	{
		User user = UserRegistry.getInstance().get((String) session.getAttribute("id"));
		for(String lusAchievementId : user.getAchievementsIds()) {
			Achievement achievement = AchievementRegistry.getInstance().get(lusAchievementId);
			%>
			<tr>
				<td><%= achievement.getAchievementName() %>
				</td>
				<td><%= achievement.getAchievementDescription() %>
				</td>
				<td><%= achievement.getStartDate() %>
				</td>
				<td><%= achievement.getStopDate() %>
				</td>
				<td><%= achievement.getAmountToReach() / schaalfactorUur %>
				</td>
				<td> <%= achievement.getUserList().size() %>
				</td>
			</tr>
			
			<%
		}
	}
	%>		
	</table>
	</li>
	
	<li>
	<p> Active achievements </p>
	
	<table class="table_gegevens">
		<tr>
			<th>Achievement name
			</th>
			<th>Achievement description
			</th>
			<th>Start date
			</th>
			<th>Stop date
			</th>
			<th>amount to reach (minutes)
			</th>
			<th>Amount of users collected the achievement.
			</th>
		</tr>
	<%
	{
		for(Achievement lusAchievement : AchievementRegistry.getInstance().getActiveAchievements()) {
			%>
			<tr>
				<td><%= lusAchievement.getAchievementName() %>
				</td>
				<td><%= lusAchievement.getAchievementDescription() %>
				</td>
				<td><%= lusAchievement.getStartDate() %>
				</td>
				<td><%= lusAchievement.getStopDate() %>
				</td>
				<td><%= lusAchievement.getAmountToReach() / schaalfactorUur %>
				</td>
				<td> <%= lusAchievement.getUserList().size() %>
				</td>
			</tr>
			
			<%
		}
	}
	%>		
	</table>
	</li>
	
	<li>
	<p>
		<input type="button" value="New achievement?" onclick="showNewAchievement()" class="large button green"></button>
		<% 
			String message = request.getParameter("message");
			if(message != null) {
				%>
				<%=message %>
				<%
			}
			%>
	</p>
	</li>
	</ul>
	<div id="shownewachievement" style="visibility:hidden" >
	<p>
	<form id="newachievement" action="/newachievement" method="post">
		<table>
		<tr>
		<td>The name of the achievement
		</td>
		<td> <input type="text" name="achievementname" value="new achievement" />
		</td>
		</tr>
		
		<tr>
		<td>An description of the achievement
		</td>
		<td> <textarea name="achievementdescription" rows="5" cols="30">description </textarea>													
		</td>
		</tr>
		
		<tr>
		<td>Choose the option of the achievement
		</td>
		<td>
		<select name="option">
			<%
			{
				Map<String, Long> options = AchievementManager.getInstance().getOptions();
				for(String key: options.keySet()) {
					%>
					<option value="<%=options.get(key)%>"><%=key%></option>
					<%
				}
			}
			
			%>
		</select>
		
		</td>
		</tr>
		
		<tr>
		<td>The amount of hours to reach to collect the achievement
		</td>
		<td>
			<input type="text" name="amounttoreach" value="2.5" /> Hour
		
		</td>
		</tr>
		
		<tr>
		<td>The kind of activity of the achievement 
		</td>
		<td>
		<select name="type">
			<%
			{
				Map<String, Long> options = ActivityFactory.getActivityTypes();
				for(String key: options.keySet()) {
					%>
					<option value="<%=options.get(key)%>"><%=key%></option>
					<%
				}
			}
			
			%>
		</select>
		
		
		</td>
		</tr>
		
		<tr>
		<td>The number of the achievement
		</td>
		<td>
			<input type="text" name="achievementnumber" value="123" />
		</td>
		</tr>
		
		<tr>
		<td>Duration to complete the achievement
		</td>
		<td>
		<select name="achievementduration">
			<option value="1">1 week</option>
			
			<%
			{
				for(int i = 2; i <= 10; i++) {
					%>
					<option value="<%=i%>"><%="" + i + " weeks"%></option>
					<%
				}
			}
			
			%>
		</select>
		
		</td>
		</tr>
	
		<tr>
		<td>
		<input type="submit" value="create new achievement" class="large button green" />
		</td>
		<td>
			
		
		</td>
		</tr>
		
		</table>
	</from>
 	</p>
	</div>
	
	
	
</div>



<script type="text/javascript"> 
	  $("#achievementContent ul").idTabs(); 
	</script>

<script type="text/javascript">
function showNewAchievement() {
	document.getElementById("shownewachievement").style.visibility="visible";
}

</script>
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