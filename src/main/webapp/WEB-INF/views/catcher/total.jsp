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

<title>Newspaper Go</title>

<script type="text/javascript">
$(function(){
	
	$(".time").each(function(){
		var week = "周" + "日一二三四五六".split("")[new Date($(this).text()).getDay()];
		$(this).append('<code>'+week+'</code>');
	});
});
</script>
</head>
<body>

	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">信息统计</div>
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

					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list.name}</td>
							<td>${list.keyWord}</td>
							<td>${list.count}</td>
							<td>${list.day}</td>	
							<td>${list.weekCount}</td>	
							<td>
							<c:forEach items="${list.url}" var="url" varStatus="vs">
								<div>标题：<span class="title">${title}</span></div>
								<div>地址：<span class="url"><a href="${url}" target="view_window">${url}</a></span></div>
							 	<div>时间：<span class="time"><fmt:formatDate value="${list.time[vs.index]}" type="date" pattern="yyyy-MM-dd "/></span>
							 		<span><a href="${ctx}/catcher/deleteCatcher/${list.id[vs.index]}">
										 <span class="glyphicon glyphicon-trash"></span>
									</a></span>
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
