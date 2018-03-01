{{!-- 用户管理列表 --}}
{{#each data}}
<ul class="tradeRecordItem clearfix cashItem" data-json="{{toJsonString this}}">
    <li class="tabs1" title="{{name}}"><!-- 真实姓名 --> 
        {{#if name}}
            {{name}}
        {{else}}
            -
        {{/if}}  
    </li>
    <li class="tabs2" title="{{mobile}}"><!-- 手机号 -->
        {{#if mobile}}
            {{mobile}}
        {{else}}
            -
        {{/if}}
    </li>

    <li class="tabs3" title="{{role}}"><!-- 用户角色 -->
        {{#if role}}
            {{turnRole role}}
        {{else}}
            -
        {{/if}}
    </li>


    <li class="tabs4" title="{{state}}"><!-- 状态 --> 
        {{#if state}}
            {{turnState state}}
        {{else}}
            -
        {{/if}}
    </li>

    
    <li class="tabs5">
    <!-- {{sentBuyerOperation orderStatus id receiptId buyerId creator}} -->
        <a class="edit" href="javascript:;" data-record="{{toJsonString this}}">编辑</a>
        <a class="lookUp" href="javascript:;" data-record="{{toJsonString this}}">查看</a> 
        <a class="locking" href="javascript:;" data-id="{{id}}" data-state="{{state}}">
            {{turnStateBtn state role}}
        </a> 
    </li>
</ul>
{{/each}}