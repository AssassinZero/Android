define(function (require, exports, module) {
    require('/resources/js/jquery');
    // var comfn = require('/resources/js/commonFn.js');
    var index = 0;
    var sellerArr = ['../../resources/img/guide/sb-1.png', '../../resources/img/guide/sb-22.png', '../../resources/img/guide/sb-33.png'];
    var sellerWordArr1 = ['../../resources/img/guide/c1-2.png', '../../resources/img/guide/c222.png', '../../resources/img/guide/c3-2.png'];
    var sellerWordArr2 = ['../../resources/img/guide/c1-1.png', '../../resources/img/guide/cc2-2.png', '../../resources/img/guide/c3-1.png'];
    // $(".seller_banner_img3").

    //我是卖家
    $(".seller ul li:eq(0)").css('color', '#333')
    //right
    $(".seller_banner_img3").click(function () {
        $(".seller_li_img")[index].src = sellerWordArr1[index]
        $(".seller ul li:eq(" + index + ")").css('color', '#ccc')
        // alert($(".buyer_banner_img2").attr('src'))
        index++;
        if (index > 2) {
            index = 0
        }
        $(".seller_li_img")[index].src = sellerWordArr2[index]
        $(".seller ul li:eq(" + index + ")").css('color', '#333')
        $(".seller_banner_img2")[0].src = sellerArr[index];
        if(index==0){
             $(".seller > p").html('若没有邀请码,请联系服务热线400-003-6006')
        }else{
            $(".seller > p").html('</br>')
        }
    })
    //left
    $(".seller_banner_img1").click(function () {

        $(".seller_li_img")[index].src = sellerWordArr1[index]
        $(".seller ul li:eq(" + index + ")").css('color', '#ccc')
        // alert($(".buyer_banner_img2").attr('src'))
        index--
        if (index < 0) {
         index = 2
        }
        $(".seller_li_img")[index].src = sellerWordArr2[index]
        $(".seller ul li:eq(" + index + ")").css('color', '#333')

        $(".seller_banner_img2")[0].src = sellerArr[index];
        if(index==0){
             $(".seller > p").html('若没有邀请码,请联系服务热线400-003-6006')
        }else{
            $(".seller > p").html('</br>')
        }
    })

    //我是买家
    var index2 = 0;
    var buyerArr = ['../../resources/img/guide/bb.png', '../../resources/img/guide/bb-22.png', '../../resources/img/guide/bb-33.png'];
    var buyerWordArr1 = ['../../resources/img/guide/c1-2.png', '../../resources/img/guide/c2.png', '../../resources/img/guide/c3-2.png'];
    var buyerWordArr2 = ['../../resources/img/guide/c1-1.png', '../../resources/img/guide/c2-2.png', '../../resources/img/guide/c3-1.png'];
    $(".buyer ul li:eq(0)").css('color', '#333')
    //right
    $(".buyer_banner_img3").click(function () {
        $(".buyer_li_img")[index2].src = buyerWordArr1[index2]
        $(".buyer ul li:eq(" + index2 + ")").css('color', '#ccc')
        // alert($(".buyer_banner_img2").attr('src'))
        index2++;
        if(index2 > 2){
             index2 = 0
        }
        $(".buyer_li_img")[index2].src = buyerWordArr2[index2]
        $(".buyer ul li:eq(" + index2 + ")").css('color', '#333')
         $(".buyer_banner_img2")[0].src = buyerArr[index2];
         if(index2==0){
             $(".buyer > p").html('资格审核-达成协议-获取账号')
        }else{
            $(".buyer > p").html('</br>')
        }
    })
    //left
    $(".buyer_banner_img1").click(function () {
         $(".buyer_li_img")[index2].src = buyerWordArr1[index2]
        $(".buyer ul li:eq(" + index2 + ")").css('color', '#ccc')
        // alert($(".buyer_banner_img2").attr('src'))
        index2--
        if (index2 < 0) {
         index2 = 2
        }
        $(".buyer_li_img")[index2 ].src = buyerWordArr2[index2]
        $(".buyer ul li:eq(" + index2  + ")").css('color', '#333')
         $(".buyer_banner_img2")[0].src = buyerArr[index2];
          if(index2==0){
             $(".buyer > p").html('资格审核-达成协议-获取账号')
        }else{
            $(".buyer > p").html('</br>')
        }
    })

    //我是代理商
    var index3=0;
     var agentArr = ['../../resources/img/guide/bb.png', '../../resources/img/guide/ab-22.png', '../../resources/img/guide/bb.png'];
    var agentWordArr1 = ['../../resources/img/guide/c1-2.png', '../../resources/img/guide/c222.png', '../../resources/img/guide/ag-c32.png'];
    var agentWordArr2 = ['../../resources/img/guide/c1-1.png', '../../resources/img/guide/cc2-2.png', '../../resources/img/guide/ag-c31.png'];
    $('.agent_li:eq(0)').css('color','#333')
    //right
    $(".agent_banner_img3").click(function () {
        
        $(".agent_li_img")[index3].src = agentWordArr1[index3]
        $(".agent ul li:eq(" + index3 + ")").css('color', '#ccc')
        // alert($(".buyer_banner_img2").attr('src'))
        index3++;
        if(index3 > 2){
             index3 = 0
        }
        $(".agent_li_img")[index3].src = agentWordArr2[index3]
        $(".agent ul li:eq(" + index3 + ")").css('color', '#333')
         $(".agent_banner_img2")[0].src = agentArr[index3];
    })

    //left
    $(".agent_banner_img1").click(function () {
         $(".agent_li_img")[index3].src = agentWordArr1[index3]
        $(".agent ul li:eq(" + index3 + ")").css('color', '#ccc')
        index3--
        if (index3 < 0) {
         index3 = 2
        }
        $(".agent_li_img")[index3].src = agentWordArr2[index3]
        $(".agent ul li:eq(" + index3  + ")").css('color', '#333')

         $(".agent_banner_img2")[0].src = agentArr[index3];
    })



    // 悬停，改变状态
    $(".buyer_banner_img1").hover(
        function () {          
            $(this)[0].src ="../../resources/img/guide/left-2.png"
        },
        function () {
            $(this)[0].src = "../../resources/img/guide/left-1.png"
        }
    );
     $(".buyer_banner_img3").hover(
        function () {          
            $(this)[0].src ="../../resources/img/guide/right-1.png"
        },
        function () {
            $(this)[0].src = "../../resources/img/guide/right-2.png"
        }
    );

    $(".seller_banner_img1").hover(
        function () {          
            $(this)[0].src ="../../resources/img/guide/left-2.png"
        },
        function () {
            $(this)[0].src = "../../resources/img/guide/left-1.png"
        }
    );
     $(".seller_banner_img3").hover(
        function () {          
            $(this)[0].src ="../../resources/img/guide/right-1.png"
        },
        function () {
            $(this)[0].src = "../../resources/img/guide/right-2.png"
        }
    );

    $(".agent_banner_img1").hover(
        function () {          
            $(this)[0].src ="../../resources/img/guide/left-2.png"
        },
        function () {
            $(this)[0].src = "../../resources/img/guide/left-1.png"
        }
    );
     $(".agent_banner_img3").hover(
        function () {          
            $(this)[0].src ="../../resources/img/guide/right-1.png"
        },
        function () {
            $(this)[0].src = "../../resources/img/guide/right-2.png"
        }
    );

});