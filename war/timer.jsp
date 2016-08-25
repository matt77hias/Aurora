<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="cwb1a.UserRegistry" %>
    <%@ page import="cwb1a.User" %>
    <%@ page import="cwb1a.Activity" %>
    <%@ page import="cwb1a.ActivityRegistry" %>
    <%@ page import="java.util.Date" %>
    <%@ page import="java.util.Calendar" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Timer - Aurora</title>
</head>
<body>
<% { %>
			<% if(session.getAttribute("id")==null)
        	{
        		String boodschap = "You need to login first.";
        		response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "main.jsp");
        	}

	%> 	

	


<form name="timer"  action="/stopTimer" method="post">
  		Duration of the current activity: <input id="duration" type="text" size="12" name="duration">
    	<input type="submit" class="large button magenta"  value="stop Timer" />
</form>

<input id="pauseButton" class="large button brown" type="submit" value="pause Timer" onclick="pause()" />

	<script type="text/javascript">
	var pauseTime=0;
	var seconds = 0;
	var minutes = 0;
	var hours = 0;
	var t;
	var p;
	var timer_is_on=0;
	
	<% 
		
		String userId = (String) session.getAttribute("id");
		User user = UserRegistry.getInstance().get(userId);
		String currentActivity = user.getCurrentActivityId();
		
		if(currentActivity!=null)
		{
			
			Activity activity = ActivityRegistry.getInstance().get(currentActivity,userId);
			Date pauseStartDate = activity.getPauseStartDate();
			
			Calendar calendar = Calendar.getInstance();
			Date currentDate = calendar.getTime();
			
			int hours = 0;
			int minutes = 0;
			int seconds = 0;
			long pauseDuration = activity.getPauseDuration();
			System.out.println(pauseDuration);
			
			if(pauseStartDate!=null)
			{
				//dan is hij gepauzeerd, indien deze gepauzeerd is, dan is pauseDuration nog niet up to date.
				pauseDuration = pauseDuration + currentDate.getTime() - pauseStartDate.getTime();
				System.out.println(pauseDuration);
				%> 
				timer_is_on = 1;
				document.getElementById("pauseButton").value="herstart Timer";
				<%				
			}
			
			
			
			Date startDate = activity.getStartDate();			
			
			long duration1 = currentDate.getTime();
			long duration2 = startDate.getTime();
			long duration = duration1 - duration2 - pauseDuration;

			duration = duration/1000;
			
			while(duration>3600)
			{
				duration = duration-3600;
				hours++;
			}
			while(duration>60)
			{
				duration = duration-60;
				minutes++;
			}
			seconds = (int) duration;
			%>
			 seconds = <%= seconds%>;
			 minutes = <%= minutes%>;
			 hours = <%= hours%>;
			 var time = hours + ":" + minutes + ":" + seconds;
			 document.getElementById("duration").value=time;
			<%
		}
	
	%>
	
	
	function timedCount()
	{
		clearTimeout(p);
		var time = hours + ":" + minutes + ":" + seconds;
		document.getElementById("duration").value=time;
		 
		seconds = seconds + 1;
		if(seconds >= 60) {
			seconds = 0;
			minutes = minutes + 1;
		}
		if(minutes >= 60) {
			minutes = 0;
			hours = hours + 1;
		}
		
		t=setTimeout("timedCount()",1000);
	}
	
	<%
	
	Activity activity = ActivityRegistry.getInstance().get(currentActivity,userId);
	Date pauseStartDate = activity.getPauseStartDate();
	
	if(pauseStartDate==null)
	{
	
	%> 
		timedCount();
	<%
	}
	%>
	
	function pause()
	{
		
		if(timer_is_on == 0)
		{ 
			setPauseStartDate("true");
			clearTimeout(t);
			timer_is_on=1;
			document.getElementById("pauseButton").value="herstart Timer";
			startPauseTimer();
		}
		else
		{ 

			setPauseStartDate("false");
			clearTimeout(p);
			timer_is_on=0;
			document.getElementById("pauseButton").value="pause Timer";
			timedCount();			
		}
	}

	function startPauseTimer()
	{
		pauseTime = pauseTime + 1;
		document.getElementById("pauseDuration").value= pauseTime;	
		p = setTimeout("startPauseTimer()",100);
	}
	
	function setPauseStartDate(test)
	{
			if (window.XMLHttpRequest)
			  {// code for IE7+, Firefox, Chrome, Opera, Safari
			  xmlhttp=new XMLHttpRequest();
			  }
			else
			  {// code for IE6, IE5
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  }
			xmlhttp.open("GET","/tim?pause=" + test,true);
			xmlhttp.send();	
	}

	
	
	</script>
	<% } %>
</body>
</html>