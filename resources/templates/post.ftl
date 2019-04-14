<#import "lib/main.ftl" as main>

<@main.main>

    <h1>${post.title}</h1>

    <#if post.author??><p>${post.author}</p></#if>

    <div>
        ${post.content}
    </div>

</@main.main>