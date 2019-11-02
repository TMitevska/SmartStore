package com.tamara.masters.smartshop;

import java.util.Map;

import com.tamara.masters.smartshop.strings.Translator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class loginController {

    @RequestMapping( value = "/" , method = RequestMethod.GET)
    public String login(Map<String, Object> model) {

        model.put("title", Translator.applicationName);
        model.put("login", Translator.login);
        model.put("register", Translator.register);
        model.put("name", Translator.username);
        model.put("password", Translator.password);
        model.put("error", Translator.error);
        model.put("creator", Translator.creator);
        model.put("loginerrormessage", Translator.loginerrormessage);
        return "loginForm";
    }

}
