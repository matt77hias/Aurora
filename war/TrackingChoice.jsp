<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@ page import="cwb1a.Course" %>
<%@ page import="cwb1a.DivisionRegistry" %>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

function CheckInput(dropdown)
{
    var index  = dropdown.selectedIndex;
    var selected = dropdown.options[index].value;
    
    
    if(selected=="Learning")
    	{
    		
    		document.getElementById("courseChoice").disabled=false;	
    		document.getElementById("LearningType").disabled=false;
    	}
    
    else
    	{
    		
    		document.getElementById("courseChoice").disabled=true;
    		document.getElementById("LearningType").disabled=true;    	
    	}

}
</script>

<title>Aurora - Tracking your studies</title>
</head>
<body>

	<% if(session.getAttribute("id")==null)
        	{
        		String boodschap = "You need to login first.";
        		response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "TrackingChoice.jsp");
        	}
	%> 


	<p> <b> Choose an activity to be tracked </b><br/>
	<form name="trackingInput" action="/tracking" method="post">
		<p>
		<select name="activity" id="activityChoice" onchange='CheckInput(this.form.activity);'>
			<option value="Sleep">Sleep </option>
			<option value="Job">Job </option>
			<option value="Night life">Night Life </option>
			<option value="Sport">Sport </option>
			<option value="Learning">Learning </option>
		</select> <br /> <p />
		
		<p>
		<select name="LearningType" id="LearningType" disabled="disabled">
			<option value="College">College </option>
			<option value="Practice">Practice </option>
			<option value="Study">Study </option>
		</select>
		</p> <br />
		
		<p>
		<select name="course" id="courseChoice" disabled="disabled">
			<%
			for(Course listCourse: DivisionRegistry.getInstance().getAllUser((String) session.getAttribute("id")) ) {
				%>
				<option value="<%= listCourse.getId() %>"> <%=listCourse.getCourseName() %>
				<% 
			}
			%>
		
		</select> <p />
		<input type="hidden" name="userId" value="<%= session.getAttribute("id") %>"  />
		<input type="submit" value="Start Timer" class="large button green" />
	</form>


	<p /> 



</body>


</html>