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
			<a href="catcher/work">开始爬取</a> <a href="catcher/work">爬取选中</a>
		</div>
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>标题</th>
						<th>内容</th>
						<th>时间</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list.id}</td>

							<td>${list.title}</td>
							<td>
							文章内容：<textarea cols="60" rows="20">${list.content}</textarea>
							<br/>
							原始信息：<textarea cols="60" rows="20">${list.baseInfo}</textarea> 
							</td>
							<td>${list.time}</td>
							<td>
							</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
