package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.ProgressBeans;

public class ProgressDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/python_ex";
    private static final String USER = "root";
    private static final String PW = System.getenv("CHECKLIST_DB_PASSWORD");

    /** 特定ユーザーの進捗を取得 */
    public static List<ProgressBeans> findByUserId(int userId) {
        List<ProgressBeans> progressList = new ArrayList<>();
        doDriver();

        String sql = "SELECT question_no, user_id, is_completed FROM progress WHERE user_id = ? ORDER BY question_no ASC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    progressList.add(new ProgressBeans(
                        rs.getString("question_no"),
                        rs.getInt("user_id"),
                        rs.getBoolean("is_completed")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressList;
    }

    /** 進捗保存・更新 */
    public static boolean saveOrUpdateProgress(String questionNo, int userId, boolean isCompleted) {
        boolean result = false;
        doDriver();

        String sql = "INSERT INTO progress (question_no, user_id, is_completed) VALUES (?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE is_completed = VALUES(is_completed)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, questionNo);
            ps.setInt(2, userId);
            ps.setBoolean(3, isCompleted);

            result = (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /** 全員の進捗Mapを取得（キー: "userId_questionNo"） */
    public static Map<String, Boolean> findAllProgressMap() {
        Map<String, Boolean> progressMap = new HashMap<>();
        doDriver();

        String sql = "SELECT user_id, question_no, is_completed FROM progress";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String key = rs.getInt("user_id") + "_" + rs.getString("question_no");
                progressMap.put(key, rs.getBoolean("is_completed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressMap;
    }

    /** 全課題番号のリストを取得 */
    public static List<String> getAllQuestionNumbers() {
        List<String> questionList = new ArrayList<>();
        doDriver();

        String sql = "SELECT DISTINCT question_no FROM progress ORDER BY question_no ASC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                questionList.add(rs.getString("question_no"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionList;
    }
    
    /**
     * 新しい課題番号を追加する
     */
    public static void addQuestion(int userId, String questionNo) {
        doDriver();

        String sql = "INSERT IGNORE INTO progress (question_no, user_id, is_completed) VALUES (?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PW);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, questionNo);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void doDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBCドライバーが読み込めません");
        }
    }
}