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
		<form action="${ctx}/catcher/addTask" method="post">
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
						<th>信息</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>匹配参数</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list.id}</td>
							<td>
								<div>媒体名称:${list.name}</div>
								<div>关键词 :${list.keyWord}</div>
								<div>媒体类型 :
								<c:choose>
									<c:when test="${list.type ==0}">地方媒体</c:when>
									<c:when test="${list.type ==1}">中央媒体</c:when>
								</c:choose>
								</div>
								地址:<div style="width: 15px">${list.urls}</div>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.state ==0}">爬取</c:when>
									<c:when test="${list.state ==1}">不爬取</c:when>
								</c:choose>
								
							</td>
							<td>${list.creatTime}</td>
							 <td>
							 		<div>标题：${list.titlePattern}</div>
							 		<div>内容：${list.contentPattern}</div>
							 		<div>首页：${list.areaPattern}</div>
							 </td>
							<td>
								<div class="list-group">
									<a href="${ctx}/order/delOrders/${list.id}" class="list-group-item list-group-item-danger">
										 <span class="glyphicon glyphicon-trash"> 删除</span>
									</a> <a href="${ctx}/catcher/updateUrlsInfo/${list.id}" target="view_window" class="list-group-item list-group-item-primary"> 
										<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"> 编辑</span>
									</a>
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
