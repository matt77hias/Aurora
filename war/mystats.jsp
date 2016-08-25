<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="cwb1a.GraphEngine" %>
<%@ page import="cwb1a.User" %>
<%@ page import="cwb1a.UserRegistry" %>
<%@ page import="cwb1a.DivisionRegistry" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/> 
	<meta http-equiv="Page-Enter" content="revealTrans(Duration=1,Transition=12)">
	<title> MyStats - Aurora </title>
	<link rel=StyleSheet href="stylesheets/mainStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/hiddenStyle.css" type="text/css" media=all>
	<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="excanvas.js"></script><![endif]-->
	<script language="javascript" type="text/javascript" src="jqplot/jquery.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/jquery.jqplot.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.pieRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.barRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.pointLabels.min.js"></script>
	<script language="javascript" type="text/javascript" src="jqplot/plugins/jqplot.bubbleRenderer.min.js"></script>
	<script language="javascript" type="text/javascript" src="jquery.idTabs.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jqplot/jquery.jqplot.css" />
	<link rel=StyleSheet href="stylesheets/buttonStyle.css" type="text/css" media=all>
	<link rel=StyleSheet href="stylesheets/listStyle.css" type="text/css" media=all>
	
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
		document.getElementById("tab3").style.display="none";
		document.getElementById("tab4").style.display="none";
		document.getElementById("tab5").style.display="none";
		
		setTimeout("createChart1()",100);
		document.getElementById('info_graph').innerHTML="This graph makes a comparison between all your activities.";	

	}
	
	function chart2() {
		document.getElementById("tab1").style.display="none";
		document.getElementById("tab2").style.display="block";
		document.getElementById("tab3").style.display="none";
		document.getElementById("tab4").style.display="none";
		document.getElementById("tab5").style.display="none";
		
		setTimeout("createChart2()",100);
		document.getElementById('info_graph').innerHTML="This graph makes a comparison between the total duration and the total pause time of each activity. The horizontal axis display all your activities. The vertical axis displays the time in minutes. Each activity has a blue bar (the total tracked time) and an orange bar (the total pause time).";	


	}
	
	function chart3() {
		document.getElementById("tab1").style.display="none";
		document.getElementById("tab2").style.display="none";
		document.getElementById("tab3").style.display="block";
		document.getElementById("tab4").style.display="none";
		document.getElementById("tab5").style.display="none";
		
		setTimeout("createChart3()",100);
		document.getElementById('info_graph').innerHTML="This graph gives you an idea of your overall progress.";


	}
	
	function chart4() {
		document.getElementById("tab1").style.display="none";
		document.getElementById("tab2").style.display="none";
		document.getElementById("tab3").style.display="none";
		document.getElementById("tab4").style.display="block";
		document.getElementById("tab5").style.display="none";
		
		document.getElementById('info_graph').innerHTML="The horizontal axis displays the activity number. The vertical axis displays the duration of each activity. How bigger the 'bubble', how better your rating was.";	

	}
	
	function chart5() {
		document.getElementById("tab1").style.display="none";
		document.getElementById("tab2").style.display="none";
		document.getElementById("tab3").style.display="none";
		document.getElementById("tab4").style.display="none";
		document.getElementById("tab5").style.display="block";
		
		setTimeout("createChart5()",100);
		document.getElementById('info_graph').innerHTML="This graph displays each course on the horizontal axis, and the duration of each course on the vertical axis. Each activity displays three bars. The blue bar represents college, the orange bar represents practice, and the brown bar is the total study time.";	


	}
	
	</script>
</head>

<body>
	<% if(session.getAttribute("id")==null)
        	{
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
		<u><h5>My Stats</h5></u>
	</div>
	
	<div id="default" align="center">
	
  		<ul class="tab"> 
   			<li class="tabChoice"><a href="#tab1" class="tabLink" onclick="chart1()" >Comparison between my activities</a></li>
    		<li class="tabChoice"><a href="#tab2" class="tabLink" onclick="chart2()">My activities: Duration vs. Pause time</a></li> 
    		<li class="tabChoice"><a href="#tab3" class="tabLink" onclick="chart3()">My overall progress</a></li>
    		<li class="tabChoice"><a href="#tab4" class="tabLink" onclick="chart4()">X vs. Y vs. Z</a></li> 
    		<li class="tabChoice"><a href="#tab5" class="tabLink" onclick="chart5()">My courses: duration of the learning activities</a></li>  
  		</ul>
  		
  		<div id="textBox">	
		<p id="info_graph" value=""> </p>	
		</div>
		
  		<div id="tab1" class="graph" align="center" style="display: none; ">
  			<div class="graph" id="chart1" style="height:600px; width:800px; " > </div>
  		</div> 
 		<div id="tab2" class="graph" align="center" style="display: none; ">
 			<div id="chart2" class="graph" style="height:500px; width:1100px;" ></div> 
 		</div> 
  		<div id="tab3" class="graph" align="center" style="display: none; ">
  			<div id="chart3" style="height:600px; width:800px; " ></div>
  		</div> 
  		<div id="tab4" class="graph" align="center" style="display: none;">
  			<p id="text">
				<select name="activity" id="activityChoice">
					<%{
						int i = 0;
						for(String key : GraphEngine.getInstance().getRateData(user).keySet()) {
							%>
							<option value="<%= i %>"> <%= key %> </option>
							<%
							i++;
						}
					}
					%>
					</select> 
				<input type="submit" class="large button green" value="generate X-Y-Z plot" onclick="createChart4()" />
			 </p>
			<div id="chart4" class="graph" style="height:600px; width:800px;" ></div>
		</div>
  		<div id="tab5" class="graph" align="center" style="display: none; ">
  			<div id="chart5" class="graph" style="height:500px; width:1100px; " ></div>
  		</div>
	</div>
	
	
  	
	
	</div>
	 
	
	
	<script type="text/javascript">		
		function createChart1() {
			

			<% long d1[] = GraphEngine.getInstance().getSeperateActivityDuration(user); %>
			  
			var data = [
					    ['College', <%= d1[0] %>],['Job', <%= d1[1] %>], ['NightLife', <%= d1[2] %>], 
					    ['Practice', <%= d1[3] %>],['Sleep', <%= d1[4] %>], ['Sport', <%= d1[5] %>],
					    ['Study', <%= d1[6] %>]
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
		        		text: 'Comparison between my activities'
		        	}

			    }
			  );
			}
		
	</script>
	<script type="text/javascript">
	function createChart2() {
			<% long d2[][] =  GraphEngine.getInstance().getSeperateActivityVSPause(user);%>
			var schaalfactor = 1000*60;
    		
    		var ad = [<%= d2[0][0]%> / schaalfactor,<%= d2[1][0]%> / schaalfactor,<%= d2[2][0] %>/ schaalfactor,<%= d2[3][0] %>/ schaalfactor,
    				  <%= d2[4][0]%> / schaalfactor,<%= d2[5][0]%> / schaalfactor,<%= d2[6][0] %> / schaalfactor];
    		var pd = [<%= d2[0][1]%> / schaalfactor,<%= d2[1][1]%> / schaalfactor,<%= d2[2][1] %> / schaalfactor,<%= d2[3][1] %>/ schaalfactor,
    				  <%= d2[4][1]%> / schaalfactor,<%= d2[5][1]%> / schaalfactor,<%= d2[6][1] %> / schaalfactor];
    		
        	var activities = ['College', 'Job', 'NightLife', 'Practice','Sleep','Sport', 'Study' ];
     
    	var plot2 = $.jqplot('chart2', [ad, pd], {
          	seriesDefaults:{
            	renderer:$.jqplot.BarRenderer,
            	rendererOptions: {fillToZero: true}
        	},
           	series:[
            	{label:'Activity duration'},
            	{label:'Pause duration'},
        	],
        	legend: {
            	show: true,
            	placement: 'outsideGrid',
        	},
        	axes: {
            	xaxis: {
                	renderer: $.jqplot.CategoryAxisRenderer,
                	ticks: activities
            	},
            	yaxis: {
                	min: 0,
            		label: 'minutes',
                	tickOptions: {},
            		},
        		},
        	title: {
        		text: 'My activities: Duration - Pause time'
        	}
        	
    		});
		}
	
	</script>
		
		
	<script type="text/javascript">
	
	function createChart3() {
   
		         <% Object d3[][] = GraphEngine.getInstance().getSeperateTimeVSStudyPoints(user); %>

		         var st = new Array();
				 var sp = new Array();
				 var courses = new Array();
				 var sf = 1000 * 60 * 60 * 30;

				 
				 <% for(int i = 0; i < d3.length; i++) {
					 %> 
					 courses[<%=i%>] = '<%=d3[i][0]%>' ;
					 st[<%=i%>] = [<%=d3[i][1] %> / sf ,<%=i + 1%>];
					 sp[<%=i%>] = [<%=d3[i][2] %>, <%=i + 1%>];
					 <%
				 }
				 
				 %>
				 
			    var data = [st, sp];
 	    		    
    
    var plot3 = $.jqplot('chart3',  data, {
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
    			text:'My overall progress'
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
	<script type="text/javascript">
		var plot4;
		function createChart4() {
			<% Map<String,long[][]> all = GraphEngine.getInstance().getRateData(user); %>
			
			var allData = new Array();
			var value = document.getElementById("activityChoice").value;
			var title = new Array();
			
			var sf = 1000 * 60;
			
			//verwijderd de vorige plot.
			if(plot4 != null) {
				plot4.destroy();
			}
			
			<%
			{ 
				int i = 0;
				for(String key : all.keySet()) {
					%>
					<%="element: " + i %>
					var data = new Array();
					<%
					for(int j = 0; j < all.get(key).length; j++ ) {
						long[][] temp = all.get(key);
						%>
						data[<%= j %>] = [<%=temp[j][0] %>,<%=temp[j][1] %> / sf,<%=temp[j][2] %>,"<%=key%>"];
						<%
					}
					%>
					allData[<%=i%>] = [data];
					title[<%=i %>] = "<%=key %>" + ": activity number - duration - rating";

					<%
					i++;
				}
			}
			%>
			
			if(allData[value][0].toString() != "") {
			//een array dieper aangemaakt standaard. Geen idee waarom
    		plot4 = $.jqplot('chart4',[ allData[value][0] ],{
        		title: title[value],
        		seriesDefaults:{
            		renderer: $.jqplot.BubbleRenderer,
            		rendererOptions: {
                		bubbleGradients: true
            		},
            		shadow: true
        		},
        		 axes: {
        	            yaxis: {
        	            	label: 'minutes',
        	            	tickInterval: 1,

        	            },
        	            xaxis: {
        	            	tickInterval: 1,
        	            	min: 0
        	            	
        	            }
        	        }
    		});
			}
			else {
				alert("No data available");
			}
		}
	</script>
	
	<script type="text/javascript">
	function createChart5() {
			
			<% 
			Object d5[][] =  GraphEngine.getInstance().getSeperateTimeVSStudyPointsSeperateActivities(user);
			%>
			var sf = 1000*60;
			
    		var college = new Array();
    		var practice = new Array();
    		var study = new Array();
    		var courses = new Array();
    		
    		<%
    		for(int i = 0; i < d5.length; i++) {
    			%>
    			courses.push("<%=d5[i][0]%>");
    			college.push(<%=d5[i][1]%> / sf);
    			practice.push(<%=d5[i][2]%> / sf);
    			study.push(<%=d5[i][3]%> / sf);
    			<%
    		}
    		%>
    		    		
    	
     
    	var plot5 = $.jqplot('chart5', [college, practice, study], {
        	// The "seriesDefaults" option is an options object that will
        	// be applied to all series in the chart.
        	seriesDefaults:{
            	renderer:$.jqplot.BarRenderer,
            	rendererOptions: {fillToZero: true}
        	},
        	// Custom labels for the series are specified with the "label"
        	// option on the series option.  Here a series option object
        	// is specified for each series.
        	series:[
            	{label:'total duration college'},
            	{label:'total duration practice'},
            	{label:'total duration study'}
        	],
        	// Show the legend and put it outside the grid, but inside the
        	// plot container, shrinking the grid to accomodate the legend.
        	// A value of "outside" would not shrink the grid and allow
        	// the legend to overflow the container.
        	legend: {
            	show: true,
            	placement: 'outsideGrid',
        	},
        	axes: {
            	// Use a category axis on the x axis and use our custom ticks.
            	xaxis: {
                	renderer: $.jqplot.CategoryAxisRenderer,
                	ticks: courses
            	},
            	// Pad the y axis just a little so bars can get close to, but
            	// not touch, the grid boundaries.  1.2 is the default padding.
            	yaxis: {
                	min: 0,
            		label: 'minutes',
                	tickOptions: {},
            		},
        		},
        	title: {
        		text: 'My courses: duration of the learning activities'
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
