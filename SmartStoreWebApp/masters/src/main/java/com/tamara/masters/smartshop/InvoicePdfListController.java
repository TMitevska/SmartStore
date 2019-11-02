package com.tamara.masters.smartshop;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;
import com.tamara.masters.smartshop.strings.Translator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Controller
public class InvoicePdfListController {
    private int productNumber=1;
    private static File fontFile = new File("C:\\Users\\Tamara\\Desktop\\Arsenal-Regular.otf");
    private static Document faktura;
    private static FontSelector fs;
    private static BaseFont unicode;
    private static PdfWriter fakturaWriter;
    private static documentTable table;

    @RequestMapping( value = "/invoicePdfList" , method = RequestMethod.GET)
    public String login(Map<String, Object> model, @RequestParam("userId") String userid, @RequestParam("roleId") String role) {

        model.put("title", Translator.applicationName);
        model.put("logout", Translator.logout);
        model.put("creator", Translator.creator);
        model.put("products", Translator.products);
        model.put("invoiceList", Translator.invoiceList);
        model.put("payslipList", Translator.payslipList);
        model.put("createinvoice", Translator.createinvoice);
        model.put("remoteControl", Translator.remoteControl);
        model.put("invoice", Translator.invoice);
        model.put("userid", userid);
        model.put("home",Translator.HOME);
        model.put("predict","Предвидување на количина на продадени производи");
        model.put("resources","Анализа на ресурси");
        model.put("errorDatabaseConnection", Translator.errorDatabaseConnection);
        model.put("error", Translator.error);
        model.put("successDelete", Translator.successDelete);
        model.put("destination", Translator.destination+"C:/Users/"+System.getProperty("user.name")+"/Downloads");
        model.put("customerCompanyName", Translator.customerCompanyName);
        model.put("productOrderNumber", Translator.productOrderNumber);
        model.put("amount", Translator.amount);
        model.put("INVOICE_NUMBER", Translator.INVOICE_NUMBER);
        model.put("download", Translator.download);
        model.put("preview", Translator.preview);
        model.put("roleId",role);
        model.put("delete", Translator.delete);
        model.put("statistics",Translator.statistics);
        return "invoicePdfList";
    }
    @CrossOrigin("*")
    @RequestMapping( value = "/previewFile" , method = RequestMethod.POST)
    @ResponseBody
    private void  previewFile(@RequestParam("path") String path) {
        System.out.println(path);
        File file = new File(path);
        if (file.toString().endsWith(".pdf")) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        ;
    }
    @CrossOrigin("*")
    @RequestMapping( value = "/downloadInvoice" , method = RequestMethod.POST)
    @ResponseBody
    public String downloadDocument(Map<String, Object> model,@RequestParam("products") String productList,@RequestParam("document_number") String document_number,@RequestParam("company_name") String company_name, @RequestParam("company_info") String company_info,@RequestParam("creation_date") String creation_date,@RequestParam("num") String num,@RequestParam("language") String language) throws DocumentException, IOException {
        List<product> products =new ArrayList<>();
        String[] allproducts = productList.split(",");
        for(int i=0;i<allproducts.length;i++) {
            String[] productData = allproducts[i].split(":");
            product product=new product(productData[3].split("=")[1], productData[0].split("=")[1],(int) Float.parseFloat(productData[4].split("=")[1]), Float.parseFloat(productData[2].split("=")[1]));
            products.add(product);
        }
        String[] crDate = creation_date.split("-");
        Date d = new Date();
        d.setYear(Integer.parseInt(crDate[0])-1900);
        d.setMonth(Integer.parseInt(crDate[1]));
        d.setDate(Integer.parseInt(crDate[2]));
        createDocuments(document_number,Integer.parseInt(num),company_name, d.toString(),company_info,products,language);
        return ("\\C:\\Users\\"+System.getProperty("user.name")+"\\Downloads\\faktura"+document_number.replace("/", "_")+".pdf").replace("\\","/");

    }
    private void createDocuments(String invoiceNumber,int rowNumber, String customerCompanyName, String date, String customerCompanyDescription,List<product> products,String language) throws DocumentException, IOException {
        createHeaders(100,customerCompanyName,customerCompanyDescription,invoiceNumber,language,date);
        createDocumentBody(products,rowNumber,language);
        createDocumentFooter(language);
        closeDocuments();
    }
    private void createHeaders(int height,String customerCompanyName, String customerCompanyDescription,String invoiceNumber,String language,String date) throws DocumentException, IOException{
        faktura = new Document();
        String brfaktura=invoiceNumber.replace("/", "_");
        fakturaWriter = PdfWriter.getInstance(faktura, new FileOutputStream("C:\\Users\\"+System.getProperty("user.name")+"\\Downloads\\faktura"+brfaktura+".pdf"));
        init();
        header headerdata=new header(brfaktura.replace("_", "/"),customerCompanyName,customerCompanyDescription,language);
        faktura.add(headerdata.createFakturaHeader(fs, height,language,date));
        Phrase phrase = fs.process(Translator.INVOICE_NUMBER+" "+invoiceNumber);
        faktura.add(new Paragraph(phrase));
    }
    private void init() throws DocumentException, IOException{
        faktura.open();;
        fakturaWriter.getAcroForm().setNeedAppearances(true);
        unicode = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fs = new FontSelector();
        fs.addFont(new Font(unicode));
    }
    private  void closeDocuments(){
        faktura.close();
    }
    private void createDocumentFooter(String language) throws DocumentException{
        footer footerdata;
        if(language.equals("true"))
            footerdata=new footer("Tamara Mitevska");
        else
            footerdata=new footer("Тамара Митевска");

        faktura.add(footerdata.writeFakturaFooter(fs,language));
    }
    private void createDocumentBody(List<product> products,int rowNumber,String language) throws DocumentException{
        table=new documentTable(fs,language);
        table.writeDataForTable(fs,products,rowNumber);
        table.writeDataForTable2(fs);
        faktura.add(table.getTable1());
        writeSum(language);
        faktura.add(table.getTable2());
        Phrase phrase;
//		if(language.equals("true")) {
//			phrase = fs.process("Note: Exemption of VAT liability in accordance with Article 24 of the Law on VAT. \n Delivery: \n EXW Kumanovo \n");
//		}
//		else {
//			phrase = fs.process("Забелешка: Ослободување на обврска за ДДВ согласно член 24 од ЗДДВ.\n Испорака:\n EXW Куманово");
//		}
//	    Paragraph paragraph1 = new Paragraph(phrase);
//		faktura.add(paragraph1);

    }
    public void writeSum(String language) throws DocumentException{
        if(language.equals("true")) {
            addSumContent(faktura, "Total amount:     "+table.vkupno() +"EUR", fs,0);
        }
        else {
            addSumContent(faktura, "Вкупно:     "+table.vkupno(), fs,0);
        }
    }
    private void addSumContent(Document document, String paragraph, FontSelector fs,int width) throws DocumentException {
        Phrase phrase = fs.process(paragraph);
        Paragraph paragraph1 = new Paragraph(phrase);
        paragraph1.setSpacingBefore(width);
        paragraph1.setAlignment(2);
        paragraph1.setIndentationRight(20);
        document.add(paragraph1);
    }
}
