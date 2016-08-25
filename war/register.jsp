
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ page import="cwb1a.DivisionRegistry" %>
<%@ page import="cwb1a.DivisionFactory" %>
<%@ page import="cwb1a.Division" %>
<%@ page import="cwb1a.Course" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.DefaultData" %>
<%@ page import="cwb1a.Subdivision" %>
<%@ page import="cwb1a.DivisionController" %>
<%@ page import="java.util.Map" %>

  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Register - Aurora </title>
	<link rel=StyleSheet href="stylesheets/registerStyle.css" type="text/css" media=all>
	
	<script>
	var courseChecked = false;
	var count = 0;
	
	function checkInput() 
	{
		
		var usernameCheck = checkUserName();
		var password = document.getElementById("password").value;
		var passwordCheck1=true;
		var passwordCheck2 = checkPassword2();
		var userNumberCheck = checkUserNumber();
		var succeeded = false;
		document.getElementById('icon5').style.visibility = "visible"; 
		
		if(!password)
			{
			
				passwordCheck1 = false;
				document.getElementById('icon2').src="stylesheets/images/cross-icon.jpg";	
				document.getElementById('icon2').style.visibility = "visible"; 
			
			}
		
		if(courseChecked==false)
			{
				document.getElementById('icon5').src="stylesheets/images/cross-icon.jpg";
				document.getElementById('message_courses').innerHTML="Please select your courses.";
			}
		
		else if(courseChecked==true)
			{			
				document.getElementById('icon5').src="stylesheets/images/check-icon.jpg";
			}

		if(usernameCheck == true && passwordCheck2 == true && userNumberCheck == true && passwordCheck1==true && courseChecked == true )
			{
				succeeded = true;
			
			}
		
		else
			{
				document.getElementById('message_end').innerHTML="Please enter the necessary information before you continue.";
			}

		return succeeded
		
	}
	function checkUserName()
	{
			var userId = document.getElementById("username").value;
			document.getElementById('icon1').style.visibility = "visible"; 		
			var succeeded = true;
			var unique = "false";
			if(!userId)
				{
					succeeded = false;				
					document.getElementById('icon1').src="stylesheets/images/cross-icon.jpg";
					document.getElementById('message_username').innerHTML="";	
				}
			else
			{
				if (window.XMLHttpRequest)
				  {// code for IE7+, Firefox, Chrome, Opera, Safari
				 	 xmlhttp=new XMLHttpRequest();
				  }
				else
				  {// code for IE6, IE5
					  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				  }
				
				xmlhttp.onreadystatechange=function()
				  {
				 	if (xmlhttp.readyState==4 && xmlhttp.status==200)
				    {
					   unique = xmlhttp.responseText;
				    }
				  }
				xmlhttp.open("GET","/reg?userId=" + userId,false);
				xmlhttp.send();	
				
				if(unique.match("true") == "true")
					{	
						document.getElementById('icon1').src="stylesheets/images/check-icon.jpg";
						document.getElementById('message_username').innerHTML="";
						succeeded = true;
					}
				
				else
					{
						document.getElementById('icon1').src="stylesheets/images/cross-icon.jpg";
						document.getElementById('message_username').innerHTML="This username is already in use.";	
						succeeded = false;
					}

			}
			
			
			return succeeded;			
	}
	
	function checkPassword1()
	{
		// indien er iets verandert aan het eerste password veld, dan wordt het "re-enter password field" terug leeggemaakt en het icon verdwijnt.
		document.getElementById("password2").value="";
		document.getElementById("password2").disabled=true;
		document.getElementById('message_password2').innerHTML="";
		document.getElementById('icon3').style.visibility = "hidden"; 
		
		
		var password = document.getElementById("password").value;
		document.getElementById('icon2').style.visibility = "visible";
		var succeeded = true;
		
		if(password.length<7||password.length>14)
			{
				succeeded = false;
				
				document.getElementById('icon2').src="stylesheets/images/cross-icon.jpg";
				document.getElementById('message_password1').innerHTML="Your password has to be longer than 7 characters and shorter than 14.";				
			}
		
		else
			{
				document.getElementById("password2").disabled=false;
				document.getElementById('icon2').src="stylesheets/images/check-icon.jpg";
				document.getElementById('message_password1').innerHTML="";	
				
			}
		return succeeded;
	}
	
	function checkPassword2() 
	{
		var password = document.getElementById("password").value;
		var password2 = document.getElementById("password2").value;
		var succeeded = false;
		document.getElementById('icon3').style.visibility = "visible"; 
		if(!password2)
			{
			// deze test is er voor als de gebruiker op submit duwt en nog niet alles heeft ingevoerd.
			document.getElementById('icon3').src="stylesheets/images/cross-icon.jpg";
			
			}
		
		else
			{
			
			if(password == password2)
			{
				succeeded = true;
				document.getElementById('icon3').src="stylesheets/images/check-icon.jpg";
				document.getElementById('message_password2').innerHTML="";	
			}
			
			else
			{
				document.getElementById('icon3').src="stylesheets/images/cross-icon.jpg";
				document.getElementById('message_password2').innerHTML="The passwords don't match.";
			}
			
			}
			return succeeded
		
	}
	
	function checkUserNumber() 
	{
		document.getElementById('icon4').style.visibility = "visible"; 		
		var usernumber = document.getElementById("usernumber").value;
		var succeeded = true;
		if(!usernumber)
			{
				succeeded = false;				
				document.getElementById('icon4').src="stylesheets/images/cross-icon.jpg";
				document.getElementById('message_usernumber').innerHTML="";	
			}
		
		else
			{
				document.getElementById('icon4').src="stylesheets/images/check-icon.jpg";
				document.getElementById('message_usernumber').innerHTML="";	
				
			}
		return succeeded;		
	}
	
	function checkCourse(checkbox)
	{
		var checked = checkbox.checked;
		if(checked == true)
		{	
			count=count+1;

			courseChecked=true;		
		}
		
		else
		{
			count = count-1;

			if(count==0)
				{
				courseChecked=false;
				}
		}

	}
	
	</script>
	
	<script type="text/javascript">
	function initialiseTree() {
		lusKey = "General";
		document.getElementById(lusKey).style.visibility="visible";
		document.getElementById("restartTree").disabled=false;
		document.getElementById("enableTree").disabled="disable";
	}
	
	function a() {
		
		<%
		{
			Map<String, ArrayList<String>> tree = DivisionController.getInstance().generateMainTree();
			for(String lusKey : tree.keySet()) {
				%>
				document.getElementById("<%=lusKey%>").style.visibility="hidden";
				<%
			}
			
		}
		
		%>
		document.getElementById("enableTree").disabled=false;
		document.getElementById("restartTree").disabled="disable";
	}
	
	function enableTrunk2(select) {
		for(var i = 0 ; select.options.length; i++) {
			if(select.options[i].selected) {
				if(document.getElementById(select.options[i].value) != null) {
					document.getElementById(select.options[i].value).style.visibility="visible";
					break;
				}
				else {
					alert("No more subdivisions");
				}
			}
		}
	}
	
	function enableTrunk() {
		var select = document.getElementById(lusKey);
		for(var i = 0 ; i < select.options.length; i++) {
			if(select.options[i].selected == true) {
				var templusKey = select.options[i].value;
				if(document.getElementById(templusKey) != null) {
					lusKey = templusKey;
					document.getElementById(lusKey).style.visibiliy="visible";
					break;
				}
				else {
					alert("No more subdivisions");
				}
			}
		}
	}
	
	
	
	</script>
	
</head>
<body>
<%
	DefaultData.createDefaultData();

%>
	<div id="registerContainer" class="transparantContainer">
	
	<div class="container">
	<div class="form">
	<br><a href="login.jsp"><img src="stylesheets/images/logo_small.png" alt="aurora" width="300"/></a><br><br>
					<form action="/reg" method="post" onsubmit="return checkInput()">
					<table>
					<tr>
					<td>Your username </td> <td> <input id="username" type="text" name="username" onblur="return checkUserName()"  /> </td> <td> <img src="stylesheets/images/check-icon.jpg" id="icon1" width="28" height="20"> </td> <td> <p class="message" id="message_username" value=""></p> </td>
					</tr>
					<tr>
					<td>Choose a password </td> <td> <input type="password" name="password" id="password" oninput="return checkPassword1()"/> </td> <td> <img src="stylesheets/images/cross-icon.jpg" id="icon2" width="28" height="20"> </td> <td> <p class="message" id="message_password1" value=""></p> </td>
					</tr>

					<tr>
					<td>Re-enter the password </td> <td> <input type="password" name="password2" id="password2" oninput="return checkPassword2()" disabled="true"/> </td> <td> <img src="stylesheets/images/cross-icon.jpg" id="icon3" width="28" height="20"> </td> <td> <p class="message" id="message_password2" value=""></p> </td>
					</tr>
					<tr>
					<td>Your student number </td> <td> <input type="text" name="usernumber" id="usernumber" onblur="return checkUserNumber()" /> </td> <td> <img src="stylesheets/images/check-icon.jpg" id="icon4" width="28" height="20" > </td> <td> <p class="message" id="message_usernumber" value=""></p> </td>
					</tr>
       		    	</table>
       		    	
       		    	<p>Select your subdivisions
       		    	(to enable the next subdivision, just click somewhere) </p>
       		    		<input type="button" class="button large magenta" id="enableTree" value="enable division tree" onclick="initialiseTree()" />
       		    		<input type="button"  class="button large brown" id="restartTree" value="restart division tree" onclick="a()" disabled="disabled" />
       		    		
       		    	<ul id="onderverdeling" >
						<%
						Map<String, ArrayList<String>> tree = DivisionController.getInstance().generateMainTree();
						for(String lusKey : tree.keySet()) {
							%>
							<li style="visibility:hidden; padding:5px">
							<select name="<%=lusKey %>" id="<%=lusKey %>" onblur=enableTrunk2(this)>
							<%
							for(String lusId : tree.get(lusKey)) {
								Subdivision sub = (Subdivision) DivisionRegistry.getInstance().get(lusId);
								%>
								<option value="<%=sub.getId() %>"><%=sub.getSubdivisionName() %> </option>
								<%
							}
							%>
							</select> </li>
							<%
						}
						
						%>
					</ul>
					       		    	
					
					
					
       		    	<table>
       		    	<tr>
					<td>Select the courses you are submitted to.</td>  <td> <img src="stylesheets/images/cross-icon.jpg" id="icon5" width="28" height="20"> </td> <td> <p class="message" id="message_courses" value=""></p> </td>
					</tr>
       		    	</table>
       		    	<ul>
       		    	<%
       		    	List<Division> divisions = DivisionRegistry.getInstance().getAllType(DivisionFactory.COURSE);
       		    		
       		    	for(Division element: divisions) {   
       		    		Course course = (Course) element;
       		    		String courseName = course.getCourseName(); %>
       		    		<li> <input type="checkbox" name="<%=course.getId()%>" value="<%=course.getId()%>" onclick="checkCourse(this)"/>
       		    		<%=courseName %></li>
       		    	
       		    	<%
       		    			
       		    	}   		    			
					  %>
					</ul>
					<table>
					<tr> 
					<td> <input type="submit" value="Register" class="button large magenta" /> </td>
					<td> <input type="button" value="cancel" onclick="window.location.href='login.jsp'" class="button large brown" color="white" /> </td>
					<td> <p class="message" id="message_end" value=""></p> </td>
					</tr>
					</table>	
       		    
					</form> 
	</div>
	</div>
	</div>
</body>

</html>