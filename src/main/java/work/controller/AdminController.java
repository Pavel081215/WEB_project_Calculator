package work.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.dao.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Pavel on 18.05.2016.
 */
public class AdminController extends HttpServlet {

    private static final String COOKIE_NAME = "counter";
    private static final String COOKIE_CALCULATOR = "Calculator";
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("start doGet AdminController");
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(COOKIE_CALCULATOR);

        int temp = 0;
        String fromCalculator = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    temp = Integer.valueOf(cookie.getValue());
                    break;
                }
            }
        }
        String fromClient = Integer.toString(temp);
        String expression;
        String time;
        String username;
        if (user != null) {
            expression = user.getExpression();
            time = user.getTime();
            username = user.getUsername();
        } else {
            username = "                we  ";
            expression = "             have not ";
            time = "                    met";

        }

            response.getWriter().println("<!DOCTYPE HTML>");
        response.getWriter().println("<html lang=\"en\">");
        response.getWriter().println(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        response.getWriter().println("<html><body> <h2> You visit this  page " + fromClient + " </h2></body></html>");

        response.getWriter().println("<html><body> <h2> YOU </h2></body></html>");
        response.getWriter().println("<html><body><h1>" + username + "</h1></body></html>");
        response.getWriter().println("<html><body><h1>" + expression + "</h1></body></html>");
        response.getWriter().println("<html><body><h1>" + time + "</h1></body></html>");

        response.getWriter().println("<html><body><form action=\"test.html\" method=\"POST\"></body></html>");
        response.getWriter().println("<html><body><input type=\"submit\" value=\"try again\"/></body></html>");


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start doPost AdminController");
        doGet(request, response);
    }
}
