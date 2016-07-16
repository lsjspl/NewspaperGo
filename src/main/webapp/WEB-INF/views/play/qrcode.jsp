<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<title>二维码</title>
	
	<script type="text/javascript">
	
	  $(function(){  
		  $('#code').popover({
 			 animation:true,
 			 trigger :'manual' ,
 				 placement:'right',
 				 content:'你一个字符也不准备输入吗！'
 		 });
	        $("#enter").click(function(){  
	        	 var text=$('#code').val();
	        	 var time=new Date().getTime();
	        	 if(text==''){
	        		  $('#code').popover('show');
	        	 	return false;
	        	 }else{
	        		 
	        		  $('#code').popover('hide');
	        		  $("#img").hide().attr('src', './creat?code='+text+"&time="+time ).fadeIn(); 
	        	 }
	   /*          $.ajax({  
	                url:"./qrcode/creat",  
	                type:'get',  
	                data:"code="+text+"&time="+time,  
	                dataType:'html',  
	                success:function(data,status){  
	                	
	                    if(status == "success"){  
	                        var objs = jQuery.parseJSON(data);  
	                        var str = "";  
	                        for(var i=0;i<objs.length;i++){  
	                            str = str + objs[i]+" ";  
	                           $('#img').attr('src',str);
	                           $('#download').attr('href',str);
	                        }  
	                    }  
	                },  
	                error:function(xhr,textStatus,errorThrown){  
	                }  
	            });   */
	        });  
	    });  
	</script>
</head>
<body>
 <div class="text-center" >
<div class="row  center">
   <label for="code">
      <a id="download"  href="" >
	     	 <img id="img" src="${ctx}/resources/img/img.jpg" class="img-responsive" />
	      </a>
   请输入一些文字：
   &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;   
   &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;&nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  
   &nbsp;&nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;        
    <input type="text" class="form-control input-lg" id="code" placeholder="请输入一些文字！然后点击生成二维码。" >
    </label>
    </div>
<button id="enter" class="btn btn-info btn-lg" >
 生成二维码
</button>

</div>
</body>
</html>
