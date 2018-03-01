<!-- 票据正面S -->
<div class="billWarp1 billFace">
    <div class="tableWrap billFace">
        <p>显示日期:<span>&nbsp 
            {{#if newDate}}
                {{newDate}}
                        {{/if}}</span>
        </p>
        <img src="../../resources/img/userCenterMember/billLogo.png">
        <h3>{{billsTitleStatus type}}</h3>
        <ul class="billBase clearfix">
            <li>出票日期:<span> {{#if billIssueDate}}
                                           {{billIssueDate}}
                                       {{/if}}</span></li>
            <li>票据状态:<span>&nbsp {{BillSupervisionStatus type}}</span></li>

            <li>汇票到期日:<span>&nbsp {{#if billDueDate}}
                                           {{billDueDate}}
                                       {{/if}}</span></li>
            <li>票据号码:<span>&nbsp {{#if billNo}}
                                           {{billNo}}
                                       {{/if}}</span></li>
        </ul>
        
        <table width="100%">
            <tbody>
                <tr class="firstRow">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="1" rowspan="3"> 出票人 </td>
                    <td colspan="4"> 全&nbsp称 </td>
                    <td colspan="13"> {{#if issueName}}
                                           {{issueName}}
                                       {{/if}}</span> </td>
                    <td colspan="1" rowspan="3"> 收款人 </td>
                    <td colspan="4"> 全&nbsp称 </td>
                    <td colspan="13"> {{#if payeeName}}
                                           {{payeeName}}
                                       {{/if}}</span> </td>
                </tr>
                <tr>
                    <td colspan="4"> 账&nbsp号 </td>
                    <td colspan="13"> <span>{{#if issueBankAccount}}
                                           {{issueBankAccount}}
                                       {{/if}}</span> </td>
                    <td colspan="4"> 账&nbsp号 </td>
                    <td colspan="13"> <span>{{#if payeeBankAccount}}
                                           {{payeeBankAccount}}
                                       {{/if}}</span> </td>
                </tr>
                <tr>
                    <td colspan="4"> 开户银行 </td>
                    <td colspan="13"> <span>{{#if issueOpenBank}}
                                           {{issueOpenBank}}
                                       {{/if}}</span>
                    </td>
                    <td colspan="4"> 开户银行 </td>
                    <td colspan="13"> <span>{{#if payeeOpenBank}}
                                           {{payeeOpenBank}}
                                       {{/if}}</span> </td>
                </tr>
                <tr>
                    <td colspan="5">出票保证信息</td>
                    <td class='row4 col1' colspan="12">保证人名称：{{#if issueBailName}}
                        {{issueBailName}}
                        {{/if}}</td>
                    <td class='row4 col2' colspan="12">保证人地址：{{#if issueBailAdd}}
                        {{issueBailAdd}}
                        {{/if}}</td>
                    <td class='row4 col3' colspan="5">保证日期：{{#if issueBailDate}}
                        {{issueBailDate}}
                        {{/if}}</td>
                </tr>
                <tr>
                    <td colspan="5" rowspan="2">票据金额</td>
                    <td colspan="3" rowspan="2">人民币</br>(大写)</td>
                    <td colspan="14" rowspan="2">{{smalltoBig faceAmount}}</td>
                    <td>十</td>
                    <td>亿</td>
                    <td>千</td>
                    <td>佰</td>
                    <td>十</td>
                    <td>万</td>
                    <td>千</td>
                    <td>佰</td>
                    <td>十</td>
                    <td>元</td>
                    <td>角</td>
                    <td>分</td>
                </tr>
                <tr>
                    {{#if faceAmount}}
                        {{billMoneyFormat faceAmount}}
                    {{else}}
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>￥</td>
                        <td>0</td>
                    {{/if}}
                </tr>
                <tr>
                    <td colspan="5" rowspan="2">承兑人信息</td>
                    <td colspan="3">全&nbsp称</td>
                    <td colspan="11">{{#if acceptorName}}
                        {{acceptorName}}
                        {{/if}}</td>
                    <td colspan="5">开户行行号</td>
                    <td colspan="12">{{#if acceptorBankCode}}
                        {{acceptorBankCode}}
                        {{/if}}</td>
                </tr>
                <tr>
                    <td colspan="3">账&nbsp号</td>
                    <td colspan="11">{{#if acceptorBankAccount}}
                        {{acceptorBankAccount}}
                        {{/if}}</td>
                    <td colspan="5">开户行名称</td>
                    <td colspan="12">{{#if acceptorBankName}}
                        {{acceptorBankName}}
                        {{/if}}</td>
                </tr>
                <tr>
                    <td colspan="5">交易合同号</td>
                    <td colspan="13">
                        {{#if contractNo}}
                        {{contractNo}}
                        {{/if}}</td>
                    <td colspan="1" rowspan="3">承兑信息</td>
                    <td class='row9' colspan="16">出票人承诺：本汇票请予以承兑，到期无条件付款</td>
                </tr>
                <tr>
                    <td colspan="5" rowspan="2">能否转让</td>
                    <td colspan="13" rowspan="2">{{billFrontTransferState isCanTransfer}}</td>
                    <td class='row10' colspan="16">承兑人承诺：本汇票已经承兑，到期无条件付款</td>
                </tr>
                <tr>
                    <td class='row11' colspan="16">承兑日期： {{#if acceptDate}}
                        {{acceptDate}}
                        {{/if}}</td>
                </tr>
                <tr>
                    <td colspan="5">承兑保证信息</td>
                    <td class='row4 col1' colspan="12">保证人名称：{{#if acceptorBailName}}
                        {{acceptorBailName}}
                        {{/if}}</td>
                    <td class='row4 col2' colspan="11">保证人地址：{{#if acceptorBailAdd}}
                        {{acceptorBailAdd}}
                        {{/if}}</td>
                    <td class='row4 col3' colspan="6">保证日期：{{#if acceptorBailDate}}
                        {{acceptorBailDate}}
                        {{/if}}</td>
                </tr>
                <tr>
                    <td colspan="5" rowspan="2">评级信息(由出票发、承兑人自己记载仅供参考)</td>
                    <td colspan="3">出票人</td>
                    <td class='row4 col1' colspan="11">评级主体：{{#if issueRatingBody}}
                        {{issueRatingBody}}
                        {{/if}}</td>
                    <td class='row4 col2' colspan="9">信用等级：{{#if issueCreditRating}}
                        {{issueCreditRating}}
                        {{/if}}</td>
                    <td class='row4 col3' colspan="6">评级到期日：{{#if issueRatingDueDate}}
                        {{issueRatingDueDate}}
                        {{/if}}</td>
                </tr>
                <tr>
                    <td colspan="3">承兑人</td>
                    <td class='row4 col1' colspan="11">评级主体：{{#if acceptorRatingBody}}
                        {{acceptorRatingBody}}
                        {{/if}}</td>
                    <td class='row4 col2' colspan="9">信用等级：{{#if acceptorCreditRating}}
                        {{acceptorCreditRating}}
                        {{/if}}</td>
                    <td class='row4 col3' colspan="6">评级到期日：{{#if acceptorRatingDueDate}}
                        {{acceptorRatingDueDate}}
                        {{/if}}</td>
                </tr>
            </tbody>
        </table> 
    </div>
</div>
<!-- 票据正面E -->

<!-- 票据背面S -->
<div class="billWarp2 billBack">
    <div class="tableWrap"> 
        <p>显示日期:<span>&nbsp 
            {{#if newDate}}
                {{newDate}}
            {{/if}}</span>
        </p>
        <img src="../../resources/img/userCenterMember/billLogo.png">
        <h3>{{billsTitleStatus type}}</h3>
        <ul class="billBase clearfix">
            <li>票据号码:<span>&nbsp {{#if billNo}}
                                           {{billNo}}
                                       {{/if}}</span></li>
        </ul>
        
        <table width="100%">
            <tbody>
                <tr class="firstRow">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                {{#each biList}}
                    <tr>
                        <td colspan="4" style="height: 32px;text-align: center;"> {{tEndorseType endorseType}} </td>
                    </tr>
                    <tr>
                        <td >背书人名称</td>
                        <td class="mosaic2" colspan="3">{{#if endorserName}}
                            {{endorserName}}
                            {{/if}}</td>
                    </tr>
                    <tr>
                        <td >被背书人名称</td>
                        <td class="mosaic2" colspan="3">{{#if endorseeName}}
                            {{endorseeName}}
                            {{/if}}</td>
                    </tr>
                    <tr>
                        <td>能否转让</td>
                        <td colspan="3">{{tIsCanTransfer transferEnable}}</td>
                    </tr>
                    <tr>
                        <td >背书日期</td>
                        <td colspan="3">{{#if endorserTm}}
                            {{endorserTm}}
                            {{/if}}</td>
                    </tr>
                    <!-- tr>
                        <td colspan="4"> 质押背书 </td>
                    </tr>
                    <tr>
                        <td>出质人名称</td>
                        <td colspan="3">{{#if pledgorName}}
                            {{pledgorName}}
                            {{/if}}</td>
                    </tr>
                    <tr>
                        <td>质权人名称</td>
                        <td colspan="3">{{#if pawneeName}}
                            {{pawneeName}}
                            {{/if}}</td>
                    </tr>
                    <tr>
                        <td>出质日期</td>
                        <td colspan="3">{{#if pledgeDate}}
                            {{pledgeDate}}
                            {{/if}}</td>
                    </tr>
                    <tr>
                        <td>质押解除日期</td>
                        <td colspan="3">{{#if releasePledgeDate}}
                            {{releasePledgeDate}}
                            {{/if}}</td>
                    </tr-->
            {{/each}}
            </tbody>
        </table> 
    </div>
</div>