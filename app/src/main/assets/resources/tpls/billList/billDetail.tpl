<div class="Details-top">
    <span>{{acceptorName}}</span>
    <div class="box">转让1手</div>
    {{tBackState backState}}
</div>
<div class="Details-content">
    <div class="money">
        <label>{{moneyFormat faceAmount}}<em>万元</em></label >
        <span>票面金额</span>
    </div>
    <div class="date">
        <label >{{splitDate dueDate}}</label >
        <span>到期日</span>
    </div>
    <div class="offer">
        {{offerDetail isDiscussPersonally quotationMethod yearRate discount}}
    </div>
</div>
<div class="Details-bottom">
    <ul>
        <li class="first">
            <label>票号：</label><span>{{billNo}}</span>
        </li>
        <li>
            <label>票据类型：</label><span>{{type}}</span>
        </li>
        <li class="other">
            <label>票据属性：</label><span>{{billAttribute}}</span>
        </li>
        <li class="first">
            <label>持票方企业名称：</label>{{subString enterpriseName}}
        </li>
        <li>
            <label>调整天数：</label><span>{{adjustDays}}天</span>
        </li>
    </ul>
</div>
<div class="time" id="Buy">
    <img class="time-img" src="../../../resources/img/billlist/time.png">
    <h1>上架时间</h1>
    <p>{{shelvesTime}}</p>
    {{stateBtn isDiscussPersonally transactionState officePhone}}
</div>