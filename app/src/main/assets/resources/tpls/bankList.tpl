<!-- 用户中心-银行列表 -->
{{#each data}}
<ul class="banklist clearfix">
    <li class="bankIndex">{{increasingIndex @index}}</li>
    <li class="bankName">
        {{#isNotBlank bankArea}}
            {{bankArea}}
        {{else}}
            -
        {{/isNotBlank}}
    </li>
    <li class="bankCode">
        {{#isNotBlank bankCard}}
            {{bankCard}}
        {{else}}
            -
        {{/isNotBlank}}
    </li>
    <li class="bankArea">
        {{#isNotBlank areaCode}}
            {{showBankArea areaCode}}
        {{else}}
            -
        {{/isNotBlank}}
    </li>
    <li class="bankUserName">
        {{#isNotBlank accountName}}
            {{accountName}}
        {{else}}
            -
        {{/isNotBlank}}
    </li>
    <li class="bankOperation">
        {{#equal isDefault 2}}
            <a class="setDefault" href="javascript:;" dataId="{{id}}">设为默认</a>
        {{/equal}}
        {{#notEqual isDefault 2}}【默认账户】{{/notEqual}}
        <a class="editBank" href="javascript:;" dataId="{{id}}" data-bank="{{bankArea}}" data-code="{{bankCard}}" data-areaCode="{{areaCode}}" data-accountName="{{accountName}}">编辑</a>
        <a class="delBank" href="javascript:;" dataId="{{id}}">删除</a>
    </li>
</ul>
{{/each}}