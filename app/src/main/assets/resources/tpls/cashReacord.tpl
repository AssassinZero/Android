<!-- 资金记录 -->
{{#each data}}
<ul class="cashItem clearfix">
    <li class="tradeTime"><!-- 交易时间 -->
	    {{#if createTime}}
	    	{{createTime}}
	    {{else}}
	    	-
	    {{/if}}
		</li>
    <li class="tradeSum"><!-- 交易金额(元) --> 
    	{{#if amount}}
	    	{{amount}}
	    {{else}}
	    	-
	    {{/if}}
    </li>
    <li class="tradeId"><!-- 交易流水号 --> 
    	{{#if serialNumber}}
	    	{{serialNumber}}
	    {{else}}
	    	-
	    {{/if}}
    </li>
    <li class="tradetype"><!-- 交易类型 --> 
    	{{#equal type 1}}充值{{/equal}}
    	{{#equal type 2}}提现{{/equal}}
    	{{#equal type 3}}支付{{/equal}}
    </li>
    <li class="tradeState"><!-- 交易状态 --> 
    	{{#equal status 1}}审核中{{/equal}}
    	{{#equal status 2}}已打款{{/equal}}
    	{{#equal status 3}}交割失败{{/equal}}
    </li>
    <li class="tradeRemark" title="{{remark}}"><!-- 备注 --> 
    	{{#if remark}}
	    	{{remark}}
	    {{else}}
	    	-
	    {{/if}}
    </li>
</ul>
{{/each}}