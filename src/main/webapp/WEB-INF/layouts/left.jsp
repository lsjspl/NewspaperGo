<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
/*定义二级菜单样式*/
.secondmenu {
	padding-top: 10px;
	padding-bottom: 1px;
	padding-left: 20%;
	text-align: left;
	background-color: #F3F3F3;
}

.secondmenu a {
	padding-top: 10px;
	padding-bottom: 10px;
	font-size: 14px;
	color: #21B6AE;
}
</style>
<div class="list-group" >
	<div class="list-group-item list-group-item-info">
		<a href="${ctx}/user/info" style="color:#ffffff;"><span class="glyphicon glyphicon-home"></span> 首    页</a>
	</div>
	<div class="list-group-item" data-toggle="collapse" data-target="#systemMenu"><span class="glyphicon glyphicon-cog"></span> 系统管理</div>
	<div id="systemMenu" class="collapse secondmenu">
		<ul class="list-unstyled">
			<shiro:hasPermission name="user:list">
				<li><a href="${ctx}/user/list"><span class="glyphicon glyphicon-user"></span> 用户管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:list">
				<li><a href="${ctx}/role/list"><span class="glyphicon glyphicon-link"></span> 角色管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="permission:list">
				<li><a href="${ctx}/permission/list"><span class="glyphicon glyphicon-tag"></span> 权限管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="record:list">
				<li><a href="${ctx}/record/list"><span class="glyphicon glyphicon-home"></span> 审查管理</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	 <div class="list-group-item" data-toggle="collapse" data-target="#catcherMenu"><span class="glyphicon glyphicon-leaf"></span> 爬虫管理</div>
	 <div id="catcherMenu" class="collapse secondmenu">
		<ul class="list-unstyled">
			<li><a href="${ctx}/catcher/listUrlsInfo"><span class="glyphicon glyphicon-user"></span> 基础信息</a></li>
			<li><a href="${ctx}/catcher/addUrlsInfo"><span class="glyphicon glyphicon-link"></span> 增加基础信息</a></li>
			<li><a href="${ctx}/permission/listCatcher"><span class="glyphicon glyphicon-tag"></span> 爬取统计</a></li>
		</ul>
	</div>
	
	<div class="list-group-item" data-toggle="collapse" data-target="#orderMenu"><span class="glyphicon glyphicon-leaf"></span> 订单管理</div>
	<div id="orderMenu" class="collapse secondmenu">
		<ul class="list-unstyled right">
			<shiro:hasPermission name="order:listOrders">
				<li><a href="${ctx}/order/listOrders"><span class="glyphicon glyphicon-list"></span> 采购订单列表</a></li>
			</shiro:hasPermission>
			<li><a href="${ctx}/order/listOrdersOut"><span class="glyphicon glyphicon-list"></span> 出库订单列表</a></li>
			<li><a href="${ctx}/order/total"><span class="glyphicon glyphicon-list"></span> 统计</a></li>
<%-- 			<shiro:hasPermission name="order:addOrders">
				<li><a href="#" class="addOrders" data-toggle="modal"
					data-target="#myModal"><span class="glyphicon glyphicon-plus"></span> 添加订单</a></li>
			</shiro:hasPermission> --%>
		</ul>
	</div>
	<div class="list-group-item" data-toggle="collapse"
		data-target="#ingredientMenu"><span class="glyphicon glyphicon-send"></span> 产品管理</div>
	<div id="ingredientMenu" class="collapse secondmenu">
		<ul class="list-unstyled right">
			<shiro:hasPermission name="order:listIngredientType">
				<li><a href="${ctx}/order/listIngredientType"><span class="glyphicon glyphicon-barcode"></span> 商品类型列表</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="order:listSupplier">
				<li><a href="${ctx}/order/listSupplier"><span class="glyphicon glyphicon-road"></span> 供应商列表</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="order:listIngredient">
				<li><a href="${ctx}/order/listIngredient"><span class="glyphicon glyphicon-th"></span> 产品列表</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<div class="list-group-item"><span class="glyphicon glyphicon-list-alt"></span> 系统信息</div>
</div>


