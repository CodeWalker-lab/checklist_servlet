<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更</title>
<style>
    body { font-family: sans-serif; margin: 40px; }
    .form-box { width: 350px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
    .form-group input[type="password"] { width: 100%; padding: 8px; box-sizing: border-box; }
    .error-msg { color: red; margin-bottom: 15px; font-size: 0.9em; }
    .btn-submit { width: 100%; padding: 10px; background-color: #28a745; color: white; border: none; border-radius: 3px; font-weight: bold; cursor: pointer; }
    .btn-submit:hover { background-color: #218838; }
</style>
</head>
<body>

<div class="form-box">
    <h2>パスワード変更</h2>

    <c:if test="${not empty errorMsg}">
        <p class="error-msg"><c:out value="${errorMsg}" /></p>
    </c:if>

    <form action="ChangePasswordServlet" method="post">
        <div class="form-group">
            <label for="currentPassword">現在のパスワード</label>
            <input type="password" id="currentPassword" name="currentPassword" required>
        </div>
        <div class="form-group">
            <label for="newPassword">新しいパスワード</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div class="form-group">
            <label for="confirmPassword">新しいパスワード（確認用）</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <button type="submit" class="btn-submit">変更を保存</button>
    </form>
    
    <p style="margin-top: 15px; text-align: center;">
        <a href="ClassProgressServlet">進捗一覧表に戻る</a>
    </p>
</div>

</body>
</html>