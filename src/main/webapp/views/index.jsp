<%@ page import="com.alexandra.HInvent.entities.Cabinet" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alexandra.HInvent.entities.Item" %>
<%@ page import="com.alexandra.HInvent.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: Alexandra
  Date: 22.12.2019
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/left-nav-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
    <title>HInvent</title>
  </head>
  <body>
    <%
      int userType = (int) request.getAttribute("user_type");
      String pageType = (String) request.getAttribute("page_type");
    %>
    <input type="checkbox" id="nav-toggle" hidden>
    <nav class="nav">
      <label for="nav-toggle" class="nav-toggle" onclick></label>
      <h2 class="logo">
        <div>HInvent</div>
      </h2>
      <ul>
        <%
          out.println("<li><a href=\"/\">Список кабинетов</a>");
          if (userType == 0) {
            out.println("<li><a href=\"/sign_up\">Регистрация пользователя</a>");
            out.println("<li><a href=\"/create_cab\">Добавить кабинет</a>");
          } else {
            out.println("<li><a href=\"/all_items\">Оборудование</a>");
            out.println("<li><a href=\"/debt\">Недостача</a>");
          }
          out.println("<li><a href=\"/sign_out\">Выход</a>");
        %>
      </ul>
      <footer style="position: fixed; bottom: 0; text-align: left">
        <div>Шуянова Родимов Полянская</div>
        <div>ИКПИ-71</div>
      </footer>
    </nav>
    <main role="main">
      <article>
        <%
          if (pageType == null) {
            return;
          }

          out.println("<div class=\"w3-container w3-center w3-margin-bottom w3-padding\">");

          if (pageType.equals("index") || !pageType.equals("sign_up")) {
//            List<Cabinet> cabinets = (List<Cabinet>) request.getAttribute("cabinets");
//
//            if (cabinets == null) {
//              return;
//            }

//            for (Cabinet cabinet : cabinets) {
//              out.println("<div class=\"w3-card-4 w3-left-align\" style=\"margin-left: 25%; margin-right: 25%;\">");
//              out.println("<div style=\"margin: 5%;\">");
//
//              out.println("<br>");
//
//              out.println("<div><b>Имя:</b> " + cabinet.getName() + "</div>");
//              out.println("<div><b>Описание:</b> " + cabinet.getDescription() + "</div>");
//
//              out.println("<br>");
//
//              out.println("<div class=\"w3-center\">");
//
//              out.println("<button class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\""
//                      + " onclick=\"location.href='/cab?id="
//                      + cabinet.getId() + "'\">Оборудование</button><br>");
//
//              if (userType == 0) {
//                out.println("<button class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\""
//                        + " onclick=\"location.href='/add_item?id="
//                        + cabinet.getId() + "'\">Добавить оборудование</button><br>");
//
//                out.println("<button class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\""
//                        + " onclick=\"location.href='/edit_cab?id="
//                        + cabinet.getId() + "'\">Редактировать кабинет</button><br>");
//              }
//
//              out.println("</div>");
//
//              out.println("</div>");
//              out.println("</div>");
//            }

            if (true /*|| cabinets.isEmpty()*/) {
              out.println("<div class=\"w3-card-4 w3-left-align\" style=\"margin-left: 25%; margin-right: 25%;\">");
              out.println("<div style=\"margin: 5%;\">");

              out.println("<br>");

              out.println("<div class=\"w3-center\"><b>Пусто</b></div>");

              out.println("<br>");

              out.println("</div>");
              out.println("</div>");
            }

          } else if (pageType.equals("show_items")) {
            List<Item> items = (List<Item>) request.getAttribute("items");

            if (items == null) {
              return;
            }

            for (Item item : items) {
              out.println("<div class=\"w3-card-4 w3-left-align\" style=\"margin-left: 25%; margin-right: 25%;\">");
              out.println("<div style=\"margin: 5%;\">");
              out.println("<div class = \"w3-center\"><img src=\"" + item.getPicture() + "\" \n"
                      + "  width=\"300\" height=\"200\" alt=\"ad\"></div>");
              out.println("<div><b>Имя:</b> " + item.getName() + "</div>");
              out.println("<div><b>Стоимость:</b> " + item.getCoast() + "</div>");
              out.println("<div><b>Ответственный:</b> " + item.getUser().getSecondName() + " " + item.getUser().getFirstName() + "</div>");
              out.println("<div><b>Кабинет:</b> " + item.getCabinet().getName() + "</div>");
              out.println("<div><b>Статус:</b> " + item.getStatus() + "</div>");

              out.println("<br>");

              out.println("<div class=\"w3-center\">");

              if (userType == 0) {
                if (item.getStatus().equals("В эксплуатации")) {
                  out.println("<button class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\""
                          + " onclick=\"location.href='/set_debt?id="
                          + item.getId() + "'\">Недостача</button><br>");
                }
                out.println("<button class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\""
                        + " onclick=\"location.href='/remove_item?id="
                        + item.getId() + "'\">Списать</button><br>");
              }

              out.println("</div>");

              out.println("</div>");
              out.println("</div>");
            }

            if (items.isEmpty()) {
              out.println("<div class=\"w3-card-4 w3-left-align\" style=\"margin-left: 25%; margin-right: 25%;\">");
              out.println("<div style=\"margin: 5%;\">");

              out.println("<br>");

              out.println("<div class=\"w3-center\"><b>Пусто</b></div>");

              out.println("<br>");

              out.println("</div>");
              out.println("</div>");
            }

          } else if (pageType.equals("add_item")) {

            List<User> users = (List<User>) request.getAttribute("users");
            Cabinet cabinet = (Cabinet) request.getAttribute("cabinet");

            if (users == null || cabinet == null) {
              return;
            }

            out.println("<div class=\"w3-card-4\" style=\"margin-left: 25%; margin-right: 25%;\">");
            out.println("  <div style=\"margin-top: 1%; margin-bottom: 1%;\">");
            out.println("    <form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">");
            out.println("<br><div>Добавить в кабинет " + cabinet.getName() + "</div><br>");
            out.println("      <label>Ответственный:");
            out.println("        <br><select class=\"w3-input w3-animate-input w3-border w3-round-large\" name=\"user_id\">");
            for (User user : users) {
              out.print("        <option value=\"" + user.getId() + "\">");
              out.print(user.getSecondName() + " " + user.getFirstName());
              out.println("        </option>");
            }
            out.println("        </select><br>");
            out.println("      </label>");
            out.println("      <label>Имя:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"name\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Стоимость:");
            out.print("<input required type=\"number\" min=\"1\" max=\"3200000\" name=\"coast\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Изображение:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"pic\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("<input hidden type=\"text\" name=\"cabinet_id\" value=" + "\"" + cabinet.getId() + "\">");
            out.println("      <div class=\"w3-center\">");
            out.println("<button type=\"submit\" class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\">Отправить</button>");
            out.println("      </div>");
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

          } else if (pageType.equals("sign_up")) {

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

            out.println("<div class=\"w3-card-4\" style=\"margin-left: 25%; margin-right: 25%;\">");
            out.println("  <div style=\"margin-top: 1%; margin-bottom: 1%;\">");
            out.println("    <form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">");
            out.println("<br><div>Регистрация</div><br>");
            out.println("      <label>Имя:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"first_name\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Фамилия:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"second_name\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Email:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"email\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Пароль:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"password\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <div class=\"w3-center\">");
            out.println("<button type=\"submit\" class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\">Отправить</button>");
            out.println("      </div>");
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

          } else if (pageType.equals("create_cabinet")) {

            out.println("<div class=\"w3-card-4\" style=\"margin-left: 25%; margin-right: 25%;\">");
            out.println("  <div style=\"margin-top: 1%; margin-bottom: 1%;\">");
            out.println("    <form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">");
            out.println("<br><div>Добавление кабинета</div><br>");
            out.println("      <label>Имя:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"name\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Описание:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"description\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <div class=\"w3-center\">");
            out.println("<button type=\"submit\" class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\">Отправить</button>");
            out.println("      </div>");
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

          } else if (pageType.equals("edit_cabinet")) {

            Cabinet cabinet = (Cabinet) request.getAttribute("cabinet");

            if (cabinet == null) {
              return;
            }

            out.println("<div class=\"w3-card-4\" style=\"margin-left: 25%; margin-right: 25%;\">");
            out.println("  <div style=\"margin-top: 1%; margin-bottom: 1%;\">");
            out.println("    <form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">");
            out.println("<br><div>Редактирование кабинета</div><br>");
            out.println("      <label>Имя:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"name\" ");
            out.print("value=\"" + cabinet.getName() + "\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("      <label>Описание:");
            out.print("<input required maxlength=\"255\" type=\"text\" name=\"description\" ");
            out.print("value=\"" + cabinet.getDescription() + "\" ");
            out.println("class=\"w3-input w3-animate-input w3-border w3-round-large\"><br>");
            out.println("      </label>");
            out.println("<input hidden type=\"text\" name=\"cabinet_id\" value=" + "\"" + cabinet.getId() + "\">");
            out.println("      <div class=\"w3-center\">");
            out.println("<button type=\"submit\" class=\"w3-btn w3-blue w3-round-large w3-margin-bottom\">Отправить</button>");
            out.println("      </div>");
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

          }

          out.println("</div>");
        %>
      </article>
    </main>
  </body>
</html>
