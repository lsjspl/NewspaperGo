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
	
</script>
</head>
<body>

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">基础信息增加</div>
  <div class="panel-body">
	</div>
	<div class="table-responsive">
		<form id="addForm" role="form" action="${ctx}/catcher/addUrlsInfo" method="post">
		<input type="hidden" name="state" value="0">
				
			<div class="form-group">
			   <label for="type">类型</label>
				<select class="form-control" name="type"  id="type" >
					<option value="0">地方媒体</option>
					<option value="1">中央媒体</option>
				</select>
			</div>
			 <div class="form-group">
			   <label for="urls">爬取网址</label>
			   <input type="text" class="form-control" name="urls" id="urls" placeholder="">
			</div>
			<div class="form-group">
			   <label for="name">关键词名字</label>
			   <input type="text" class="form-control" name="name" id="name" placeholder="">
			</div>
			
			<div class="form-group">
				<button id="enter" type="submit" class="btn btn-info pull-right">添加</button>
			</div>
	
		</form>
	</div>
	</div>
</body>
</html>
