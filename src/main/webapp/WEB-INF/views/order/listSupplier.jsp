<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<shiro:hasPermission name="order:addSupplier">
	<div>
		<!-- Button trigger modal -->
		<button id="add" class="btn btn-primary btn" data-toggle="modal"
			data-target="#myModal">添加</button>
	</div>
	</shiro:hasPermission>
	<hr/>
	<div class="table-responsive">
		<table class="table table-hover center">
			<thead>
				<tr>
					<th>#</th>
					<th>名称</th>
					<th>地址</th>
					<th>电话</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${list}" var="list">
					<tr>
						<td>${list.id}</td>
						<td>${list.name}</td>
						<td>${list.address}</td>
						<td>${list.number}</td>
						<td>
						<shiro:hasPermission name="order:delSupplier">
						<a   class="btn btn-danger"  href="${ctx}/order/delSupplier/${list.id}"> 
							<span class="glyphicon glyphicon-trash">删除 </span>
						</a> 
						</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加</h4>
				</div>
				<form id="addForm" role="form" action="${ctx}/order/addSupplier"
					method="post">
					<div class="modal-body">
						<!-- 弹框内容  -->
						<div class="form-group">
							<label for="name">名称</label> 
							<input type="text" name="name" class="form-control" id="name" placeholder="请输入名称">
						</div>
						<div class="form-group">
							<label for="address">地址</label> 
							<input type="text" name="address" class="form-control" id="address" placeholder="请输入地址">
						</div>
						<div class="form-group">
							<label for="number">电话</label> 
							<input type="text" name="number" class="form-control" id="number" placeholder="请输入电话">
						</div>
						<!-- 弹框内容  -->
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button id="enter" type="submit" class="btn btn-info pull-right">添加</button>
					</div>

				</form>
			</div>
		</div>
	</div>

</body>
</html>
