{{!-- 卖家-卖出票据 --}}{{#each data}}
<ul class="mySellerItem clearfix" data-json="{{toJsonString this}}">
    <p class="mySellertitle">
        <label>订单号:{{orderNo}}</label>
        <span>{{createTime}}</span>
        <em>持票方企业名称：{{#if enterpriseName}} {{enterpriseName}} {{else}} 暂无 {{/if}}</em></p>
    <li class="tabs1" title="{{billsNum}}">
        <!-- 基本信息 --> 
        <p class="lineht">票据数量:{{billsNum}}</p>
    </li>
    <!-- <li class="tabs7" title="{{name2}}">
        收款信息
        <p>银行账户：{{#if name2}} {{name2}} {{else}} - {{/if}}</p>
        <p>账户名称：{{#if name2}} {{name2}} {{else}} - {{/if}}</p>
    </li> -->
    <li class="tabs4" title="{{name3}}">
        <!-- 金额 -->
        <div class="tabsleft">
            <p>
            {{#if billsTotalAmt}} {{yuanConversion billsTotalAmt}} {{else}} - {{/if}}</p>
            <p>票面金额（万元）</p> 
        </div>
        <div class="tabsright">
            <p>
            {{#if settlementAmount}} {{settlementAmount}} {{else}} - {{/if}}</p>
            <p>结算金额（元）</p> 
        </div>
    </li>
    <li class="tabs5" title="{{businessState}}">
        <!-- 订单状态 -->
        <p class="lineht">{{#if businessState}} {{businessStateType businessState}} {{else}} - {{/if}}
        </p>
    </li> 

    <li class="tabs6">
        {{lfbillReview businessState id}}
        <p><a class="lookUp" href="javascript:;" data-id="{{id}}">查看详情</a> </p>
        <!-- <p><a class="cancelOrder" href="javascript:;" data-id="{{id}}">取消订单</a></p> -->
    </li>
</ul>
{{/each}}