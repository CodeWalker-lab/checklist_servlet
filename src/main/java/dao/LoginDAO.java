package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserBeans;

public class LoginDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/python_ex";
    private static final String USER = "root";
    private static final String PW = System.getenv("CHECKLIST_DB_PASSWORD");

    public static UserBeans findByEmailAndPassword(String email, String password) {
        UserBeans user = null;
        doDriver();

        // role をSQLから除外
        String sql = "SELECT id, username, email, password FROM user WHERE email = ? AND password = SHA2(?, 256)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new UserBeans(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    /**
     * パスワードの更新処理
     */
    public static boolean updatePassword(int userId, String currentPassword, String newPassword) {
        boolean isSuccess = false;
        doDriver();

        // 現在のパスワードが合致している場合のみ新パスワードに更新
        String sql = "UPDATE user SET password = SHA2(?, 256) WHERE id = ? AND password = SHA2(?, 256)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            ps.setString(3, currentPassword);

            int count = ps.executeUpdate();
            if (count > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public static void doDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBCドライバーが読み込めません");
        }
    }
}