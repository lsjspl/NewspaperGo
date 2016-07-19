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
		<input type="hidden" name="id" value="${urlsInfo.id}">
				
			<div class="form-group">
			   <label for="type">类型</label>
				<select class="form-control" name="type"  id="type" >
					<option value="0" <c:if test="${urlsInfo.type==0}">selected="selected"</c:if>>地方媒体</option>
					<option value="1" <c:if test="${urlsInfo.type==1}">selected="selected"</c:if>>中央媒体</option>
				</select>
			</div>
			 <div class="form-group">
			   <label for="type">状态</label>
				<select class="form-control" name="state"  id="state" >
					<option value="0" <c:if test="${urlsInfo.state==0}">selected="selected"</c:if>>爬取</option>
					<option value="1" <c:if test="${urlsInfo.state==1}">selected="selected"</c:if>>不爬取</option>
				</select>
			</div>
			 <div class="form-group">
			   <label for="urls">爬取网址</label>
			   <input type="text" class="form-control" name="urls" id="urls"  value="${urlsInfo.urls}">
			</div>
			<div class="form-group">
			   <label for="name">媒体名称</label>
			   <input type="text" class="form-control" name="name" id="name" value="${urlsInfo.name}">
			</div>
						<div class="form-group">
			   <label for="key">关键词</label>
			   <input type="text" class="form-control" name="keyWord" id="keyWord" value="${urlsInfo.keyWord}">
			</div>
									<div class="form-group">
			   <label for="titlePattern">标题</label>
			   <input type="text" class="form-control" name="titlePattern" id="titlePattern" value="${urlsInfo.titlePattern}">
			</div>
						<div class="form-group">
			   <label for="timePattern">时间</label>
			   <input type="text" class="form-control" name="timePattern" id="timePattern"  value="${urlsInfo.timePattern}">
			</div>
						<div class="form-group">
			   <label for="urlPattern">网址</label>
			   <input type="text" class="form-control" name="urlPattern" id="urlPattern"  value="${urlsInfo.urlPattern}">
			</div>
						<div class="form-group">
			   <label for="contentPattern">内容</label>
			   <input type="text" class="form-control" name="contentPattern" id="contentPattern"  value="${urlsInfo.contentPattern}">
			</div>
						<div class="form-group">
			   <label for="otherPattern">其他</label>
			   <input type="text" class="form-control" name="otherPattern" id="otherPattern"  value="${urlsInfo.otherPattern}">
			</div>
			<div class="form-group">
				<button id="enter" type="submit" class="btn btn-info pull-right">修改</button>
			</div>
	
		</form>
	</div>
	</div>
</body>
</html>
