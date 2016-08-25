<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="cwb1a.Activity" %>
<%@ page import="cwb1a.ActivityRegistry" %>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.User" %>
<%@ page import="cwb1a.GraphEngine" %>
<%@ page import="cwb1a.LearningTime" %>
<%@ page import="java.util.Map" %>
<%@ page import="cwb1a.ActivityFactory"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=Cp1252"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/> 
	<meta http-equiv="Page-Enter" content="revealTrans(Duration=1,Transition=12)">
	<title> myEvents - Aurora </title>
	<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/hiddenStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>
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
	<script language="javascript" type="text/javascript" src="jqplot/jquery.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/jquery.jqplot.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.pieRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jquery.idTabs.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="jqplot/jquery.jqplot.css" />
	
	
</head>
<body>

	
	<% if(session.getAttribute("id")==null)
        	{
        		String boodschap = "You need to login first.";
        		response.sendRedirect("login.jsp?boodschap=" + boodschap + "&url=" + "myevents.jsp");
        	}
	%> 	

	<div id="menu">
		<%@ include file="menu.jsp" %>
	</div>
		
	<div id="content">	
	
	<div class="whereImNow">
		<u><h5>My Events</h5></u>
	</div>
	
	<ul>	
	<% {
		String userId1 = (String) session.getAttribute("id");
		User userEvents = UserRegistry.getInstance().get(userId1);
		if(userEvents.getCurrentActivityId()!=null)
		{
	%>
	<li>
	<div id="timer_events">
				<%@ include file="timer.jsp" %>		
	</div>	
	</li>
	<% } }
	%>
	
	<li>
	<p> All your activities </p>
	
	<table class="table_gegevens">
	<tr class="table_head">
	<th>Course id </th>
	<th>Activity type</th>
	<th>start Date</th>
	<th>stop Date</th>
	<th>duration (minutes)</th>
	<th>pause duration (minutes)</th>
	
	</tr>
	<% 
	String userId = (String) session.getAttribute("id");
	long schaalfactor = 1000 * 60;
	
	for(Activity lusActivity : ActivityRegistry.getInstance().getAllActivitiesUser(userId)) {
		%>
		<tr class="gegevens">
		<% 
		long type = lusActivity.getType();
		
		if(type == ActivityFactory.COLLEGE || type == ActivityFactory.PRACTICE || type == ActivityFactory.STUDY) {
			LearningTime temp = (LearningTime) lusActivity;
			%>
			<td> <%= temp.getCourseId() %> </td>
			<%
		}
		else {
			%> <td>not available </td> <%
		}
		%>
		<td> <%= ActivityFactory.getActivityTypesReversed().get(lusActivity.getType()) %> </td>
		<td> <%= lusActivity.getStartDate() %></td>
		<td> <%= lusActivity.getStopDate() %></td>
		<td> <%= lusActivity.getDuration() / schaalfactor %></td>
		<td> <%= lusActivity.getPauseDuration() / schaalfactor %></td>
		
		</tr>
	<%
	}
	%>
	
	
	</table>
	</li>
	
	<li>
	<table>
	<tr>
	<td>
	<div id="chart1" style="height:600px; width:800px; " > </div>
	</td>
	</tr>
	</table>
	</li>
	
	<% {
		String userId2 = (String) session.getAttribute("id");
		User userEvents = UserRegistry.getInstance().get(userId2);
		if(userEvents.getCurrentActivityId()==null)
		{
	%>
	<li>
	<p>Tracking a new activity?</p>
	<div>
	<form name="chooseForm">			
		<input type="button" value="Track event" onClick="window.location.href='TrackingChoice.jsp' " class="large button green"/>
	</form> 
	</div>
	</li>
	<% } }%>
	</ul>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			<% long[] d1 = GraphEngine.getInstance().getActiveActivities(); %>
						  
			var data = [['Non active', <%= d1[0] %>],
					    ['College', <%= d1[1] %>],['Job', <%= d1[2] %>], ['NightLife', <%= d1[3] %>], 
					    ['Practice', <%= d1[4] %>],['Sleep', <%= d1[5] %>], ['Sport', <%= d1[6] %>],
					    ['Study', <%= d1[7] %>]
					  ];
			 var plot1 = jQuery.jqplot ('chart1', [data], 
			    { 
				 seriesDefaults: {
				        // Make this a pie chart.
				        renderer: jQuery.jqplot.PieRenderer, 
				        rendererOptions: {
				          // Put data labels on the pie slices.
				          // By default, labels show the percentage of the slice.
				          showDataLabels: true
				        }
				      }, 
				      legend: { show:true, location: 'e' },
				      title: {
				    	  text:'Active activities of the users'
				      }
			    }
			  );
			});
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