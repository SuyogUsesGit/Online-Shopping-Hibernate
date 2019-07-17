<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mj
  Date: 7/8/2019
  Time: 9:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Cart Contents</title>
    <style>
        body {
            text-align: center;
        }
        input {
            border: none;
        }
        table {
            margin: 0 auto 0 auto;
        }
        #lgout {
            float: right;
        }
        .total {
            margin-left: 40%;
        }
        #total {
            text-align: left;
            padding-left: 3%;
        }

    </style>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<form action="/InvalidateServlet" method="post">
    <input id="lgout" type="submit" value="Logout">
</form>
<h2 id="h2_1">${cartContentsMsg}</h2>

<form id="frm" method="post">
<table id="table1" border="2" cellpadding="10">
    <th>Item Name</th>
    <th>Quantity Added to Cart</th>
    <th>Price</th>

    <c:forEach var="cartBean" items="${viewCartItemsList}">
        <tr>
            <td>${cartBean.itemName}</td>
            <td>${cartBean.quantity}</td>
            <td>${cartBean.price}</td>
        </tr>
    </c:forEach>
</table><br><br>

    <div class="total">
        <b id="myBold">Total:</b><input id="total" name="total" value="${totalAmount}" readonly><br><br>
    </div>

    <input id="btn1" type="button" value="Continue Shopping" onclick="myFunc(this.id)"><br><br>
    <input id="btn2" type="button" value="Checkout" onclick="myFunc(this.id)"><br><br>
</form>



<script language="JavaScript">

    msg = "${cartContentsMsg}";
    if(msg.charAt(msg.length - 1) == '.')  {
        document.getElementById("table1").style.visibility = "hidden";
        document.getElementById("btn2").style.visibility = "hidden";
        document.getElementById("myBold").style.visibility = "hidden";
        document.getElementById("total").style.visibility = "hidden";
        document.getElementById("h2_1").style.color = "red";
    }

    function myFunc(val) {
        if (val == "btn1") document.getElementById("frm").action = "/WelcomeServlet";
        if (val == "btn2") document.getElementById("frm").action = "/CheckoutServlet";
        document.getElementById("frm").submit();

    }

    window.history.forward();
    function noBack() { window.history.forward(); }

</script>
<%
    if((session.getAttribute("userId") == null)) response.sendRedirect("index.jsp");
%>
</body>
</html>
