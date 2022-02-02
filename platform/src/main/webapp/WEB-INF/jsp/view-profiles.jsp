<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Books</title>
    <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Review</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${profiles}" var="profile">
        <tr>
            <td>${profile.id}</td>
            <td>${profile.name}</td>
            <td>${profile.review}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>