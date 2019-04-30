<#list page.posts as post>
    <div>
      <div>
        <h3>${post.title}</h3>
        <#include "/lib/created-info.ftl">
        <#include "/lib/tags.ftl">
        <p>${post.content}</p>
        <a href="/post/${post.id}">Read ></a>
      </div>
    </div>
<#else>
    <div style="text-align:center;">
      <h3>Here's empty. ¯\_(ツ)_/¯</h3>
    </div>
</#list>

<#include "/lib/next-prev-arrow.ftl">