<%--
  Created by IntelliJ IDEA.
  User: Alexandra
  Date: 22.12.2019
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>
<body>
    <div class="w3-container w3-blue w3-opacity w3-center">
        <div>Осуществите вход</div>
    </div>

    <div class="w3-container w3-padding">
        <%
            String message = (String) request.getAttribute("message");

            if (message != null) {
                out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\"" +
                        " style=\"margin-left: 25%; margin-right: 25%;\">\n" +
                        "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                        "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border" +
                        " w3-border-red w3-hover-border-grey\">×</span>\n" +
                        "   <h5>" + message + "</h5>\n" +
                        "</div>");
            }
        %>
        <div class="w3-card-4" style="margin-left: 25%; margin-right: 25%;">
            <form method="post" class="w3-selection w3-light-grey w3-padding">
                <label>Email:
                    <input required maxlength="255" type="text" name="email"
                           class="w3-input w3-animate-input w3-border w3-round-large"><br>
                </label>
                <label>Пароль:
                    <input required maxlength="255" type="password" name="password"
                           class="w3-input w3-animate-input w3-border w3-round-large"><br>
                </label>
                <div class="w3-center">
                    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Отправить</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
