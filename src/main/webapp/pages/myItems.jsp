<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>My Items</title>
    <link rel="stylesheet" type="text/css" href="/static/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/myItems.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/header.css" />
    <script src="/static/js/jQuery.js"></script>
    <script src="/static/js/items.js"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
</head>

<body>
    <sec:authorize access="isAuthenticated()">
        <input class="principal" type="hidden" value="<sec:authentication property="principal.username" />" />
    </sec:authorize>
    <header>
        <div class="header sticky">
            <span class="navigation">
                <a class="items-link" href="/">Items Board</a>
            </span>
            <span class="navigation">
                <a class="items-link " href="/item">Create Item</a>
            </span>
            <span class="logo"><img src="/static/logos/market.png" alt=""></span>
            <span class="user-info">
                <sec:authorize access="isAuthenticated()">
                    <form class="user-info" action="/logout" method="POST">
                        You logged as:
                        <sec:authentication property="principal.username" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button class="logout red-button button" type="submit">Logout</button>
                    </form>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <form action="/login" method="GET"><button class="login green-button button"
                            type="submit">Login</button></form>
                </sec:authorize>
            </span>
        </div>
    </header>
    <div class="flex-container content">
        <img class="spinner" src="/static/logos/hammer.gif" />
    </div>
</body>

</html>