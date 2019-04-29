<#import "lib/main.ftl" as main>

<@main.main>
    <#list page.posts as post>
        <div>
          <div>
            <h3>${post.title}</h3>
            <#if post.theme??>
                <h6>${post.theme}</h6>
            </#if>
            <#if post.tags?size != 0>
                <#list post.tags as tag>
                    <a
                        href="#"
                        style="
                            background-color: #333333;
                            color: #cccccc;
                            text-decoration: none;
                            padding: 0.2rem;
                        ">${tag}</a>
                </#list>
            </#if>
            <p>${post.content}</p>
            <p>${post.author}</p>
            <p>${post.createdDate?number_to_datetime}</p>
            <a href="/post/${post.id}">Read ></a>
          </div>
        </div>
    <#else>
        <div style="text-align:center;">
          <h3>Here's empty. ¯\_(ツ)_/¯</h3>
        </div>
    </#list>

    <#include "/lib/next-prev-arrow.ftl">
</@main.main>