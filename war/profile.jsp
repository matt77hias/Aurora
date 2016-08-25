<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="cwb1a.User" %>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.GraphEngine" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Compare - Aurora</title>
	<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/hiddenStyle.css" type="text/css" media=all>
	<script language="javascript" type="text/javascript" src="jqplot/jquery.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/jquery.jqplot.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.pieRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.barRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.pointLabels.min.js"></script>
	<script language="javascript" type="text/javascript" src="jquery.idTabs.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="jqplot/jquery.jqplot.css" />
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
	if(session.getAttribute("id")==null)  {
		String boodschap = "You need to login first.";
       	response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "main.jsp");
    }
	%>
	
	 	
	<div id="menu">
		<%@ include file="menu.jsp" %>
	</div>
	
	<div id="content">
	
	<div class="whereImNow">
		<u><h5>Profile</h5></u>
	</div>  		
  	<div align="center"> <%@ include file="settings.jsp" %> </div> 
	
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