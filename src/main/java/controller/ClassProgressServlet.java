package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import beans.UserBeans;
import dao.AdminDAO;
import dao.ProgressDAO;

@WebServlet("/ClassProgressServlet")
public class ClassProgressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBeans userBeans = (UserBeans) session.getAttribute("userBeans");

        if (userBeans == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }

        List<UserBeans> usersList = AdminDAO.findAllUsers();
        List<String> questionList = ProgressDAO.getAllQuestionNumbers();
        Map<String, Boolean> progressMap = ProgressDAO.findAllProgressMap();

        request.setAttribute("usersList", usersList);
        request.setAttribute("questionList", questionList);
        request.setAttribute("progressMap", progressMap);

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/class_progress.jsp");
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

        String[] checkedArray = request.getParameterValues("completedQuestions");
        Set<String> checkedSet = new HashSet<>();
        if (checkedArray != null) {
            checkedSet.addAll(Arrays.asList(checkedArray));
        }

        List<String> allQuestions = ProgressDAO.getAllQuestionNumbers();
        for (String qNo : allQuestions) {
            boolean isCompleted = checkedSet.contains(qNo);
            ProgressDAO.saveOrUpdateProgress(qNo, userBeans.getId(), isCompleted);
        }

        session.setAttribute("message", "進捗状況を更新しました。");
        response.sendRedirect("ClassProgressServlet");
    }
}