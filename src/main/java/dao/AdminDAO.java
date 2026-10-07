package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UserBeans;

public class AdminDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/python_ex";
    private static final String USER = "root";
    private static final String PW = System.getenv("CHECKLIST_DB_PASSWORD");

    /** 全ユーザーを取得 */
    public static List<UserBeans> findAllUsers() {
        List<UserBeans> userList = new ArrayList<>();
        doDriver();

        // role をSQLから除外
        String sql = "SELECT id, username, email, password FROM user ORDER BY id ASC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                userList.add(new UserBeans(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static void doDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBCドライバーが読み込めません");
        }
    }
}