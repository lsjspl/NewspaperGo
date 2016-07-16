<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
 <script src="${ctx}/resources/ui/js/Chart.js"></script> 
<title>主页</title>

<script type="text/javascript">
	$(document).ready(function(){
		var data = {
			    labels: ["January", "February", "March", "April", "May", "June", "July"],
			    datasets: [
			        {
			            label: "My First dataset",
			            fill: false,
			            lineTension: 0.1,
			            backgroundColor: "rgba(75,192,192,0.4)",
			            borderColor: "rgba(75,192,192,1)",
			            borderCapStyle: 'butt',
			            borderDash: [],
			            borderDashOffset: 0.0,
			            borderJoinStyle: 'miter',
			            pointBorderColor: "rgba(75,192,192,1)",
			            pointBackgroundColor: "#fff",
			            pointBorderWidth: 1,
			            pointHoverRadius: 5,
			            pointHoverBackgroundColor: "rgba(75,192,192,1)",
			            pointHoverBorderColor: "rgba(220,220,220,1)",
			            pointHoverBorderWidth: 2,
			            pointRadius: 1,
			            pointHitRadius: 10,
			            data: [65, 59, 80, 81, 56, 55, 40],
			        }
			    ]
			};
		//This will get the first returned node in the jQuery collection.
		 new Chart($("#myChart").get(0).getContext("2d"), {
		    type: 'line',
		    data: data,
		    options: {
		        xAxes: [{
		            display: false
		        }]
		    }
		});
		
		
		var data = {
			    datasets: [{
			        data: [
			            11,
			            16,
			            7,
			            3,
			            14
			        ],
			        backgroundColor: [
			            "#FF6384",
			            "#4BC0C0",
			            "#FFCE56",
			            "#E7E9ED",
			            "#36A2EB"
			        ],
			        label: 'My dataset' // for legend
			    }],
			    labels: [
			        "Red",
			        "Green",
			        "Yellow",
			        "Grey",
			        "Blue"
			    ]
			};
		
		new Chart($("#myChart2").get(0).getContext("2d"), {
		    data: data,
		    type: "polarArea",
		    options: {
		        elements: {
		            arc: {
		                borderColor: "#000000"
		            }
		        }
		    }
		});
	});
</script>
</head>
<body>
<canvas id="myChart" height="100"></canvas>
<canvas id="myChart2" height="100"></canvas>
<%-- <div class="row">
	  <div class="col-md-4">
	  <span class="glyphicon glyphicon-leaf info" aria-hidden="true" style="font-size: 300px;color: #000000;"></span>
	  <img class="img-thumbnail" src="${ctx}/resources/img/400${sessionScope.LOGINED.headImg}">
	  </div>
	  <div class="col-md-6 col-md-offset-1">
	  		<div class="row"><h3 class="text-warning">用户名：${sessionScope.LOGINED.name}</h3></div>
			<div class="row"><h3 class="text-warning">昵称：${sessionScope.LOGINED.nickName}</h3></div>
			<div class="row"><h3 class="text-warning">性别：${sessionScope.LOGINED.sex}</h3></div>
			<div class="row"><h3 class="text-warning">邮箱：${sessionScope.LOGINED.email}</h3></div>
	 </div>
</div> --%>

</body>
</html>
