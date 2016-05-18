package work.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.dao.JdbcCalculatorDao;
import work.dao.User;
import work.worker.WorkerCalculator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AnswerController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);
    private static final String COOKIE_CALCULATOR = "Calculator";
    private JdbcCalculatorDao jdbcCalculatorDao = new JdbcCalculatorDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("start doGet AnswerController");
        String question = request.getParameter("question");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(COOKIE_CALCULATOR);
        user.setExpression(question);
        try {
            jdbcCalculatorDao.setInfoUser(user);
        } catch (SQLException e) {
            logger.error("jdbcCalculatorDao SQLException");
            e.printStackTrace();
        }
        String answer = null;
        if (!question.isEmpty()) {
            WorkerCalculator workerCalculator = null;
            try {
                workerCalculator = new WorkerCalculator();
            } catch (Exception e) {
                logger.error("workerCalculator SQLException");
                e.printStackTrace();
            }
            workerCalculator.setInfoin(question);
            try {
                answer = workerCalculator.getInfoout();
            } catch (Exception e) {
                logger.error("workerCalculator SQLException");
                answer = "Invalid value, please try again";
            }
        } else {
            answer = "Invalid value, please try again";
        }
        answer = answer.toUpperCase();
        String info = "Question";
        info = info.toUpperCase();
        response.getWriter().println("<!DOCTYPE HTML>");
        response.getWriter().println("<html lang=\"en\">");
        response.getWriter().println("<head><meta charset=\"UTF-8\"><head>");
        response.getWriter().println("<html><body><h2>" + info + "</h2></body></html>");
        response.getWriter().println("<html><body><p>" + answer + "</p></body></html>");
        response.getWriter().println("<html><body><form action=\"question\" method=\"POST\"></body></html>");
        response.getWriter().println("<html><body><input type=\"submit\" value=\"try again\"/></body></html>");


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start doPost AnswerController");
        doGet(request, response);
    }
}

