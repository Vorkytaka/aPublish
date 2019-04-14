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

    <a <#if page.page != 0>href="/${page.page - 1}"</#if>>Previous</a>
    <a <#if page.hasNextPage>href="/${page.page + 1}"</#if>>Next</a>
</@main.main>