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

<title>主页</title>

<script type="text/javascript">
	
</script>
</head>
<body>

	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">基础信息维护</div>
		<div class="panel-body">

			<shiro:hasPermission name="order:addOrders">
				<div>
					<!-- Button trigger modal -->
					<button id="addOrders" class="btn btn-primary btn"
						data-toggle="modal" data-target="#myModal">添加</button>
				</div>
			</shiro:hasPermission>
		</div>
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>网址</th>
						<th>筛选名称</th>
						<th>媒体类型</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list.id}</td>

							<td>${list.urls}</td>
							<td>${list.name}</td>
							<td><c:choose>
									<c:when test="${list.type ==0}">地方媒体</c:when>
									<c:when test="${list.type ==1}">中央媒体</c:when>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${list.state ==0}">爬取</c:when>
									<c:when test="${list.state ==1}">不爬取</c:when>
								</c:choose></td>
							<td>${list.careatTime}</td>
							<td>
								<div class="list-group">
									<a href="${ctx}/order/delOrders/${list.id}" class="list-group-item list-group-item-danger">
										 <span class="glyphicon glyphicon-trash"> 删除</span>
									</a> <a href="${ctx}/catcher/updateUrlsInfo/${list.id}" class="list-group-item list-group-item-primary"> 
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
