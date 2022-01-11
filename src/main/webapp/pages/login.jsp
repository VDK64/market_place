<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/static/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/login.css" />
    <script src="/static/js/jQuery.js"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
</head>

<body>
    <div class="container">
        <div class="form">
            <img class="logo" src="/static/logos/market.png" />
            <form method="POST" action="/login">
                <div class="input-data">
                    <p>Login:</p>
                    <input class="input" name="username" type="text" />
                    <p>Password:</p>
                    <input class="input" name="password" type="password" />
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button class="navigation-buttons green-button button" type="submit">Sign in</button>
            </form>
            <form action="/registration" method="GET">
                <button class="navigation-buttons green-button button" type="submit">Sign up</button>
            </form>
            <form action="/" method="GET">
                <button class="navigation-buttons green-button button" type="submit">Enter as guest</button>
            </form>
            <% if (request.getParameter("error") != null) {
                    out.println("<span class=\"error\">Bad Credentials!</span>");
                }
                if (request.getParameter("success") != null) {
                    out.println("<span class=\"success\">Registration was successfully!</span>");
                } 
                 %>
        </div>
    </div>
</body>

</html>