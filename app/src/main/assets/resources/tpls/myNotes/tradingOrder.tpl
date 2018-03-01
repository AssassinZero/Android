{{!-- 卖家-全部交易 --}}
{{#each data}}
<ul class="tradeRecordItem clearfix cashItem" data-json="{{toJsonString this}}">
    <li class="tabs0">
        {{lfChecksBtn dueDate billNo}}
        <!-- <input type="checkbox" class="hideCheck" checked={{hasElement billNo}} id="{{billNo}}"/>
        <label for="{{billNo}}" class="labelCheck {{hasElementAddClass billNo}}"></label> -->
    </li>
    <li class="tabs1" title="{{sourceText}}"><!-- 来源 -->
        {{#if sourceText}}
            {{sourceText}}
        {{else}}
            -
        {{/if}}
    </li>
    <li class="tabs2" title="{{typeText}}"><!-- 票据类别 -->
        {{#if typeText}}
            {{typeText}}
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
    <li class="tabs4" title="{{billNo}}"><!-- 票号后六位 --> 
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
    <li class="tabs6" title="{{yuanConversion faceAmount}}"><!-- 票面金额（元） --> 
        {{#if faceAmount}}
            {{yuanConversion faceAmount}}
        {{else}}
            -
        {{/if}}
    </li>

    
    <li class="tabs7">
        <!-- <a class="theShelves" href="javascript:;" data-id={{billNo}}>上架</a>
        <a class="unregulatedTransaction" href="javascript:;" data-id={{billNo}}>纯票交易</a> -->
        {{lfDueDateBtn dueDate billNo}}
        <a class="lookUp" href="javascript:;" data-id={{billNo}}>查看</a> 
    </li>
</ul>
{{/each}}