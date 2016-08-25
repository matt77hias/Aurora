<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.User" %>
<%@ page import="cwb1a.Activity" %>
<%@ page import="cwb1a.ActivityRegistry" %>
<%@ page import="cwb1a.ActivityFactory" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>
<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>
<link rel=StyleSheet href="stylesheets/hiddenStyle.css" type="text/css" media=all>

<title>Activity Questions - Aurora</title>
</head>
<body>

	<% if(session.getAttribute("id")==null)
        	{	
        		String boodschap = "You need to login first.";
        		response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "main.jsp");
        	}
	%>
	
	<% User user = UserRegistry.getInstance().get((String) session.getAttribute("id")); %> 	
<div id="content" style="border-radius:25px">
<p> Please answer the questions </p>

<form name="activityQuestions" action="/activityQuestions" method="post">
<% String activityId = user.getCurrentActivityId();
Activity activity = ActivityRegistry.getInstance().get(activityId, user.getId());
long type = activity.getType();
if(type == ActivityFactory.COLLEGE) {
	%>
	<p>Usefullness rate? </br>
	<input type="radio" name="option1" value="5" />very well </br>
	<input type="radio" name="option1" value="4" />just fine </br>
	<input type="radio" name="option1" value="3" CHECKED/>reasonable </br>
	<input type="radio" name="option1" value="2" />bad </br>	
	<input type="radio" name="option1" value="1" />terrible  </br>
	</p>
	<p>Position in the class? </br>
	<input type="radio" name="option2" value="3" />in front </br>
	<input type="radio" name="option2" value="2" CHECKED/>center </br>
	<input type="radio" name="option2" value="1" />behind </br>
	</p>
	<%
}
else if(type == ActivityFactory.JOB) {
	%>
	<p>intensity rate? </br>
	<input type="radio" name="option1" value="5" />very intense </br>
	<input type="radio" name="option1" value="4" />tiresome </br>
	<input type="radio" name="option1" value="3" CHECKED/>acceptable </br>
	<input type="radio" name="option1" value="2" />easy </br>	
	<input type="radio" name="option1" value="1" />light  </br>
	</p>
	<%
	
}
else if(type == ActivityFactory.NIGHTLIFE ) {
	%>
	<p>alcohol rate? </br>
	<input type="radio" name="option1" value="5" />very high </br>
	<input type="radio" name="option1" value="4" />high </br>
	<input type="radio" name="option1" value="3" CHECKED/>acceptable </br>
	<input type="radio" name="option1" value="2" />low </br>	
	<input type="radio" name="option1" value="1" />very low  </br>
	</p>
	<%
	
}
else if(type == ActivityFactory.PRACTICE) {
	%>	
	<p>Usefullness rate? </br>
	<input type="radio" name="option1" value="5" />very well </br>
	<input type="radio" name="option1" value="4" />just fine </br>
	<input type="radio" name="option1" value="3" CHECKED/>reasonable </br>
	<input type="radio" name="option1" value="2" />bad </br>	
	<input type="radio" name="option1" value="1" />terrible  </br>
	</p>
	<p>preparation feeling rate? </br>
	<input type="radio" name="option2" value="5" />very well </br>
	<input type="radio" name="option2" value="4" />just fine </br>
	<input type="radio" name="option2" value="3" CHECKED/>reasonable </br>
	<input type="radio" name="option2" value="2" />bad </br>	
	<input type="radio" name="option2" value="1" />terrible  </br>
	</p>
	
	<% 
}

else if(type == ActivityFactory.SLEEP) {
	%>	
	<p>wake up rate? </br>
	<input type="radio" name="option1" value="5" />very well </br>
	<input type="radio" name="option1" value="4" />just fine </br>
	<input type="radio" name="option1" value="3" CHECKED/>reasonable </br>
	<input type="radio" name="option1" value="2" />bad </br>	
	<input type="radio" name="option1" value="1" />terrible  </br>
	</p>
	<%
	
}

else if(type == ActivityFactory.SPORT) {
	%>
	<p>intensity rate? </br>
	<input type="radio" name="option1" value="5" />very intense </br>
	<input type="radio" name="option1" value="4" />tiresome </br>
	<input type="radio" name="option1" value="3" CHECKED/>acceptable </br>
	<input type="radio" name="option1" value="2" />easy </br>	
	<input type="radio" name="option1" value="1" />light  </br>
	</p>
	<%
	
}

else if(type == ActivityFactory.STUDY) {
	%>	
	<p>quality rate? </br>
	<input type="radio" name="option1" value="5" />very well </br>
	<input type="radio" name="option1" value="4" />just fine </br>
	<input type="radio" name="option1" value="3" CHECKED/>reasonable </br>
	<input type="radio" name="option1" value="2" />bad </br>	
	<input type="radio" name="option1" value="1" />terrible  </br>
	</p>
	
	<p>quantity rate? </br>
	<input type="radio" name="option2" value="5" />very high </br>
	<input type="radio" name="option2" value="4" />high </br>
	<input type="radio" name="option2" value="3" CHECKED />acceptable </br>
	<input type="radio" name="option2" value="2" />low </br>	
	<input type="radio" name="option2" value="1" />very low  </br>
	</p>
	<%
	
	
}
%>
 
<input type="submit" value="confirm" class="large button green" />
</form>
</div>
</body>
</html>