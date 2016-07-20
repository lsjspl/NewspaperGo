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
	
	$(function(){
		$("#testCatcher").click(function(){
			$.ajax({
				url:"../testCatcher?id="+$("#id").val(),
				type : "post",
				data : "",
				dataType : 'json',
				success:function(data,status){
					$("#testResult").html(JSON.stringify(data));
				}
			});
		});
		
	});
</script>
</head>
<body>

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">POWERED BY LIUSANJUN</div>
  
  <div class="panel-body">
  
  <div class="col-lg-6">
		<form id="addForm" class="form-horizontal" role="form" action="${ctx}/catcher/addUrlsInfo" method="post">
		<input type="hidden" id="id" name="id" value="${urlsInfo.id}">
		 <fieldset>
    	<legend>编辑【${urlsInfo.name}】基础信息</legend>
			<div class="form-group">
			<label for="type" class="col-lg-4 control-label">类型</label>
    		<div class="col-lg-8">
				<select class="form-control" name="type"  id="type" >
					<option value="0" <c:if test="${urlsInfo.type==0}">selected="selected"</c:if>>地方媒体</option>
					<option value="1" <c:if test="${urlsInfo.type==1}">selected="selected"</c:if>>中央媒体</option>
				</select>
			</div>
			</div>
			
			<div class="form-group">
			<label for="state" class="col-lg-4 control-label">状态</label>
      			<div class="col-lg-8">
				<select class="form-control" name="state"  id="state" >
					<option value="0" <c:if test="${urlsInfo.state==0}">selected="selected"</c:if>>爬取</option>
					<option value="1" <c:if test="${urlsInfo.state==1}">selected="selected"</c:if>>不爬取</option>
				</select>
				</div>
			</div>
			
			 <div class="form-group">
			   <label for="urls"  class="col-lg-4 control-label">爬取网址</label>
			   <div class="col-lg-8">
			   <input type="text" class="form-control" name="urls" id="urls" placeholder="网址"  value="${urlsInfo.urls}">
			   </div>
			</div>
			<div class="form-group">
			   <label for="name"  class="col-lg-4 control-label">媒体名称</label>
			   <div class="col-lg-8">
			   <input type="text" class="form-control" name="name" id="name" placeholder="媒体名称"  value="${urlsInfo.name}">
			   </div>
			</div>
			<div class="form-group">
			   <label for="keyWord"  class="col-lg-4 control-label">关键词</label>
			   <div class="col-lg-8">
			   <input type="text" class="form-control" name="keyWord" id="keyWord" placeholder="关键词"  value="${urlsInfo.keyWord}">
			   </div>
			</div>
			<div class="form-group">
			   <label for="titlePattern"  class="col-lg-4 control-label">标题选择器</label>
			   <div class="col-lg-8">
			   <input type="text" class="form-control" name="titlePattern" id="titlePattern" placeholder="标题选择器"  value="${urlsInfo.titlePattern}">
			   </div>
			</div>
			<div class="form-group">
			   <label for="contentPattern"  class="col-lg-4 control-label">内容选择器</label>
			   <div class="col-lg-8">
			   <input type="text" class="form-control" name="contentPattern" id="contentPattern" placeholder="内容选择器"  value="${urlsInfo.contentPattern}"> 
			   </div>
			</div>
			<div class="form-group">
				<button id="enter" type="submit" class="btn btn-info pull-right">编辑</button>
				<button type="button" id="testCatcher"  class="btn btn-info pull-right">测试</button>
			</div>
	  	</fieldset>
		</form>
		</div>
		
		<div id="testResult" class="col-lg-6">
		 
		 哈哈哈
		 </div>
	</div>
	</div>
	
</body>
</html>
