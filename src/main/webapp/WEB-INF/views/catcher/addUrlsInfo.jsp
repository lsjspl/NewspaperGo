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
  
  
  <form class="form-horizontal">
  <fieldset>
    <legend>Legend</legend>
    <div class="form-group">
      <label for="inputEmail" class="col-lg-2 control-label">Email</label>
      <div class="col-lg-10">
        <input type="text" class="form-control" id="inputEmail" placeholder="Email">
      </div>
    </div>
    <div class="form-group">
      <label for="inputPassword" class="col-lg-2 control-label">Password</label>
      <div class="col-lg-10">
        <input type="password" class="form-control" id="inputPassword" placeholder="Password">
        <div class="checkbox">
          <label>
            <input type="checkbox"> Checkbox
          </label>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="textArea" class="col-lg-2 control-label">Textarea</label>
      <div class="col-lg-10">
        <textarea class="form-control" rows="3" id="textArea"></textarea>
        <span class="help-block">A longer block of help text that breaks onto a new line and may extend beyond one line.</span>
      </div>
    </div>
    <div class="form-group">
      <label class="col-lg-2 control-label">Radios</label>
      <div class="col-lg-10">
        <div class="radio">
          <label>
            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">
            Option one is this
          </label>
        </div>
        <div class="radio">
          <label>
            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
            Option two can be something else
          </label>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="select" class="col-lg-2 control-label">Selects</label>
      <div class="col-lg-10">
        <select class="form-control" id="select">
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
        <br>
        <select multiple="" class="form-control">
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
      </div>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-2">
        <button type="reset" class="btn btn-default">Cancel</button>
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
    </div>
  </fieldset>
</form>
  
  <div class="panel-body">
	</div>
	<div class="table-responsive">
	<div class="col-lg-6">
		<form id="addForm" role="form" action="${ctx}/catcher/addUrlsInfo" method="post">
		 <fieldset>
    	<legend>Legend</legend>
		<input type="hidden" name="state" value="0">
			
			<div class="form-group">
			   <label for="type">类型</label>
				<select class="form-control" name="type"  id="type" >
					<option value="0">地方媒体</option>
					<option value="1">中央媒体</option>
				</select>
			</div>
			 <div class="form-group">
			   <label for="urls"  class="col-lg-2 control-label">爬取网址</label>
			   <div class="col-lg-10">
			   <input type="text" class="form-control" name="urls" id="urls" placeholder="网址">
			   </div>
			</div>
			<div class="form-group">
			   <label for="name"  class="col-lg-2 control-label">媒体名称</label>
			   <input type="text" class="form-control" name="name" id="name" placeholder="媒体名称">
			</div>
			<div class="form-group">
			   <label for="keyWord"  class="col-lg-2 control-label">关键词</label>
			   <div class="col-lg-10">
			   <input type="text" class="form-control" name="keyWord" id="keyWord" placeholder="关键词">
			   </div>
			</div>
			<div class="form-group">
			   <label for="titlePattern"  class="col-lg-2 control-label">标题选择器</label>
			   <div class="col-lg-10">
			   <input type="text" class="form-control" name="titlePattern" id="titlePattern" placeholder="标题选择器">
			   </div>
			</div>
			<div class="form-group">
			   <label for="contentPattern"  class="col-lg-2 control-label">内容选择器</label>
			   <div class="col-lg-10">
			   <input type="text" class="form-control" name="contentPattern" id="contentPattern" placeholder="内容选择器">
			   </div>
			</div>
<!-- 						<div class="form-group">
			   <label for="timePattern">时间</label>
			   <input type="text" class="form-control" name="timePattern" id="timePattern" placeholder="">
			</div>
						<div class="form-group">
			   <label for="urlPattern">网址</label>
			   <input type="text" class="form-control" name="urlPattern" id="urlPattern" placeholder="">
			</div>
						<div class="form-group">
			   <label for="otherPattern">其他</label>
			   <input type="text" class="form-control" name="otherPattern" id="otherPattern" placeholder="">
			</div> -->
			<div class="form-group">
				<button id="enter" type="submit" class="btn btn-info pull-right">添加</button>
			</div>
	  	</fieldset>
		</form>
		</div>
	</div>
	</div>
</body>
</html>
