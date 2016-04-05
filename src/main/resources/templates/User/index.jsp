<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:replace="_nw_temp :: header ('PRODUCT INDEX')">
</head>
<body>

  <div class="container">
    <div class="page-header">
      <h1 th:inline="text">
        [[${header.title}]]
        <small th:text="${header.subtitle}">subtitle</small>
      </h1>
    </div>

    <div th:replace="_nw_temp :: nav"></div>

  	User Page
    <div th:replace="_nw_temp :: footer"></div>
  </div>

  <div th:include="_nw_temp :: script"></div>
</body>
</html>