{{!-- 我的票据-历史票据 --}} {{#each data}}
<ul class="tradeRecordItem clearfix cashItem" data-json="{{toJsonString this}}">
    <li class="tabs1" title="{{sourceText}}"><!-- 来源 -->
        {{#if sourceText}}
            {{sourceText}}
        {{else}}
            -
        {{/if}}
    </li>
    <li class="tabs2" title="{{billTypeText}}"><!-- 票据类别 -->
        {{#if billTypeText}}
            {{billTypeText}}
        {{else}}
            -
        {{/if}}
    </li>
    <li class="tabs3" title="{{billAttributeText}}"><!-- 票据属性 -->
        {{#if billAttributeText}}
            {{billAttributeText}}
        {{else}}
            -
        {{/if}}
    </li>
    <li class="tabs4" title="{{billNo}}"><!-- 票据号码 --> 
        {{#if billNo}}
            {{billNoType billNo}}
        {{else}}
            -
        {{/if}}
    </li>
    <li class="tabs5" title="{{dueDate}}"><!-- 到期日 --> 
        {{#if dueDate}}
            {{dueDate}}
        {{else}}
            -
        {{/if}}
    </li>
    <li class="tabs6" title="{{yuanConversion billAmount}}"><!-- 票面金额（元） --> 
        {{#if billAmount}}
            {{yuanConversion billAmount}}
        {{else}}
            -
        {{/if}}
    </li>
 
    <li class="tabs8">
        <!-- {{sentBuyerOperation orderStatus id receiptId buyerId creator}} -->
        <a class="lookUp" href="javascript:;" data-id="{{billNo}}">票据正反面</a>
    </li>
</ul>
{{/each}}