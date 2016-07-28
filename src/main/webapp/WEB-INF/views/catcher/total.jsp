<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no -cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<script src="${ctx}/resources/ui/js/exportTable/tableExport.js"></script>
<script src="${ctx}/resources/ui/js/exportTable/jquery.base64.js"></script>
<title>Newspaper Go</title>

<script type="text/javascript">

var getDetail;

$(function(){
	
	getDetail=function(catcherId){
		$.ajax({
			url:"${ctx}/catcher/catcherDetail",
			type : "post",
			data : {id:catcherId},
			dataType : 'json',
			success:function(data,status){
				$("#titleDetail").html(data.title);
				$("#contentDetail").html(data.content);
				$("#detailInfo").show();
			}
		});
	}
	
	$(".time").each(function(){
		var day=new Date($(this).text()).getDay();
		var week = "周" + "日一二三四五六".split("")[day];
		$(this).append('<code>'+week+'</code>');
		if(day==0 || day==6){
		$(this).siblings(".displayOtherDay").remove();	
		}else{
		 $(this).siblings(".displayWeek").remove();
		}
	});
	
	$("#totalPanel").click(function(){
		$("#detailInfo").hide();
	});
	
});

</script>
</head>
<body>

		<div style="position: fixed; width: 400px; height: 600px; display: none; right: 10px; top: 80px; background-color: #fff; z-index: 1000;" 
		id="detailInfo">
			<div class="panel panel-default">
				<div class="panel-heading"  id="titleDetail">
				</div>
				<div class="panel-body"  id="contentDetail" style="overflow:auto;height: 500px;">
				</div>
			</div>
		</div>
	<div class="panel panel-default"  id="totalPanel">
		<!-- Default panel contents -->
		<div class="panel-heading">信息统计  </div>
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>媒体名称</th>
						<th>关键词</th>
						<th>报道数量</th>
						<th>报道天数</th>
						<th>周末数量</th>
						<th>报道详情</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="list"  varStatus="point">
						<tr id="${point.index}">
							<td>${list.name}</td>
							<td>${list.keyWord}</td>
							<td>${list.count}</td>
							<td>${list.day}</td>	
							<td>${list.weekCount}</td>	
							<td width="600px">
							<c:forEach items="${list.url}" var="url" varStatus="vs">
								<div>
									<span class="label label-primary">${vs.index+1}</span>&nbsp;&nbsp;
									<span class="title" onclick="getDetail(${list.id[vs.index]})">${list.title[vs.index]}</span>
								</div>
								<div>地址：<span class="url"><a href="${url}" target="_blank">${url}</a></span></div>
							 	<div>时间：
								 	<span class="time">
								 	<fmt:formatDate value="${list.time[vs.index]}" type="date" pattern="yyyy-MM-dd "/>
								 	</span>
							 		<c:choose>
											<c:when test="${list.state[vs.index]==1}"><span class="label label-danger">已屏蔽</span></c:when>
											<c:when test="${list.state[vs.index]==2}"><span class="label label-warning">非周末</span></c:when>
											<c:when test="${list.state[vs.index]==3}"><span class="label label-info">周末</span></c:when>
									</c:choose>
							 		
							 		<c:if test="${list.state[vs.index]!=0}">
							 		<a href="${ctx}/catcher/updateCatcherState/${taskId}/${list.id[vs.index]}/0/${point.index}">
							 			<span class="glyphicon glyphicon-eye-open" aria-hidden="true" title="重置"></span>
									</a>
									</c:if>
							 		<c:if test="${list.state[vs.index]!=1}">
								 		<a href="${ctx}/catcher/updateCatcherState/${taskId}/${list.id[vs.index]}/1/${point.index}">
								 			<span class="glyphicon glyphicon-eye-close" aria-hidden="true" title="屏蔽"></span>
										</a>
									</c:if>
									<c:if test="${list.state[vs.index]==0}">
										<a href="${ctx}/catcher/updateCatcherState/${taskId}/${list.id[vs.index]}/2/${point.index}" class="displayWeek">
								 			<span class="glyphicon glyphicon-retweet" aria-hidden="true" title="非周末"></span>
										</a>
									</c:if>
									<c:if test="${list.state[vs.index]==0}">
										<a href="${ctx}/catcher/updateCatcherState/${taskId}/${list.id[vs.index]}/3/${point.index}" class="displayOtherDay">
								 			<span class="glyphicon glyphicon-retweet" aria-hidden="true" title="周末"></span>
										</a>
									</c:if>
							 	</div>
							</c:forEach>
							</td>						
						</tr>
					</c:forEach>

				</tbody>
			</table>
			</div>
	</div>

</body>
</html>
