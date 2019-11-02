package com.tamara.masters.smartshop;

import com.tamara.masters.smartshop.strings.Translator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
@Controller
public class registerController {
    @RequestMapping( value = "/register" , method = RequestMethod.GET)
    public String register(Map<String, Object> model) {

        model.put("title", Translator.applicationName);
        model.put("name", Translator.username);
        model.put("password", Translator.password);
        model.put("address", Translator.address);
        model.put("email", Translator.email);
        model.put("register", Translator.register);
        model.put("login", Translator.login);
        model.put("phone", Translator.phone);
        model.put("error", Translator.error);
        model.put("emailerrormessage", Translator.emailerrormessage);
        model.put("creator", Translator.creator);
        model.put("owner", Translator.owner);
        model.put("acountant", Translator.acountant);
        model.put("employee", Translator.employee);
        model.put("role", Translator.role);

        return "registerForm";
    }


}
