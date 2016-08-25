<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel=StyleSheet href="stylesheets/menuStyle.css" type="text/css" media=all>
</head>

<body>
	<div id="menu">
		<ul id="topMenu">
			<li><a href="main.jsp" id="titleButton" title="Home"><img border="0" src="stylesheets/images/logo.png" alt="Aurora" height="58px" /></a>
			<li><a href="myevents.jsp" id="eventsButton" title="MyEvents"><img border="0" src="stylesheets/images/myevents.png" alt="My Events" width="35" height="35" /> <br> My Events</a></li>
			<li><a href="mystats.jsp" id="statsButton" title="MyStats"><img border="0" src="stylesheets/images/mystats.png" alt="My Stats" width="35" height="35" /> <br> My Stats</a></li>
			<li><a href="myachievements.jsp" id="achievementButton" title="MyAchievements"><img border="0" src="stylesheets/images/myachievements.png" alt="My Achievements" width="35" height="35" /> <br> My Achievements</a></li>
			<li><a href="compare.jsp" id="compareButton" title="Compare"><img border="0" src="stylesheets/images/compare.png" alt="Compare" width="35" height="35" /> <br> Compare</a></li>
			<li><a href="profile.jsp" id="titleButton" title="MyProfile" ><img border="0" src="stylesheets/images/user.png" alt="User" width="35" height="35" /> <br><%= session.getAttribute("id") %></a></li>
			<li><a href="logout.jsp" id="logoutButton" title="LogOut" ><img border="0" src="stylesheets/images/logout-icon.png" alt="User" width="35" height="35" /> <br> Log Out </a></li>
		</ul>
	</div>
</body>
</html>