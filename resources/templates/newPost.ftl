<#import "lib/main.ftl" as main>

<@main.main>
    <form action="/post/" method="post">
        <div style="margin-bottom: 1rem;">
            <input
                type="text"
                name="title"
                maxlength="64"
                style="width:100%;"
                placeholder="Title"
                required/>
        </div>
        <div style="margin-bottom: 1rem;">
            <textarea
                name="content"
                style="width:100%; resize:vertical;"
                placeholder="Content"
                rows="10"
                required></textarea>
        </div>
        <div style="margin-bottom: 1rem;">
            <input
                type="text"
                name="author"
                maxlength="64"
                style="width:100%;"
                placeholder="Author (optional)"/>
        </div>
        <div style="margin-bottom: 1rem;">
            <input
                type="text"
                name="lang"
                maxlength="2"
                style="width:100%;"
                placeholder="Language (2-letter code) (optional)"/>
        </div>
        <div style="margin-bottom: 1rem;">
            <input
                type="text"
                name="tags"
                style="width:100%;"
                placeholder="Tags (comma separated) (optional)"/>
        </div>
        <div style="float: right;">
            <input
                type="submit"
                value="Post it!">
        </div>
    </form>
</@main.main>