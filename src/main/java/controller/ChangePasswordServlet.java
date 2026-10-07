package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import beans.UserBeans;
import dao.LoginDAO;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBeans userBeans = (UserBeans) session.getAttribute("userBeans");

        if (userBeans == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/change_password.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBeans userBeans = (UserBeans) session.getAttribute("userBeans");

        if (userBeans == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 新しいパスワードの入力確認チェック
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMsg", "新しいパスワードと確認用パスワードが一致しません。");
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/change_password.jsp");
            rd.forward(request, response);
            return;
        }

        // DBのパスワード更新処理呼び出し
        boolean isUpdated = LoginDAO.updatePassword(userBeans.getId(), currentPassword, newPassword);

        if (isUpdated) {
            session.setAttribute("message", "パスワードを変更しました。");
            response.sendRedirect("ClassProgressServlet");
        } else {
            request.setAttribute("errorMsg", "現在のパスワードが正しくありません。");
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/change_password.jsp");
            rd.forward(request, response);
        }
    }
}