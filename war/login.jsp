<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/> 
	<title> Log-in - Aurora </title>
	<link rel=StyleSheet href="stylesheets/registerStyle.css" type="text/css" media=all>
	<script type="text/javascript">	
		function validateData()
		{
			document.getElementById('icon1').style.visibility = "hidden"; 	
			document.getElementById('icon2').style.visibility = "hidden"; 	
			
			var username = document.getElementById("username").value;		
			var password = document.getElementById("password").value;
			var test;
			
			if (!username || !password)
				  {		  
				  	test = false;
				  	document.getElementById('message').innerHTML="Please enter the necessary information.";
				  	
				  	if(!username)
				  		{
				  			document.getElementById('icon1').src="stylesheets/images/cross-icon.jpg";
				  			document.getElementById('icon1').style.visibility = "visible"; 	
				  		}
				  	
				  	if(!password)
				  		{
				  			document.getElementById('icon2').src="stylesheets/images/cross-icon.jpg";
				  			document.getElementById('icon2').style.visibility = "visible"; 	
				  		}			  	
			 	  }
	
			else
				{
				 	test = true;
					document.getElementById('message').innerHTML="";			 	
				}
			
			return test;
		}
		function loginDisplay(){
			document.getElementById('loginContainer').style.height = '170px';
			document.getElementById('loginContainer').style.display = "block";
			document.getElementById('loginButton').style.display = "none";
		}
	</script>
</head>

<body>
	<div id="startContainer" class="transparantContainer">
		<br><a href="login.jsp"><img src="stylesheets/images/logo_small.png" alt="aurora" width="300"/></a><br><br> <br>
		<%	if(request.getParameter("boodschap")!=null)
				{
					String boodschap = request.getParameter("boodschap");
					%> <p> <%= boodschap %> </p> <%
						
				}
		%> <br>
		<a href="register.jsp" class="button large green"> <span>New user</span></a>
		<a href="#" class="button large brown" id="loginButton" onclick="loginDisplay()"> <span> Login </span></a>
	</div>
	
	<div id="loginContainer" class="transparantContainer">
	
		<div class="form">
			<br>
			<h2> Login </h1>
				<% 
					String url = "main.jsp"; 
					if(request.getParameter("url")!=null)
						{
						url = request.getParameter("url");		
					}
				%>	
				<div class="formField">
					<form name="loginForm" action="/log" method="post" onsubmit="return validateData()">
						Username: <input id="username" type="text" name="username" /> <img src="stylesheets/images/cross-icon.jpg" id="icon1" width="28" height="20"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Password: <input id="password" type="password" name="password" /> <img src="stylesheets/images/cross-icon.jpg" id="icon2" width="28" height="20"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="submit" value="Log-in" class="button large brown" color="white"/>
					
						<div>
							<p id="message" value=""></p>
						</div>					
					
						<input type="hidden" value="<%=url%>" name="url" />
						
					</form> 
				</div>
		</div>
     </div>  
</body>
</html>