package org.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by alex on 26.06.2014.
 */
public class SessionFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        //Get the IP address of client machine.
        String ipAddress = request.getRemoteAddr();

        //Log the IP address and current timestamp.
        System.out.println("IP "+ipAddress + ", Time "
                + new Date().toString());

        request.getRequestedSessionId()

        chain.doFilter(new CustomSession(request), res);
    }

    @Override
    public void destroy() {

    }
}
