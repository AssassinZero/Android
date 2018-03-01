{{!-- 卖家-全部交易 --}} {{#each data}}
<ul class="mySellerItem clearfix" data-json="{{toJsonString this}}">
    <p class="mySellertitle"><label>订单号:{{orderNo}}</label><span>{{createTime}}</span><em>持票方企业名称：{{#if collectionAccount}} {{collectionAccount}} {{else}} 暂无 {{/if}}</em></p>
    <li class="tabs1">
        <!-- 基本信息 --> 
        <p class="lineht">票据数量:{{onlineOrederIdCount}}</p>
    </li>
    <li class="tabs7">
        <!-- 收款信息 -->
        <p style="margin-top: 24px;">银行账户：{{#if bankAccount}} {{bankAccount}} {{else}} - {{/if}}</p>
        <p style="margin-top: 10px;">账户名称：{{#if accountName}} {{accountName}} {{else}} - {{/if}}</p>
    </li>
    <li class="tabs4">
        <!-- 金额 -->
        <div class="tabsleft">
            <p>
                {{#if billAmountCount}} {{yuanConversion billAmountCount}}万元 {{else}} 0 万元 {{/if}}</p>
            <p>（票面金额）</p>
        </div>
        <div class="tabsright">
            <p>
                {{#if settlementAmount}} {{moneyFor settlementAmount}}元 {{else}} 0 元 {{/if}}</p>
            <p>（结算金额）</p>
        </div>
    </li>
    <li class="tabs5">
        <!-- 订单状态 -->
        <p class="lineht">{{businessStateName}}</p>
    </li> 

    <li class="tabs6">
        {{buyersAverageUserStatus businessState orderNo}}
    </li>
</ul>
{{/each}}