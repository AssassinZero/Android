{{#each this}}
<tr>
    <td>{{type}}</td>
    <td>{{billAttribute}}</td>
    <td>{{billNo}}</td>
    <td>{{moneyFor faceAmount}}</td>
    <td>{{splitDate dueDate}}</td>
    <td>{{adjustDays}}</td>
    <td>{{moneyFor originalMoney}}</td>
    <td>{{offers isDiscussPersonally quotationMethod yearRate discount}}</td>
</tr>
{{/each}}