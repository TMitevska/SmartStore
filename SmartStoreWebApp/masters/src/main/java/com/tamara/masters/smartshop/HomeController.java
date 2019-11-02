package com.tamara.masters.smartshop;

import com.tamara.masters.smartshop.strings.Translator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class HomeController {
    @RequestMapping( value = "/home" , method = RequestMethod.GET)
    public String remoteControl(Map<String, Object> model, @RequestParam("userId") String userid, @RequestParam("roleId") String role) {

        model.put("title", Translator.applicationName);
        model.put("logout", Translator.logout);
        model.put("products", Translator.products);
        model.put("invoiceList", Translator.invoiceList);
        model.put("payslipList", Translator.payslipList);
        model.put("createinvoice", Translator.createinvoice);
        model.put("remoteControl", Translator.remoteControl);
        model.put("userid", userid);
        model.put("creator", Translator.creator);
        model.put("roleId",role);
        model.put("home",Translator.HOME);
        model.put("photos",Translator.photos);
        model.put("efficient","Ефикасност");
        model.put("performance","Добри перформанси");
        model.put("management","Менаџмент со ресурси");
        model.put("predict","Предвидување на количина на продадени производи");
        model.put("resources","Анализа на ресурси");
        model.put("statistics",Translator.statistics);
        return "index";
    }
}

