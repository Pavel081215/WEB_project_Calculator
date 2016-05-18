package work.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.dao.User;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    private static final String COOKIE_NAME = "counter";
    private static final String COOKIE_CALCULATOR = "Calculator";
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start doPost LoginServlet");

        doGet(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start doGet LoginServlet");
        String result = request.getParameter("userInfo");
        if (result.isEmpty()) {
            response.sendRedirect("firstYES.html");
        } else {
            try {
                Date Current_Date = new Date();
                String userInfo = request.getParameter("userInfo");
                Cookie fromClient = null;
                HttpSession session = request.getSession();
                User user = new User();
                user.setUsername(userInfo);
                user.setTime(Current_Date.toString());
                session.setAttribute(COOKIE_CALCULATOR, user);
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (COOKIE_NAME.equals(cookie.getName())) {
                            fromClient = cookie;
                            break;
                        }
                    }
                }
                if (fromClient == null) {
                    response.addCookie(new Cookie(COOKIE_NAME, "" + 1));
                } else {
                    int visitCount = Integer.valueOf(fromClient.getValue());
                    response.addCookie(new Cookie(COOKIE_NAME, "" + (visitCount + 1)));
                }
                response.sendRedirect("test.html");

            } catch (Throwable theException) {
                theException.printStackTrace();
            }
        }
    }

}
