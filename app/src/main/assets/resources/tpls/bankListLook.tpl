<!-- 用户中心-银行列表-仅仅查看 -->
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
        {{#notEqual isDefault 2}}【默认账户】{{/notEqual}}
    </li>
</ul>
{{/each}}