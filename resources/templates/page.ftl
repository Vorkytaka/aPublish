<#import "lib/main.ftl" as main>

<@main.main>
    <#list page.posts as post>
            <div>
              <div>
                <h3>${post.title}</h3>
                <#if post.theme??>
                    <h6>${post.theme}</h6>
                </#if>
                <p>${post.content}</p>
                <p>${post.author}</p>
                <p>${post.createdDate?number_to_datetime}</p>
                <a href="/post/${post.id}">Read ></a>
              </div>
            </div>
    <#else>
        <div>
          Here's empty. ¯\_(ツ)_/¯
        </div>
    </#list>

    <#include "/lib/next-prev-arrow.ftl">
</@main.main>