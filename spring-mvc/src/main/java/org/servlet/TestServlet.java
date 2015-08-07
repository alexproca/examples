package org.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by alex on 26.06.2014.
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Integer counter = (Integer)session.getAttribute("counter");

        resp.getWriter().println("Vasile a plecat la piata");
        resp.getWriter().println(session.getId());
        resp.getWriter().println(session.getClass().getName());
        resp.getWriter().println("Counter is " + counter);


        if(counter == null)
        {
            counter = 0;
        }
        else
        {
            counter += 1;
        }

        session.setAttribute("counter", counter);

    }
}
