<#import "lib/main.ftl" as main>

<@main.main>
  <div>
    <#list page.posts as post>
        <p>${post.title}</p>
    <#else>
        <p>No posts</p>
    </#list>
  </div>

  <a <#if page.page != 0>href="/${page.page - 1}"</#if>>prev</a>
  <a <#if page.hasNextPage>href="/${page.page + 1}"</#if>>next</a>
</@main.main>