{{#each data}}
<li>
    <img src="/modules/cms/CmsArticleAction/read.htm?path={{picPath}}" />
    <div>
        <p class="mTitle">
            <a href="/AboutUs/detail.htm?id={{id}}">{{title}}</a>
        </p>
        <h3>{{modifyTime}}</h3>
        <p class="mContent">{{introduction}}</p>
    </div>
</li>
{{/each}}