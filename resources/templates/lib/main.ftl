<#macro main>
    <!doctype html>
    <html lang="en">
    <head>
        <title>${(title)!"aPublish"}</title>

        <link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">

        <style>
            * {
               font-family: 'PT Sans', sans-serif;
            }
        </style>
    </head>
    <body>
        <#include "/lib/header.ftl">

        <div>
            <#nested>
        </div>
    </body>
    </html>
</#macro>