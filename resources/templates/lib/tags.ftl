<div>

    <#if post.lang??>
        <a
            href="#"
            style="
                background-color: #777777;
                color: #dddddd;
                text-decoration: none;
                padding: 0.2rem;
            ">${post.lang}</a>
    </#if>

    <#if post.lang?? && post.tags?size != 0>
        |
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

</div>