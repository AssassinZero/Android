define(function(require, exports, module) {
	require('/resources/js/jquery');
        $("#tabmain div ul li div a").click(function(e){
           e.preventDefault();
           $("#tab1").hide();
           $("#tab2").show(); 
        })
        	
})