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
	
	<div class="list-group-item" data-toggle="collapse" data-target="#catcherMenu"><span class="glyphicon glyphicon-leaf"></span> 爬虫管理</div>
	 <div id="catcherMenu" class="collapse secondmenu">
		<ul class="list-unstyled">
			<li><a href="${ctx}/catcher/listUrlsInfo"><span class="glyphicon glyphicon-user"></span> 基础信息</a></li>
			<li><a href="${ctx}/catcher/addUrlsInfo"><span class="glyphicon glyphicon-link"></span> 增加基础信息</a></li>
			<li><a href="${ctx}/catcher/listCatcher"><span class="glyphicon glyphicon-tag"></span> 爬取详单</a></li>
			<li><a href="${ctx}/catcher/total"><span class="glyphicon glyphicon-tag"></span> 统计数据</a></li>
		</ul>
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
	

	
	<div class="list-group-item"><span class="glyphicon glyphicon-list-alt"></span> 系统信息</div>
</div>


