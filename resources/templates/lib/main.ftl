<#macro main>
    <!doctype html>
    <html lang="en">
    <head>
        <title>${(title)!"aPublish"}</title>

        <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
        <link rel="stylesheet" href="/static/style.css">
    </head>
    <body>
        <div id="body">
            <#include "/lib/header.ftl">

            <div>
                <#nested>
            </div>
        </div>
    </body>
    </html>
</#macro>