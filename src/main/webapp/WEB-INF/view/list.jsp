<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/19
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="yearRate" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Title</title>
</head>
<body>
<div>
    <ul>
        <yearRate:forEach var="item" items="${list}">
            <li>"${item}"</li>
        </yearRate:forEach>
    </ul>
</div>

</body>
</html>
