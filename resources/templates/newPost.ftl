<#import "lib/main.ftl" as main>

<@main.main>
    <form action="/post/" method="post">
        <p><input type="text" name="title" maxlength="64" required/></p>
        <p><textarea name="content" required></textarea></p>
        <p><input type="text" name="author" maxlength="64"/></p>
        <p><input type="text" name="lang" maxlength="2"/></p>
        <p><input type="text" name="tags"/></p>
        <p><input type="submit"></p>
    </form>
</@main.main>