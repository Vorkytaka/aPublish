<#import "lib/main.ftl" as main>

<@main.main>

    <h1>${post.title}</h1>

    <#include "/lib/created-info.ftl">

    <#include "/lib/tags.ftl">

    <div>
        ${post.content}
    </div>

</@main.main>