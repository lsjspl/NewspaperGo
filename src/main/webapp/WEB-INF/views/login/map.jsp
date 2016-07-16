<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>覆盖物事件</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>
<div id="container"></div>
	<div id='panel'></div>
	<script type="text/javascript">
	window.onerror=function (){return true;}
	var isSateMap=false;//是否已经到卫星地图界面
	//声明map对象
	var	map = new AMap.Map('container', {
		view:new AMap.View2D({center:[113.726756,34.772404],
		zoom : 12,
		rotation : 0
		}),
		zooms:[12,15],
		lang:"zh_cn"
	});
	AMap.event.addListener(map, 'zoomend', _onZoomEnd);
	function _onZoomEnd(){
		 	if (map.getZoom() >= 15 && isSateMap==false){
				 	map = new AMap.Map('container',{
				 	center: [113.726751,34.772395],
				 	layers: [new AMap.TileLayer.Satellite()],
				 	rotation : 0,
				 	zoom: 16,
				 	zooms:[14,18],
		  	 	});
				isSateMap=true;
				 AMap.event.addListener(map, 'zoomend', _onZoomEnd); 
				  getLngLat();//圈图 
			}
		 if (map.getZoom() <15 && isSateMap==true){
			 map = new AMap.Map('container', {
					view:new AMap.View2D({center:[113.726756,34.772404],
					zoom : 14,
					rotation : 0
					}),
					zooms:[12,15],
					lang:"zh_cn"
				});
			isSateMap=false;
			AMap.event.addListener(map, 'zoomend', _onZoomEnd);
		}
	}
	function getLngLat(){
		$.ajax({
			type : "POST",
			url : "${ctx}/login/getLngLat",
			async : true,
			dataType : 'json',
			success : function(data) {
				addPolygonArr(data);
			}
		});
	}
	//添加圈图
	function addPolygonArr(data){
		var polygonArr;
		for(var i=0;i<data.length;i++){
			polygonArr = new Array();
			for(var j=0;j<data[i].LngLat.length;j++){
				polygonArr.push(new AMap.LngLat(data[i].LngLat[j].split(',')[0],data[i].LngLat[j].split(',')[1]));
			}
			var	polygon = new AMap.Polygon({
		        path: polygonArr,//设置多边形边界路径
		        strokeColor: "#c00", //线颜色
		        strokeOpacity: 0.9, //线透明度
		        strokeWeight: 2,    //线宽
		        fillColor: "#1791fc", //填充色
		        fillOpacity: 0//填充透明度
		    });
		    AMap.event.addListener(polygon, 'mouseover', loadOtherData);
		    polygon.setMap(map);
		    polygon.setExtData(data[i]);
		}
	  /*   map.setFitView() */
	}
	 function loadOtherData(e) {
		 	var Obj = e.target.getExtData();
	    	var div = document.createElement('div');
			div.innerHTML = "￥10K";
	    	var marker = new AMap.Marker({
				position : new AMap.LngLat(Obj.center.split(',')[0],Obj.center.split(',')[1]),
				content : div,
				offset : {
					x : 0,
					y : 0
				},
				map : map
			});
		}
   </script>
</body>
</html>
