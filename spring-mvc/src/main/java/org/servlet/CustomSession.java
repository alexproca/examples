package org.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by alex on 26.06.2014.
 */
public class CustomSession extends HttpServletRequestWrapper {

    public class MyHttpSession implements javax.servlet.http.HttpSession {
        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int interval) {

        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public Object getValue(String name) {
            return null;
        }

        @Override
        public Enumeration getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public void putValue(String name, Object value) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public void removeValue(String name) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }

        // and implement all the methods

    }

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public CustomSession(HttpServletRequest request) {
        super(request);
    }

    HttpSession s = new MyHttpSession();

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    @Override
    public HttpSession getSession(boolean create) {
        return ((HttpServletRequest)getRequest()).getRequestedSessionId();

        DateFormat
    }

    public Properties getProperties(String file) throws IOException {
        InputStream fileStream = StreamsBestPractices.class.getResourceAsStream(file);
        Properties props = new Properties();

        props.load(fileStream);
        lastCopy.load(fileStream);

        fileStream.close();

        return props;
    }



}
