# チェックリスト（サーブレット＋ＳＱＬ）

* 開発背景：
複数人で表ファイルを共有、可視化するため、例としてチェックリストをJava Servlet + SQL で作成。
<img width="311" height="151" alt="image" src="https://github.com/user-attachments/assets/fd9cebf5-06a7-4942-b4cc-878ac314abd5" />

* 開発環境：
  Eclips, MySQL
  
* 主な機能：
  各人をログインベースとして識別し、自身の列のみ変更できる権限を付与。
  講師は課題の行を追加し各人の進行状況（チェックリスト）を可視化、確認できる。
  
* データベース構成
　データベースに各人のチェックオンオフを記録。

* 起動方法

①移行先のMySQLに接続し、空の python_ex データベースを作成します。

CREATE DATABASE python_ex CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

②デモ用ＤＢ python_ex_backup.sql ファイルがあるフォルダでコマンドプロンプトを開き、移行先へインポートします。

mysql -u root -p python_ex < python_ex_backup.sql

### ◆サーブレット＆JSPの構造

excel/
 └── src/
     └── main/
           ├── java/
           │    ├── beans/
           │    │    ├── ProgressBeans.java
           │    │    └── UserBeans.java
           │    ├── controller/
           │    │    ├── AddQuestionServlet.java
           │    │    ├── ChangePasswordServlet.java
           │    │    ├── ClassProgressServlet.java
           │    │    ├── LoginServlet.java
           │    │    └── LogoutServlet.java
           │    ├── dao/
           │    │    ├── AdminDAO.java
           │    │    ├── LoginDAO.java
           │    │    └── ProgressDAO.java
           │    └── filter/
           │         └── CharacterEncodingFilter.java
           └── webapp/
                ├── login.jsp
                └── WEB-INF/
                     ├── web.xml
                     └── jsp/
                          ├── change_password.jsp
                          └── class_progress.jsp
```[cite: 1]
