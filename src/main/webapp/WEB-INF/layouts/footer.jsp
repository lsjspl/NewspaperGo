<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="footer">
	<!-- 导航条 begin -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- 导航条居中 -->
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header active">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
<%-- 				<a class="navbar-brand active" href="#"> 
				<img class="img-thumbnail" src="${ctx}/resources/img/logo.png">
			<span class="glyphicon glyphicon-fire" aria-hidden="true" style="font-size: 50px;"></span>
				</a> --%>
				 <a class="navbar-brand active" href="${ctx}/user/info">Newspaper Go</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1" >
				<ul class="nav navbar-nav">
					<li>&nbsp;</li>
					<!-- 把手机的导航栏菜单撑开 -->
					<li>&nbsp;</li>
					<!-- 把手机的导航栏菜单撑开 -->
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;管理
							<b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
						<ul class="dropdown-menu">
							<shiro:guest>
								<li class="small"><a href="${ctx}/login/list">登录</a></li>
							</shiro:guest>
							<shiro:authenticated>
								<li class="center"><a href="${ctx}/user/info">
									<img class="img-thumbnail" src="${ctx}/resources/img/${sessionScope.LOGINED.headImg}" /></a>
								</li>
								<li class="small"><a href="#">尊敬的${sessionScope.LOGINED.nickName}</a></li>
								<li class="small"><a href="#">IP:${sessionScope.LOGINED.ip}</a></li>
								<li class="divider"></li>
								<li class="small">
									<a href="${ctx}/login/exit"><span class="glyphicon glyphicon-off"></span> 退出 </a>
								</li>
							</shiro:authenticated>
						</ul></li>
				</ul>

				<shiro:authenticated>

					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
							<span class="glyphicon glyphicon-cog"> </span>&nbsp;&nbsp;管理员工具 
								<b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;
							</a>
							<ul class="dropdown-menu">
								<shiro:hasPermission name="user:list">
									<li><a href="${ctx}/user/list">用户管理</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="role:list">
								<li><a href="${ctx}/role/list">角色管理</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="permission:list">
									<li><a href="${ctx}/permission/list">权限管理</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="record:list">
									<li><a href="${ctx}/record/list">审查管理</a></li>
								</shiro:hasPermission>
							</ul></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <span class="glyphicon glyphicon-cog">
							</span>&nbsp;&nbsp;订单管理 <b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;
						</a>
							<ul class="dropdown-menu">
								<shiro:hasPermission name="order:listOrders">
								<li><a href="${ctx}/order/listOrders">订单列表</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:listIngredientType">
								<li><a href="${ctx}/order/listIngredientType">商品类型列表</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:listSupplier">
								<li><a href="${ctx}/order/listSupplier">供应商列表</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:listIngredient">
								<li><a href="${ctx}/order/listIngredient">产品列表</a></li>
								</shiro:hasPermission>
							</ul></li>
					</ul>

					<%-- <ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<span class="glyphicon glyphicon-cog"></span>
								&nbsp;&nbsp;娱乐 
								<b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;
							</a>
							<ul class="dropdown-menu">
								<shiro:hasPermission name="play:qrcode">
									<li><a href="${ctx}/play/qrcode/menu">二维码</a></li>
								</shiro:hasPermission>
							</ul></li>
					</ul> --%>
				</shiro:authenticated>
				<c:if test="${msg!=null}">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <span
								class="glyphicon glyphicon-envelope"></span>消息 <span
								class="badge">${msg.size()}</span>
						</a>
							<ul class="dropdown-menu list-group">
								<c:forEach items="${msg}" var="msgStr">
									<li class="list-group-item"><a href="#">${msgStr}</a></li>
								</c:forEach>
							</ul></li>
					</ul>
				</c:if>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- 导航条居中 -->
	</nav>
	<!-- 导航条 end -->

	<div id="context-menu">
		<ul class="dropdown-menu" role="menu">
			<li><a tabindex="-1" href="#">asdasd</a></li>
			<li><a tabindex="-1" href="#">Separated link</a></li>
		</ul>
	</div>

</div>