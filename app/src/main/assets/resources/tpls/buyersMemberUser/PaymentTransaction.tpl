{{!-- 买家-待支付交易 --}} {{#each data}}
<ul class="mySellerItem clearfix" data-json="{{toJsonString this}}">
    <p class="mySellertitle"><label>订单号:{{orderNo}}</label><span>{{createTime}}</span><em>持票方企业名称：{{#if enterpriseName}} {{enterpriseName}} {{else}} 暂无 {{/if}}</em></p>
    <li class="tabs1 lh1">
        <!-- 基本信息 -->
        <p style="margin-top: 24px;">票据类型 : {{billsTypeStatus billsType}} </p>
        <p>票据属性 : {{billsAttributeStatus billsAttribute}}</p>
        <p>票据数量 : {{billsNum}}</p>
    </li>
    <li class="tabs2">
        <!-- 类型 -->
        <p class="lineht">{{businessTypeStatus businessType}}</p>
    </li>
    <li class="tabs3">
        <!-- 报价信息 -->
        {{priceStatus quotationMethod yearRate discount}}
    </li>
    <li class="tabs4">
        <!-- 金额 -->
        <div class="tabsleft">
            <p> {{#if billsTotalAmt}} {{yuanConversion billsTotalAmt}}万元 {{else}} 0 万元 {{/if}}</p>
            <p>（票面金额）</p>
        </div>
        <div class="tabsright">
            <p> {{#if settlementAmount}} {{moneyFor settlementAmount}}元 {{else}} 0 元 {{/if}}</p>
            <p>（结算金额）</p>
        </div>
    </li>
    <li class="tabs5">
        <!-- 订单状态 -->
        <p class="lineht">{{businessStatus}}</p>
    </li>

    <li class="tabs6"> 
        {{buyersMemberStatus this}}
    </li>
</ul>
{{/each}}