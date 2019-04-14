<#import "lib/main.ftl" as main>

<@main.main>
  <div class="container">
    <#list page.posts as post>
            <div class="card my-3">
              <div class="card-body">
                <h3 class="card-title">${post.title}</h3>
                <#if post.author??>
                    <h6 class="card-subtitle mb-2 text-muted">${post.author}</h6>\
                </#if>
                <p class="card-text">${post.content}</p>
                <a href="/post/${post.id}" class="card-link stretched-link">Read ></a>
              </div>
            </div>
    <#else>
        <div class="alert alert-primary my-3" role="alert">
          Here's empty. ¯\_(ツ)_/¯
        </div>
    </#list>
  </div>

  <nav aria-label="Page navigation example">
    <ul class="pagination text-center justify-content-center">
      <li class="page-item"><a class="page-link" <#if page.page != 0>href="/${page.page - 1}"</#if>>Previous</a></li>
      <li class="page-item"><a class="page-link" <#if page.hasNextPage>href="/${page.page + 1}"</#if>>Next</a></li>
    </ul>
  </nav>
</@main.main>