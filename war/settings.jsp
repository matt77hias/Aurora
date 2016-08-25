<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.DivisionRegistry" %>
<%@ page import="cwb1a.Course" %>
<%@ page import="cwb1a.User" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>
<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>


<title>My settings - Aurora</title>
</head>
<body>

<% if(session.getAttribute("id")==null)
        	{
        		String boodschap = "You need to login first.";
        		response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "settings.jsp");
        	}
%>  
		
	
	<%  
	User user = UserRegistry.getInstance().get((String) session.getAttribute("id"));		
	%> 
		<table>
			<tr>
				<td>Username:
				</td>
				<td><%=user.getUserName() %>
				</td>
			</tr>
			
			<tr>
				<td>User id:
				</td>
				<td><%=user.getId() %>
				</td>
			</tr>
			
			<tr>
				<td>User number:
				</td>
				<td><%=user.getUserNumber() %>
				</td>
			</tr>
			
			<tr>
				<td>User type:
				</td>
				<td><%=user.getType() %>
				</td>
			</tr>
			
			<tr>
				<td>User password: </br>
					<input type="submit" id="passwordButton" value="show password" style="width:120px" onclick="enablePassword()" class="large button magenta" />
				</td>
				<td id="password" style="visibility:hidden"><%=user.getUserPassword() %>
				</td>
			</tr>
			
			<tr>
				<td>My courses </td>
				<td> <ol>
					<%
					for(Course lusCourse : DivisionRegistry.getInstance().getAllUser(user.getId())) {
						%>
						<li><%=lusCourse.getCourseName() %> </li> </br>
						<%
					}
					%>
					</ol>
			</tr>
		
		
		</table>
			
	<script type="text/javascript">
	var enable = false;
	function enablePassword() {
		if(enable) {
			document.getElementById("passwordButton").value="show password";
			document.getElementById("password").style.visibility="hidden";
			enable = false;
		}
		else {
			document.getElementById("passwordButton").value="hide password";
			document.getElementById("password").style.visibility="visible";
			enable = true;
		}
		
	}
	
	</script>
	



</body>
</html>