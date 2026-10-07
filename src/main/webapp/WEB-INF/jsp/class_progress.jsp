<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>【共有】クラス全員の進捗一覧表</title>
<style>
    body { font-family: sans-serif; margin: 20px; }
    .header-box { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #ccc; padding-bottom: 10px; margin-bottom: 20px; }
    table.matrix-table { border-collapse: collapse; margin-top: 15px; }
    table.matrix-table th, table.matrix-table td { border: 1px solid #666; padding: 8px 12px; text-align: center; }
    .my-column { background-color: #fff3cd; }
    .msg-success { color: green; font-weight: bold; margin-bottom: 10px; }
    
/* ボタンエリアのスタイル（左と右端に振り分け） */
    .button-group { 
        margin-top: 15px; 
        display: flex; 
        justify-content: space-between; 
        align-items: center; 
    }
    .btn-save { padding: 8px 20px; background-color: #28a745; color: white; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
    .btn-save:hover { background-color: #218838; }
    .btn-add { padding: 8px 16px; background-color: #17a2b8; color: white; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
    .btn-add:hover { background-color: #138496; }

    /* モーダルダイアログのスタイル */
    .modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); justify-content: center; align-items: center; }
    .modal-content { background: white; padding: 20px; border-radius: 5px; width: 300px; }
</style>
</head>
<body>

    <div class="header-box">
        <h2>【共有】クラス全員の進捗一覧表</h2>
        <div>
            ログイン中: <strong><c:out value="${userBeans.username}" /></strong> さん
            <a href="ChangePasswordServlet" style="margin-left: 15px;">パスワード変更</a>
            <a href="LogoutServlet" style="margin-left: 15px;">ログアウト</a>
        </div>
    </div>

    <p style="color: #666;">（※自分の列のみチェックの変更・保存が可能です）</p>

    <c:if test="${not empty message}">
        <p class="msg-success"><c:out value="${message}" /></p>
    </c:if>

    <!-- 進捗更新用のフォーム -->
    <form action="ClassProgressServlet" method="post">
        <table class="matrix-table">
            <thead>
                <tr style="background-color: #e6f2ff;">
                    <th>課題番号</th>
                    <c:forEach var="user" items="${usersList}">
                        <th class="${user.id == userBeans.id ? 'my-column' : ''}">
                            <c:out value="${user.username}" />
                            <c:if test="${user.id == userBeans.id}"><br>(自分)</c:if>
                        </th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="q" items="${questionList}">
                    <tr>
                        <td style="font-weight: bold;"><c:out value="${q}" /></td>
                        <c:forEach var="user" items="${usersList}">
                            <c:set var="key" value="${user.id}_${q}" />
                            <c:set var="isChecked" value="${progressMap[key]}" />
                            
                            <td class="${user.id == userBeans.id ? 'my-column' : ''}">
                                <c:choose>
                                    <c:when test="${user.id == userBeans.id}">
                                        <input type="checkbox" name="completedQuestions" value="${q}"
                                               <c:if test="${isChecked}">checked</c:if> />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" <c:if test="${isChecked}">checked</c:if> disabled />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

	<!-- ボタン配置エリア（左：保存 / 右端：課題追加） -->
        <div class="button-group">
            <input type="submit" value="自分の進捗を保存する" class="btn-save">
            <button type="button" class="btn-add" onclick="openModal()">課題番号を追加する</button>
        </div>
        
    </form>

    <!-- 課題追加用モーダルダイアログ -->
    <div id="addModal" class="modal-overlay">
        <div class="modal-content">
            <h3>新しい課題を追加</h3>
            <form action="AddQuestionServlet" method="post">
                <p>課題番号 (例: Q03):</p>
                <input type="text" name="questionNo" required style="width: 100%; padding: 6px; box-sizing: border-box; margin-bottom: 15px;">
                <div style="text-align: right;">
                    <button type="button" onclick="closeModal()" style="margin-right: 5px;">キャンセル</button>
                    <button type="submit" class="btn-add">追加</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openModal() {
            document.getElementById('addModal').style.display = 'flex';
        }
        function closeModal() {
            document.getElementById('addModal').style.display = 'none';
        }
    </script>

</body>
</html>