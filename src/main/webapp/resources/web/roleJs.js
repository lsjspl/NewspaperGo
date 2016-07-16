$(document).ready(function() {
			
		$(".update").click(function(){
			$(".input-group-addon") .hide();
				
			addPerBtn();
			
			var id=$(this).attr("id");
			
			$.ajax({
				url : "me/"+id,
				type : 'post',
				dataType : 'json',
				async:false,
				success : function(data, status) {
					$(":input#name").val(data.name);
					$.each(data.permissions,function(){
						$('label[id="'+this.id+'"]').addClass("active");
					});
				}
			});
			
			console.log(".btn.perlable#"+this.id+$(".btn.perlable#"+1).html() );
			$(".idForUpdate").remove();
			$("#addForm").attr("action","modify");
			$("#addForm").append("<input class='idForUpdate' type='hidden' name='id' value='"+id+"'>");
			
		});

		// 当用户点击添加的时候异步加载权限列表
		$("#add").click(function() {
			$(".input-group-addon") .hide();
			
			$(":input#name").val("");
			
			$(".idForUpdate").remove();
			
			$("#addForm").attr("action","add");
			
			addPerBtn();
		});
		
		function addPerBtn( ){//异步加载权限 并增加按钮到面板
			
			$.ajax({
				url : "../permission/getPerGroup",
				type : 'post',
				dataType : 'json',
				async:false,
				success : function(map, status) {
					if (status == "success") {
						$("#autoDiv").empty();//清空权限列表的div
						
						var parentDiv=$("#perPanel");//查找权限面板的div
						
						for ( var method in map) {//循环传来的map
							
							var perList=map[method];//得到权限列表
							
							var div =parentDiv .clone().removeAttr("hidden").attr("id",method+"Panel");//复制模板权限面板 并改变其属性
							
							var perBtns=div.find(".panel-collapse.in");//查找权限按钮列表的父div
							
							var perBtnAdd=perBtns.find(".btn-group").empty();//查找并清空权限列表
							
							div.find(".panel-title").empty().append("权限类型"+method);//添加权限标题
							div.find(".perInfo").attr("href","#"+method);//添加面板指向
							perBtns.attr("id",method);//添加id
							
							for ( var i=0;i< perList.length;i++) {//循环权限列表 并添加权限按钮到页面
								perBtnAdd.append("<label class='btn perlable "+perList[i].id+"' id='"+perList[i].id+"'> "+perList[i].name +"</label>");
							}

							$("#autoDiv").append(div);//追加权限面板
						}
					}
				},
				error : function(xhr, textStatus, errorThrown) {
					alert("权限获取错误 请稍后再试");
				}
			});
		}

		$("#enter").click(
				function() {
					
					if ($("#name").val() == "") {
						$(".input-group-addon") .empty().append("角色名称不能为空").show();
						return false;
					}
					
					var perIds = "";
					$(".btn.perlable.active").each(function() {
						perIds += $(this).attr('id') + ",";
					});
					perIds = perIds.substring(0, perIds.length - 1);
					$("#perIds").val(perIds);
					
					 if($("#perIds").val()==""){
							$(".input-group-addon") .empty().append("没有选择任何权限").show();
							return false;
						}
	});
});