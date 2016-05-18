package work.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class QuestionController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("start doGet QuestionController");
        response.sendRedirect("test.html");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start doPost QuestionController");
        doGet(request, response);
    }
}