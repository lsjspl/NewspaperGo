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
	
	$(".del,.redo").click(function(){
		return confirm("是否要这样做？考虑一下不了？")
	});
	
	$("#onePaper").click(function(){
		$("#onePaperBtn").button('toggle');
		console.log($("#onePaper .active").get(0));
		$(".type0").prop("checked",$("#onePaper .active").get(0)?false:"checked");
	});
	$("#twoPaper").click(function(){
		$("#twoPaperBtn").button('toggle');
		$(".type1").prop("checked",$("#twoPaper .active").get(0)?false:"checked");
	});
});

</script>
</head>
<body>

	<div class="panel panel-default">
		<!-- Default panel contents -->
		
		<div class="panel-heading">基础信息维护</div>
		<div class="panel-body">
				
				<form  class="form-horizontal" action="${ctx}/catcher/addTask" method="post">
				
					<div class="form-group">
				      <label for="name" class="col-lg-2 control-label">任务名称</label>
				      <div class="col-lg-10">
				        <input type="text" class="form-control" name="name" id="name" placeholder="任务名称">
				      </div>
				    </div>
				
					<div class="form-group">
				      <label for="startDate" class="col-lg-2 control-label">开始时间</label>
				      <div class="col-lg-10">
				        <input type="text" class="form-control" name="startDate" id="startDate" placeholder="开始时间">
				      </div>
				    </div>
		
					<div class="form-group">
				      <label for="endDate" class="col-lg-2 control-label">结束时间</label>
				      <div class="col-lg-10">
				        <input type="text" class="form-control" name="endDate" id="endDate" placeholder="结束时间">
				      </div>
				    </div>
				    		    		
				    		    		
				    <div class="form-group">
				      <label for="inputPassword" class="col-lg-2 control-label">报纸名称</label>
				      <div class="col-lg-10">
				      <div class="btn-group" data-toggle="buttons">
						  <label class="btn btn-info active" id="onePaper" >
						    <input type="checkbox"  id="onePaperBtn"  autocomplete="off" checked> 地方媒体
						  </label>
						  <label class="btn btn-info active" id="twoPaper">
						    <input type="checkbox"   id="twoPaperBtn" autocomplete="off" checked> 党媒
						  </label>
						</div>
						<div class="checkbox">
							<c:forEach items="${urlsInfoList}" var="urlsInfo">
						
				         	 <label>
				          	  <input type="checkbox" checked="checked" class="type${urlsInfo.type}" name="urlsInfoIds" value="${urlsInfo.id}">${urlsInfo.name}
				          	  </label>
							</c:forEach>
						</div>
				      </div>
				    </div>		    		    		
		
					<div class="form-group">
				      <label for="submit" class="col-lg-2 control-label"></label>
				      <div class="col-lg-10">
				        <button type="submit" >增加爬虫任务</button>
				      </div>
				    </div>
					
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
									<c:when test="${list.state ==0}">等待爬取</c:when>
									<c:when test="${list.state ==1}">爬取中</c:when>
									<c:when test="${list.state ==2}">爬取完成</c:when>
									<c:when test="${list.state ==3}">任务终止</c:when>
								</c:choose>
								<div>
								原因：${list.remarks}
								</div>
							</td>
							<td>${list.creatTime}</td>
							<td>
								<div class="list-group">

									<c:if test="${list.state !=1}">
									<a href="${ctx}/catcher/deleteTask/${list.id}" class="list-group-item list-group-item-danger del">
										 <span class="glyphicon glyphicon-trash"> 删除</span>
									</a>
									</c:if>
									<c:if test="${list.state > 1}">	
									<a href="${ctx}/catcher/redoTask/${list.id}" class="list-group-item list-group-item-danger redo">
										 <span class="glyphicon glyphicon-retweet"> 重新爬取</span>
									</a>
									</c:if>
									<c:if test="${list.state !=0}">	
									<a href="${ctx}/catcher/taskLog/2/${list.id}" class="list-group-item list-group-item-primary"> 
										<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"> 成功日志</span>
									</a>
									<a href="${ctx}/catcher/taskLog/3/${list.id}" class="list-group-item list-group-item-primary"> 
										<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"> 失败日志</span>
									</a>
									</c:if>
									<c:if test="${list.state ==2}">
									 <a href="${ctx}/catcher/total/${list.id}" class="list-group-item list-group-item-primary"> 
										<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"> 统计</span>
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
