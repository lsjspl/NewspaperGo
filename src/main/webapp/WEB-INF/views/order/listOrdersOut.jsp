<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
	
	$("#addOrdersOut").click(function(){
		
		$.ajax({
			url:"../order/totalJson",
			type : "post",
			dataType : 'json',
			success:function(data,status){
				var select=$("#ingredientOut").empty();
				select.append(
						'<thead>'+
						'<tr>'+
						   '<th>类型</th>'+
						   '<th>名称</th>'+
						   '<th>价格</th>'+
						   '<th>数量</th>'+
						   '<th>出库</th>'+
						'</tr>'+
						'</thead>'
				);
				$.each(data,function(i,value){
					select.append(
							'<tr>'+
							   '<td>'+this.ingredientId.ingredientType.name+'</td>'+
							   '<td>'+this.ingredientId.name+'</td>'+
							   '<td>'+this.ingredientId.price+"元/"+this.ingredientId.unit+'</td>'+
							   '<td>'+this.amount +'</td>'+
							   '<td class="col-sm-4">'+
							  		'<div class="input-group">'+
						   			'<input type="hidden" name="ingredientId" value="'+this.ingredientId.id+'">'+
							   			'<input min="0" max="'+this.amount+'" value="0" type="number" name="amount" class="ingredientInfo form-control">'+
							  			 '<div class="input-group-addon">'+this.ingredientId.unit+'</div>'+
							  		'</div>'+
							  	'</td>'+
							'</tr>'
							
					);
					
				});
			}
		});
		
	});
	
});
	
</script>
</head>
<body>

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">订单管理页面</div>
  <div class="panel-body">
 
	<shiro:hasPermission name="order:addOrders">
	<div>
		<!-- Button trigger modal -->
		<button id="addOrdersOut" class="btn btn-primary btn" data-toggle="modal"
			data-target="#addOut">出库</button>
	</div>
	</shiro:hasPermission> 
	</div>
	<div class="table-responsive">
		<table class="table table-hover">
			<thead>
				<tr>
				<th>#</th>
				<th>状态</th>
	            <th>商品信息</th>
<!-- 	            <th>创建时间</th>
	            <th>购买信息</th>
	            <th>收货信息</th> -->
	            <th>备注</th>
	            <th>操作</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${list}" var="list">
					<tr>
					  	<td>${list.id}</td>
			            <td>
						<div><span class="label label-info">创建订单</span></div>
						<div>时间：${list.createtime}</div>
			            <c:if test="${list.status gt 0}">
							<div><span class="label label-info">${list.buyyer.name}下单完成</span></div>
							<div>时间：${list.buttime}</div>
			            </c:if>
			            <c:if test="${list.status gt 1}">
							<div><span class="label label-info">${list.manager.name}发货完成</span></div>
							<div>时间：${list.intime}</div>
			            </c:if>
			            <td>
				            <ul class="list-group">
					           	 <c:forEach items="${list.detail}" var="detail">
						           	 <li class="list-group-item list-group-item-info">
						           	 	<div>${ detail.ingredientId.name}${ -detail.amount}${ detail.ingredientId.unit} </div>
						           	 	<div>单价${detail.ingredientId.price}总价<fmt:formatNumber value="${ detail.ingredientId.price*(-detail.amount)}" pattern="0.00"/></div>
						           	 </li>
					           	 </c:forEach>
				           	</ul>
			            </td>
		<%-- 				<td>${list.createtime}</td>
						<td>
							<div><span class="label label-info">${list.buyyer.name}</span></div>
							<div><span class="label label-default">${list.buttime}</span></div>
						</td>
						<td>
							<div><span class="label label-info">${list.manager.name}</span></div>
							<div><span class="label label-default">${list.intime}</span></div>
						</td> --%>
						<td>${list.remarks}</td>
						<td>
							<div class="list-group">
								<shiro:hasPermission name="order:delOrders">
								<a href="${ctx}/order/delOrders/${list.id}"  class="list-group-item list-group-item-danger"> 
									<span class="glyphicon glyphicon-trash"> 删除</span> 
								</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:buyOrders">
				            	<a  href="${ctx}/order/buyOrders/${list.id}"  class="list-group-item list-group-item-primary">
									<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"> 下单</span>			           		
								</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:receiveOrders">
								<a  href="${ctx}/order/receiveOrders/${list.id}"  class="list-group-item list-group-item-primary">
									<span class="glyphicon glyphicon-saved" aria-hidden="true"> 发货</span>		           		
								</a>	
								</shiro:hasPermission>						
							</div>
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</div>


	<!-- Modal -->
	<div class="modal fade" id="addOut" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加</h4>
				</div>
				   <form id="addForm" role="form" action="${ctx}/order/addOrdersOut" method="post">
					<div class="modal-body" >
						<!-- 弹框内容  -->
						<div class="form-group">
						   <label for="ingredient">商品</label>
						   <div class="table-responsive">
						   		<table class="table table-hover info table-condensed" id="ingredientOut" >
						   		</table>
						   </div>
						</div>
						<div class="form-group">
						   <label for="remarks">备注</label>
						   <input type="text" class="form-control" name="remarks" id="remarks" placeholder="">
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
