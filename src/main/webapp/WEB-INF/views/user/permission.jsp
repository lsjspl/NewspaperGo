<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<script src="${ctx}/resources/web/permissionJs.js"></script>
	<title>主页</title>
</head>
<body>

<!-- Button trigger modal -->
<button id="add" class="btn btn-primary btn" data-toggle="modal" data-target="#myModal">
添加权限
</button>
<hr/>
 <div class="table-responsive">
      <table class="table table-hover center">
        <thead>
          <tr>
            <th>#</th>
            <th>权限名称</th>
            <th>方法名称</th>
             <th>锁定状态</th>
             <th>操作</th>
          </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${list}"  var="list"> 
          <tr>
            <td>${list.id}</td>
            <td>${list.name}</td>
             <td>${list.method}</td>
             <td>${list.state}</td>
            <td>
            <a href="./del/${list.id}"><span class="glyphicon glyphicon-trash"></span></a> | 
             <a href="./lock/${list.id}"><span class="glyphicon glyphicon-lock"></span></a> | 
             <a class="update" data-toggle="modal" data-target="#myModal"  href="#">
             <span class="glyphicon glyphicon-pencil"></span></a>
            </td>
          </tr>
          </c:forEach>
          
        </tbody>
      </table>
    </div>
 
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">添加权限</h4>
      </div>
      <form id="addForm" role="form" action="../permission/add" method="post">
      <div class="modal-body">
      <!-- 弹框内容  -->
      
      		<div class="input-group">
			<span class="input-group-addon">权限名称</span>
			<input type="text" class="form-control "  id="name" name="name"  placeholder="权限名称">
			<span class="input-group-addon"></span>
			</div>
			<br />
			<div class="input-group">
		<span class="input-group-addon">操作名称</span>
		<input type="text"  class="form-control" id="method" name="method" placeholder="操作名称" >
		</div>
		<br/>
	
      
      <!-- 弹框内容  -->
      </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button id="enter" type="submit"  class="btn btn-info pull-right">添加</button>
      </div>
      
      </form>
    </div>
  </div>
</div>
 
</body>
</html>
