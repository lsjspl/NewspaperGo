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

$(document).ready(function(){
	
	$("#add").click(function(){
		
		$.ajax({
			url:"../order/listIngredientTypeJson",
			type : "post",
			data : "",
			dataType : 'json',
			success:function(data,status){
				var select=$("#ingredientType").empty();
				$.each(data,function(){
					select.append('<option value="'+this.id+'">'+this.name+'</option>');
				});
			}
		});
		
	});
	
});
	
</script>
</head>
<body>
	<shiro:hasPermission name="order:addIngredient">
	<div>
		<!-- Button trigger modal -->
		<button id="add" class="btn btn-primary btn" data-toggle="modal"
			data-target="#myModal">添加</button>
	</div>
	</shiro:hasPermission>
	<hr>
	<div class="table-responsive">
		<table class="table table-hover center">
			<thead>
				<tr>
				<th>#</th>
	            <th>名称</th>
	            <th>类型</th>
	            <th>价格</th>
	            <th>单位</th>
	            <th>修改时间</th>
	            <th>操作</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${list}" var="list">
					<tr>
					  	<td>${list.id}</td>
			            <td>${list.name}</td>
			             <td>${list.ingredientType.name}</td>
			            <td>${list.price}</td>
			             <td>${list.unit}</td>
			            <td>${list.changeTime}</td>
			            
						<td>
						<shiro:hasPermission name="order:delIngredient">
						<a class="btn btn-danger" href="${ctx}/order/delIngredient/${list.id}"> <span class="glyphicon glyphicon-trash"></span>删除  </a> 
						</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加</h4>
				</div>
				   <form id="addForm" role="form" action="${ctx}/order/addIngredient" method="post">
					<div class="modal-body" >
						<!-- 弹框内容  -->
						<div class="form-group form-horizontal">
						   <label for="name">名称</label>
						   <input type="text" class="form-control" name="name" id="name" placeholder="">
						</div>
						<div class="form-group">
						   <label for="ingredientType">类型</label>
							<select class="form-control" name="ingredientType.id"  id="ingredientType" >
							</select>
						</div>
						<div class="form-group">
						   <label for="price">价格</label>
						   <input type="number" class="form-control" step="0.1" name="price" id="price" placeholder="">
						</div>
						<div class="form-group">
						   <label for="unit">单位</label>
						   <input type="text" class="form-control" name="unit" id="unit" placeholder="">
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
