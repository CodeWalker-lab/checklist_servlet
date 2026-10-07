<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン - Python演習進捗管理</title>
<style>
    body { font-family: sans-serif; margin: 40px; }
    .login-box { width: 320px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
    .form-group input[type="text"], .form-group input[type="password"] {
        width: 100%; padding: 8px; box-sizing: border-box;
    }
    .error-msg { color: red; font-size: 0.9em; margin-bottom: 15px; }
    .btn-submit { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 3px; font-weight: bold; cursor: pointer; }
    .btn-submit:hover { background-color: #0056b3; }
</style>
</head>
<body>

<div class="login-box">
    <h2>ログイン</h2>

    <c:if test="${not empty errorMsg}">
        <p class="error-msg"><c:out value="${errorMsg}" /></p>
    </c:if>

    <form action="LoginServlet" method="post">
        <div class="form-group">
            <label for="email">メールアドレス</label>
            <input type="text" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">パスワード</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="btn-submit">ログイン</button>
    </form>
</div>

</body>
</html>