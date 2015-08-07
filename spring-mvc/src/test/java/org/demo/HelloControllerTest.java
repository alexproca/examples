package org.demo;

import org.junit.Test;
import org.springframework.ui.ModelMap;

import static org.junit.Assert.*;

public class HelloControllerTest {

    @Test
    public void testPrintsHello() throws Exception {

        HelloController hc = new HelloController();

        ModelMap mm = new ModelMap();

        assertEquals("org/hello", hc.printWelcome(mm));

        assertEquals("Spring 3 MVC Hello world!", mm.get("message"));

    }
}