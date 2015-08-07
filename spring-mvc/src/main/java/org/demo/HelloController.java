package org.demo;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/welcome")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Spring 3 MVC Hello world!");
        return "hello";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String printRestWelcome(ModelMap model)
    {
        model.addAttribute("currentTime", new DateTime().toString("dd-MM-YYYY HH:mm:ss"));
        return "rest";
    }

}