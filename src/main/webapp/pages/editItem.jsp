<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Item</title>
    <link rel="stylesheet" type="text/css" href="/static/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/header.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/editItem.css" />
    <script src="/static/js/jQuery.js"></script>
    <script src="/static/js/editItem.js"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
</head>

<body>
    <header>
        <div class="header sticky">
            <span class="navigation">
                <a class="items-link" href="/">Items Board</a>
            </span>
            <span class="navigation">
                <a class="items-link " href="/myItems">My Items</a>
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
    <div class="container">
        <div class="form">
            <form method="post" enctype="multipart/form-data">
                <div class="input-data">
                    <p>Select image of item. (optional)</p>
                    <input type="file" id="itemImage" name="img" accept="image/*" ${disabled} />
                    <p>All fields are required!</p>
                    <p>Title:</p>
                    <input class="title dataForm" value="${item.title}" name="title" type="text" maxlength="30" required />
                    <p>Description:</p>
                    <input class="description dataForm tall" value="${item.description}" name="description" type="text" maxlength="200" required />
                    <p>Start price:</p>
                    <input class="start-price dataForm" value="${item.startPrice}" name="startPrice" type="number" step="0.01" required ${disabled} />
                    <p>Bid Inc:</p>
                    <input class="bid-inc dataForm" value="${item.bidInc}" name="bidIncrement" type="number" step="0.01" required ${disabled} />
                    <p>Stop date:</p>
                    <input class="stop-date dataForm" value="${stopDate}" name="stopDate" type="date" onkeyup="checkEmail()" required ${disabled} />
                    <p>Stop time:</p>
                    <input class="stop-time dataForm" value="${stopTime}" name="stopTime" type="time" required ${disabled} />
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button class="navigation-buttons green-button button" type="submit">OK</button>
            </form>
        </div>
    </div>
</body>

</html>