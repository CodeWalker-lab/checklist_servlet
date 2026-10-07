package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import beans.UserBeans;
import dao.ProgressDAO;

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBeans userBeans = (UserBeans) session.getAttribute("userBeans");

        if (userBeans == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String questionNo = request.getParameter("questionNo");

        if (questionNo != null && !questionNo.trim().isEmpty()) {
            // 新規課題番号を追加（自分の進捗未完了Stateとして登録）
            ProgressDAO.addQuestion(userBeans.getId(), questionNo.trim());
            session.setAttribute("message", "課題 「" + questionNo.trim() + "」 を追加しました。");
        }

        response.sendRedirect("ClassProgressServlet");
    }
}