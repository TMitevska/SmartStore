package com.tamara.masters.smartshop;

import com.tamara.masters.smartshop.strings.Translator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class productsController {



    @RequestMapping( value = "/products" , method = RequestMethod.GET)
    public String productList(Map<String, Object> model, @RequestParam("userId") String userid,  @RequestParam("roleId") String role) {

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
        model.put("newPredictions",Translator.newPredictions);
        model.put("deleteProducts",Translator.deleteProducts);
        model.put("deletePredictions",Translator.deleteSales);
        model.put("newProducts",Translator.newProducts);
        model.put("newFile",Translator.newFile);
        model.put("delete",Translator.delete);
        model.put("choose",Translator.choose);
        model.put("submit",Translator.submit);
        model.put("errorDatabaseConnection", Translator.errorDatabaseConnection);
        model.put("error", Translator.error);
        model.put("successDelete", Translator.successDelete);
        model.put("predict","Предвидување на количина на продадени производи");
        model.put("resources","Анализа на ресурси");
        model.put("statistics",Translator.statistics);
        return "products";
    }

    @RequestMapping( value = "/products" , method = RequestMethod.POST)
    public String productListPost(Map<String, Object> model, @RequestParam("userId") String userid,  @RequestParam("roleId") String role) {

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
        model.put("newPredictions",Translator.newPredictions);
        model.put("deleteProducts",Translator.deleteProducts);
        model.put("deletePredictions",Translator.deleteSales);
        model.put("newProducts",Translator.newProducts);
        model.put("newFile",Translator.newFile);
        model.put("delete",Translator.delete);
        model.put("choose",Translator.choose);
        model.put("submit",Translator.submit);
        model.put("errorDatabaseConnection", Translator.errorDatabaseConnection);
        model.put("error", Translator.error);
        model.put("successDelete", Translator.successDelete);
        model.put("predict","Предвидување на количина на продадени производи");
        model.put("resources","Анализа на ресурси");
        model.put("statistics",Translator.statistics);
        return "products";
    }

}
