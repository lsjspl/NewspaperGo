<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<link rel="stylesheet"  href="${ctx}/resources/ui/js/time/datedropper.css">
<script src="${ctx}/resources/ui/js/time/datedropper.min.js"></script>
<title>Newspaper Go</title>

<script>
/* var webSocket = new WebSocket('ws://'+window.location.host+'${ctx}/websocket/websocket');

webSocket.onerror = function(event) {
	 alert(event.data);
};

webSocket.onopen = function(event) {
	document.getElementById('messages').innerHTML = 'Connection established';
};

webSocket.onmessage = function(event) {
	  document.getElementById('messages').innerHTML += '<br />' + event.data;
};


function start() {
  webSocket.send('hello');
  return false;
}

function close() {
	  webSocket.close();
}
 */
$(function(){
		$("#testCatcher").click(function(){
			$.ajax({
				url:"../testCatcher?id="+$("#id").val(),
				type : "post",
				data : "",
				dataType : 'json',
				success:function(data,status){
					$("#testResult").html(JSON.stringify(data));
				}
			});
		});
		
	$("#startDate").dateDropper({
		animate: true,
		format: 'Y-m-d',
		maxYear: '2020'
	});
	
	$("#endDate").dateDropper({
		animate: true,
		format: 'Y-m-d',
		maxYear: '2020'
	});
	

});

</script>
</head>
<body>

	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">基础信息维护</div>
		<div class="panel-body">
		  <div>
  </div>
  <div id="messages"></div>
		<form action="addTask" method="post">
				任务名称<input type="text"  name="name">  
		
			从 <input type="text" class="input" name="startDate" id="startDate"> 到
			<input type="text" class="input" name="endDate" id="endDate">
				<button type="submit" >增加爬虫任务</button>
		</form>
		</div>
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>任务名称</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list.id}</td>
							<td>${list.name}</td>
							<td>${list.startDate}</td>
							<td>${list.endDate}</td>
							<td>
								<c:choose>
									<c:when test="${list.state ==0}">爬取中</c:when>
									<c:when test="${list.state ==1}">爬取完成</c:when>
									<c:when test="${list.state ==2}">任务终止</c:when>
								</c:choose>
								
							</td>
							<td>${list.creatTime}</td>
							<td>
								<div class="list-group">
									<a href="#" class="list-group-item list-group-item-danger">
										 <span class="glyphicon glyphicon-trash"> 停止</span>
									</a>
									<c:if test="${list.state ==1}">
									 <a href="${ctx}/catcher/total/${list.id}" class="list-group-item list-group-item-primary"> 
										<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"> 统计信息</span>
									</a>
									</c:if>
								</div>
							</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
