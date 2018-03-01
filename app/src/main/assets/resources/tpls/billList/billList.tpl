{{!-- 卖家-全部交易 --}} {{#each data}}
<ul class="cashItem">
    <li class="list-uli">
        <input type="checkbox" checked="{{hasElement billNo}}" class="hideCheck" id="{{billNo}}"/>
        {{labelDis isDiscussPersonally transactionState billNo}}
    </li>
    <!-- 票据类别 -->
    <li class='list-uli0'>{{billType type}}</li>
    <!-- 所属企业 -->
    <li class='list-uli1'>{{acceptorName}}{{billTypeIco billAttribute}}</li>
    <!-- 票面金额 -->
    <li class='list-uli2 bordercolor'>{{moneyFormat faceAmount}}<em>万元</em></li>
    <!-- 票据到期日 -->
    <li class='list-uli3'>{{splitDate dueDate}}</li>
    <!-- 调整天数 -->
    <li class='list-uli4'>{{adjustDays}}天</li>
    <!-- 报价 -->
    <li class='list-uli5'>{{offer isDiscussPersonally quotationMethod yearRate discount}}</li>
    <!-- 操作 -->
    <li class='list-uli6'>{{listInfor transactionState billNo}}</li>
</ul>
{{/each}}