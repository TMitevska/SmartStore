package com.tamara.masters.smartshop;

import com.tamara.masters.smartshop.strings.Translator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class CreateDocuments {
    @RequestMapping( value = "/createDocument" , method = RequestMethod.GET)
    public String createDocument(Map<String, Object> model, @RequestParam("userId") String userid, @RequestParam("roleId") String role) {
        model.put("title", Translator.applicationName);
        model.put("logout", Translator.logout);
        model.put("products", Translator.products);
        model.put("invoiceList", Translator.invoiceList);
        model.put("payslipList", Translator.payslipList);
        model.put("createinvoice", Translator.createinvoice);
        model.put("remoteControl", Translator.remoteControl);
        model.put("next", Translator.NEXT);
        model.put("customerCompanyName", Translator.customerCompanyName);
        model.put("customerCompanyDescription", Translator.customerCompanyDescription);
        model.put("numberOfProducts", Translator.numberOfProducts);
        model.put("INVOICE_NUMBER", Translator.INVOICE_NUMBER);
        model.put("userId", userid);
        model.put("creator", Translator.creator);
        model.put("errorDatabaseConnection", Translator.errorDatabaseConnection);
        model.put("error", Translator.error);
        model.put("roleId",role);
        model.put("home",Translator.HOME);
        model.put("resources","Анализа на ресурси");
        model.put("efficient","Ефикасност");
        model.put("performance","Добри перформанси");
        model.put("management","Менаџмент со ресурси");
        model.put("statistics",Translator.statistics);
        model.put("predict","Предвидување на количина на продадени производи");

        return "createDocumentForm";
    }

    @RequestMapping( value = "/addproductsinDocument" , method = RequestMethod.GET)
    public String addproductsinDocument(Map<String, Object> model, @RequestParam("userId") String userid,@RequestParam("customerCompanyName") String customerCompanyName,
                                        @RequestParam("customerCompanyDescription") String customerCompanyDescription, @RequestParam("INVOICE_NUMBER") String documentnumber, @RequestParam("numberOfProducts") int num, @RequestParam("roleId") String role) {
        model.put("title", Translator.applicationName);
        model.put("logout", Translator.logout);
        model.put("products", Translator.products);
        model.put("invoiceList", Translator.invoiceList);
        model.put("payslipList", Translator.payslipList);
        model.put("createinvoice", Translator.createinvoice);
        model.put("remoteControl", Translator.remoteControl);
        model.put("next", Translator.CREATE);
        model.put("customerCompanyName", customerCompanyName);
        model.put("customerCompanyDescription", customerCompanyDescription);
        model.put("numberOfProducts", num);
        model.put("INVOICE_NUMBER", documentnumber);
        model.put("userId", userid);
        model.put("productOrderNumber", Translator.productOrderNumber);
        model.put("productName", Translator.productName);
        model.put("productType", Translator.productType);
        model.put("productNumber", Translator.productNumber);
        model.put("productValue", Translator.productValue);
        model.put("creator", Translator.creator);
        model.put("roleId",role);
        model.put("home",Translator.HOME);
        model.put("resources","Анализа на ресурси");
        model.put("efficient","Ефикасност");
        model.put("performance","Добри перформанси");
        model.put("management","Менаџмент со ресурси");
        model.put("predict","Предвидување на количина на продадени производи");
        model.put("statistics",Translator.statistics);
        return "addDocumentProductsForm";
    }
}
