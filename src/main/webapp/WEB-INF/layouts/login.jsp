<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link rel="stylesheet"  href="${ctx}/resources/ui/css/bootswatch.min.css">
<!-- 输入框特效js -->
<%-- <link rel="stylesheet"  href="${ctx}/resources/ui/css/input.css"> --%>
<script src="${ctx}/resources/ui/js/jquery-2.1.3.min.js"></script>
<script src="${ctx}/resources/ui/js/bootstrap.min.js"></script>
<!-- 右键菜单js -->
<script src="${ctx}/resources/ui/js/menu.js"></script>


<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<sitemesh:head/>
	<!-- 导航条 -->
<style type="text/css">
	body { 
			 padding-top: 150px;
		 }
</style>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div class="row">
			<div id="main"  >
				<sitemesh:body />
			</div>
		</div>
		<%-- <%@ include file="/WEB-INF/layouts/footer.jsp"%> --%>
	</div>
 </body>
</html>