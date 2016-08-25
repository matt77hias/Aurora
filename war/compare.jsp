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
	<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/listStyle.css" type="text/css" media=all>
	
	
	
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
	
	<script type="text/javascript">
		function chart1() {
			
			document.getElementById("tab1").style.display="block";
			document.getElementById("tab2").style.display="none";

			
			setTimeout("createChart1()",100);
			
			document.getElementById('info_graph').innerHTML="This graph makes a comparison between the total time that all the users have spent on each activity.";

			
		}
		
		function chart2() {
			document.getElementById("tab2").style.display="block";
			document.getElementById("tab1").style.display="none";

			
			setTimeout("createChart2()",100);
			
			document.getElementById('info_graph').innerHTML="This graph gives you an idea of the average progress of all the users.";


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
	
	<% User user = UserRegistry.getInstance().get((String) session.getAttribute("id")); %>
	 	
	<div id="menu">
		<%@ include file="menu.jsp" %>
	</div>
		
	<div id="content">
	
	<div class="whereImNow">
		<u><h5>Compare</h5></u>
	</div>
	  	<div id="default" align="center">
	
  		<ul class="tab"> 
   			<li class="tabChoice"><a class="tabLink" href="#tab1" onclick="chart1()">Global comparison of the activities</a></li> 
    		<li class="tabChoice"><a class="tabLink" href="#tab2" onclick="chart2()">Average global overall progress</a></li> 
  		</ul>
  		
  		<div id="textBox">	
		<p id="info_graph" value=""> </p>	
		</div>
  		
  		<div class="graph" id="tab1" align="center" style="display: none; "><div class="graph" id="chart1" style="height:600px; width:800px; " ></div>
  		</div> 
 		<div class="graph" id="tab2" align="center" style="display: none; "><div class="graph" id="chart2" style="height:600px; width:800px;" ></div>
 		</div>
 		
 		</div>
	</div>
	
	
	
	<script type="text/javascript">
		
	function createChart1() {
		
			<% long d1[] = GraphEngine.getInstance().getSeperateActivityDurationAll(); %>
			  
			var data = [
					    ['College', <%= d1[0] %>],['Job', <%= d1[1] %>], ['NightLife', <%= d1[2] %>], 
					    ['Practice', <%= d1[3] %>],['Sleep', <%= d1[4] %>], ['Sport', <%= d1[5] %>],
					    ['Study', <%= d1[6] %>]
					  ];
			  var plot1 = jQuery.jqplot ('chart1', [data], 
			    { 
			      seriesDefaults: {
			        renderer: jQuery.jqplot.PieRenderer, 
			        rendererOptions: {
				          showDataLabels: true
			        }
			      }, 
			      legend: { show:true, location: 'e' },
			      title: {
		        		text: 'Global comparison of the activities'
		        	}

			    }
			  );
			}
	
	</script>
	
	<script type="text/javascript">
	function createChart2() {
		<%
		Object d2[][] = GraphEngine.getInstance().getAverageTimeVSStudyPointsAllUsers(user); 
		%>

	  	var st = new Array();
		var sp = new Array();
		var courses = new Array();
			
		var sf = 1000 * 60 * 60 * 30;

				 
		<% 
		 for(int i = 0; i < d2.length; i++) {
			%> 
			courses[<%=i%>] = '<%=d2[i][0]%>' ;
			st[<%=i%>] = [<%=d2[i][1] %> / sf ,<%=i + 1%>];
			sp[<%=i%>] = [<%=d2[i][2] %>, <%=i + 1%>];
			<%
		}
		%>
		var data = [st, sp];
 	    		    
    	var plot2 = $.jqplot('chart2',  data, {
        	seriesDefaults: {
            	renderer:$.jqplot.BarRenderer,
            		pointLabels: { show: true, location: 'e', edgeTolerance: -15 },
            			shadowAngle: 135,
            			rendererOptions: {
                			barDirection: 'horizontal'
            			}
        			},
        			legend: {
        				show: true,
        				placement: 'outsideGrid',
    				},
    				series:[
            			{label:'study points reached'},
            			{label:'study points to reach'},
        			],
        			title: {
    					text:'Average global overall progress'
    				},
        			axes: {
            			yaxis: {
                			renderer: $.jqplot.CategoryAxisRenderer,
                			ticks: courses
            			},
            			xaxis: {
            				tickInterval: 1,
            				min: 0
            			}
        			}
    			});
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