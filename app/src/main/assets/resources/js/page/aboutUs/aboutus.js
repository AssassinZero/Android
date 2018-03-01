define(function(require, exports, module) {
	require('/resources/js/jquery');

  ajaxUrl(10)

  $(".tabs a").click(function(){
    var moduleType = $(this).attr("data-val");
    ajaxUrl(moduleType)
    $(this).addClass('hover').siblings().removeClass('hover');
    
  })

  function ajaxUrl(param){
    if(param==10){
      $("#tabmain h1").html("注册登录");
    }else if(param==20){
      $("#tabmain h1").html("交易须知");
    }else if(param==30){
      $("#tabmain h1").html("票据常识");
    }else if(param==40){
      $("#tabmain h1").html("法律说明");
    }else if(param==50){
      $("#tabmain h1").html("代理商须知");
    }
    $.ajax({
      type:'get',
      url:'/modules/guide/bizBeginnerGuideAction/getBeginnerGuideList.htm?module=' + param,
      dataType:'json',
      success:function(json){
        var jsonLen = json.length;
        var jsonList="";
        if(jsonLen<=0){
          jsonList = "<li>暂无数据</li>"
        }
        for(var i=0;i<jsonLen;i++){
          jsonList=jsonList+'<li>'+
                                '<div class="problemTop">'+
                                    '<h3>'+json[i].title+'</h3>'+
                                    '<em class="problemIco"></em>'+
                                '</div>'+
                                '<div class="problemBtm">'+
                                    '<p>'+json[i].content+'</p>'+
                                '</div>'+
                            '</li>'
        }
        $(".problemList").html(jsonList);
        $(".problemTop").on("click", problemTop);
      }
    })
  }

  function problemTop(){
    if($(this).parent('li').hasClass("active")==true){
      $(this).parent('li').removeClass('active');
      return
    }
    $(this).parent('li').addClass('active').siblings().removeClass('active');
  }


	//  $(".tabs a").click(function(e){
  //        e.preventDefault();
  //         var str=$(this).attr("href");
  //          $(str).siblings().hide();
  //           $(str).show(); 
  //       })
  //       $("#tabmain div ul li div a").click(function(e){
  //           e.preventDefault();
  //          $("#tab5").siblings().hide();
  //           $("#tab5").show(); 
  //       })
  //       	var pageTool = require('./pagination');
	
	// $(function() {
	// 	var page = $('div[page]');
	// 	$(page).each(function(){
	// 		var	siteId = $(this).attr('siteId');
	// 		pageTool.initPage($(this), 0, 5, {
	// 			siteId : siteId
	// 		});
	// 	});
	// });
  //       function initMap(){
  //     createMap();//创建地图
  //     setMapEvent();//设置地图事件
  //     addMapControl();//向地图添加控件
  //     addMapOverlay();//向地图添加覆盖物
  //   }
  //   function createMap(){ 
  //     map = new BMap.Map("map"); 
  //     map.centerAndZoom(new BMap.Point(120.116014,30.308362),16);
  //   }
  //   function setMapEvent(){
  //     map.enableScrollWheelZoom();
  //     map.enableKeyboard();
  //     map.enableDragging();
  //     map.enableDoubleClickZoom()
  //   }
  //   function addClickHandler(target,window){
  //     target.addEventListener("click",function(){
  //       target.openInfoWindow(window);
  //     });
  //   }
  //   function addMapOverlay(){
  //     var markers = [
  //       {content:"杭州市票之保股份有限公司",title:"票之保",imageOffset: {width:0,height:3},position:{lat:30.31042,lng:120.112744}}
  //     ];
  //     for(var index = 0; index < markers.length; index++ ){
  //       var point = new BMap.Point(markers[index].position.lng,markers[index].position.lat);
  //       var marker = new BMap.Marker(point,{icon:new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png",new BMap.Size(20,25),{
  //         imageOffset: new BMap.Size(markers[index].imageOffset.width,markers[index].imageOffset.height)
  //       })});
  //       var label = new BMap.Label(markers[index].title,{offset: new BMap.Size(25,5)});
  //       var opts = {
  //         width: 200,
  //         title: markers[index].title,
  //         enableMessage: false
  //       };
  //       var infoWindow = new BMap.InfoWindow(markers[index].content,opts);
  //       marker.setLabel(label);
  //       addClickHandler(marker,infoWindow);
  //       map.addOverlay(marker);
  //     };
  //   }
  //   //向地图添加控件
  //   function addMapControl(){
  //     var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
  //     scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
  //     map.addControl(scaleControl);
  //     var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
  //     map.addControl(navControl);
  //     var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
  //     map.addControl(overviewControl);
  //   }
  //   var map;
  //     initMap();
});