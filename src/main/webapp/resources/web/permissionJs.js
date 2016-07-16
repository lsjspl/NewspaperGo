$(document).ready(
		$(function($) {
			
			$(".update").click(function(){
				$(".input-group-addon") .hide();
				
				var id=$(this).parent().siblings(".id").text();
				var name=$(this).parent().siblings(".name").text();
				var method=$(this).parent().siblings(".method").text();
				
				$(":input#method").attr("disabled","true").val(method);
				$(":input#name").val(name);
				$(".idForUpdate").remove();
				$("#addForm").append("<input class='idForUpdate' type='hidden' name='id' value='"+id+"'>");
				$("#addForm").attr("action","../permission/modify");
			});
			
			$("#add").click(function() {
				$(".input-group-addon") .hide();
				$("#addForm").attr("action","../permission/add");
				$(":input#name").val("");
				$(":input#method").val("");
				$(".idForUpdate").remove();
				
			});
			
			$("#enter").click( function() {
				if ($("#name").val() == "") {
					$(".input-group-addon") .empty().append("权限名称不能为空").show();
						return false;
					}else if($("#method").val() == ""){
						$(".input-group-addon") .empty().append("方法编码不能为空").show();
						return false;
					}
			});
			
		}));