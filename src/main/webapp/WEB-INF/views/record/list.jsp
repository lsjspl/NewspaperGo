<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>主页</title>
</head>
<body>
	<div class="table-responsive col-md-offset-1 col-sm-10 ">
		<table class="table table-hover success center">
			<thead>
				<tr>
					<th>#</th>
					<th>用户名称</th>
					<th>执行操作</th>
					<th>执行时间</th>
					<th>IP</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${list}" var="list">
					<tr class="table-bordered">
						<td class="table-bordered id">${list.id}</td>
						<td class="table-bordered userName">${list.userName}</td>
							<td class="table-bordered method">${list.method}</td>
								<td class="table-bordered recordTime">${list.recordTime}</td>
								<td class="table-bordered ip">${list.ip}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</body>
</html>
