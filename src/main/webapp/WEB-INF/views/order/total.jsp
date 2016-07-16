<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
	<hr>
	<div class="table-responsive">
		<table class="table table-hover center">
			<thead>
				<tr>
	            <th>名称</th>
	            <th>类型</th>
	            <th>价格</th>
	            <th>单位</th>
	            <th>数量</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${list}" var="list">
					<tr>
			            <td>${list.ingredientId.name}</td>
			             <td>${list.ingredientId.ingredientType.name}</td>
			            <td>${list.ingredientId.price}</td>
			             <td>${list.ingredientId.unit}</td>
			            <td>${list.amount}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>

</body>
</html>
