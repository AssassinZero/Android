{{!-- 订单信息-弹窗 --}}
<div class='titleWarp'>
    <span class="tipTitle">持票方</span>
    <span class="hTitle">
        {{#if pjsReceipt.orgName}}
            {{pjsReceipt.orgName}}
        {{else}}
            -
        {{/if}}
    </span>
</div>
<div class="lf-tab">
    <ul class="lf-tab-title">
        <li class="lf-this">交割信息</li>
        <!-- <li>电票详情</li> -->
    </ul>
    <div class="lf-tab-content">
        <div class="lf-tab-item lf-show">
            <div class="lfTab-item-left">
                <ul>
                    <li><label>汇票类型</label><span>
                        {{#if pjsReceipt.receiptType}}
                            {{pjsReceipt.receiptType}}
                        {{else}}
                            -
                        {{/if}}
                    </span></li>
                    <li><label>票据贴现日</label><span>
                        {{#if pjsReceipt.issueDate}}
                            {{dateFormat pjsReceipt.issueDate}}
                        {{else}}
                            -
                        {{/if}}
                    </span></li>
                    <li><label> 融资金额</label><span>
                        {{#isNotBlank pjsReceipt.financingAmount}}
                            {{toThousands pjsReceipt.financingAmount}}元
                        {{else}}
                            -
                        {{/isNotBlank}}
                    </span></li>
                    <li><label>票面金额</label><span>
                        {{#if pjsReceipt.billAmt}}
                            {{toThousands pjsReceipt.billAmt}}元
                        {{else}}
                            -
                        {{/if}}
                    </span></li>
                    <li><label>贴现率(%)</label><span>
                        {{#isNotBlank pjsReceipt.discountRate}}
                            {{multiplicationFn pjsReceipt.discountRate 100}}
                        {{else}}
                            -
                        {{/isNotBlank}}
                    </span></li>
                    <li><label>服务费</label><span>
                        <!-- 服务费 = financingAmount - settlementAmt -->
                        {{#if pjsReceipt.financingAmount}}
                            {{subtractionFn pjsReceipt.financingAmount pjsReceipt.settlementAmt}}元
                        {{else}}
                            -
                        {{/if}}
                    </span></li>
                    <li><label>购买方</label><span>
                        {{#if pjsReceipt.maker}}
                            {{pjsReceipt.maker}}
                        {{else}}
                            -
                        {{/if}}
                    </span></li>
                </ul>
            </div>

            <!-- 每一步详情 S -->
            <div class="lfTab-item-right">
            {{#if pjsOrderProcesseList}}
                <ul style="border-bottom:1px solid #eaeaea; ">
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "02"}}'>
                    <p><span class='sort'>1</span>协议达成，资金方确定<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "02" ../countDown}}
                    </div>
                </li>
                
                {{!-- <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "06"}}'>
                    <p><span class='sort'>2</span>卖家签收电子担保函<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "06" ../countDown}}
                    </div>
                </li> --}}
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "09"}}'>
                    <p><span class='sort'>2</span>卖家签署合同<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "09" ../countDown}}
                    </div>
                </li>
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "10"}}'>
                    <p><span class='sort'>3</span>资金方确认，资金方付款<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "10" ../countDown}}
                    </div>
                </li>
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "12"}}'>
                    <p><span class='sort'>4</span>资金代收，将电银背书转交至交割中心<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "12" ../countDown}}
                    </div>
                </li>
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "15"}}'>
                    <p><span class='sort'>5</span>交割中心确定签收电银<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "15" ../countDown}}
                    </div>
                </li>
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "17"}}'>
                    <p><span class='sort'>6</span>交割中心签收电银，资金到账<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "17" ../countDown}}
                    </div>
                </li>
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "18"}}'>
                    <p><span class='sort'>7</span>卖方确认收款<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "18" ../countDown}}
                    </div>
                </li>
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "21"}}'>
                    <p><span class='sort'>8</span>交割完成<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "21" ../countDown}}
                    </div>
                </li>
                {{#equal ../orderStatus "24"}}
                <li class='{{orderDetailActive pjsOrderProcesseList ../orderStatus "24"}}'>
                    <p><span class='sort'>9</span>取消交割<span class='icon'></span></p>
                    <div class='silderDiv'>
                        {{orderDetailOpen pjsOrderProcesseList ../orderStatus "24" ../countDown}}
                    </div>
                </li>
                {{/equal}}
                </ul>
            {{/if}}
            </div>
            <!-- 每一步详情 E -->

        </div>
        <!-- <div class="lf-tab-item">
            <div class='ticket'>
                <p>电票正面</p>
                <img src="/resources/img/seller/nophoto.jpg">
            </div>
            <div class='ticket'>
                <p>电票背面</p>
                <img src="/resources/img/seller/nophoto.jpg">
            </div>
        </div> -->
    </div>
</div>