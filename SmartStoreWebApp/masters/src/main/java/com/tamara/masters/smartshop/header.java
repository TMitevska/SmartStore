package com.tamara.masters.smartshop;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.tamara.masters.smartshop.strings.Translator;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class header {
    private PdfPTable table;
    private PdfPTable uplatnicaTable;
    private String brfaktura;
    private String buyerInfo;
    private String[] infoForOwner={Translator.companyName, Translator.companyAddress, Translator.companyDanNumber, Translator.companyAccount ,Translator.companyBank};
    private String[] info;
    public header(){}
    public header( String brfaktura, String customerName, String customerDescription,String language) throws DocumentException {
        super();
        this.brfaktura = brfaktura;
        this.table=new PdfPTable(2);
        this.table.setWidthPercentage(100);
        this.table.setWidths(new float[] {1,1});
        this.uplatnicaTable=new PdfPTable(2);
        this.uplatnicaTable.setWidthPercentage(100);
        this.uplatnicaTable.setWidths(new float[] {1,1});
        setBuyerInfo(customerName , customerDescription, language);
    }

    public String getBrfaktura() {
        return brfaktura;
    }

    public void setBrfaktura(String brfaktura) {
        this.brfaktura = brfaktura;
    }

    public PdfPTable getUplatnicaTable() {
        return uplatnicaTable;
    }
    public void setUplatnicaTable(PdfPTable uplatnicaTable) {
        this.uplatnicaTable = uplatnicaTable;
    }
    public PdfPTable getTable() {
        return table;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
    }
    @SuppressWarnings("deprecation")
    public PdfPTable createFakturaHeader(FontSelector fs, int height , String language,String date) throws DocumentException{

        if(language.equals("true"))
            info = Translator.PDF_INFO_EN;
        else
            info = Translator.PDF_INFO_MK;
        addTableContent(getOwnerInfo(language),fs,height,0);
        Date startdate = null;
        Date enddate= null;
        try {
            startdate = parseDate(date, "yyyy-MM-dd");
            enddate=parseDate(date, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int month = startdate.getMonth();
        enddate.setMonth(month+1);
        addTableContent( info[1] +getDate(startdate)+" \n"+ info[2] +getDate(enddate)+" \n\n\n"+getBuyerInfo(),fs,height,2);

        return this.getTable();
    }
    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formater = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        return formater.parse(date);
    }
    public PdfPTable createUplatnicaHeader(FontSelector fs,int height,String language ,String date) throws DocumentException{
        if(language.equals("true"))
            info = Translator.PDF_INFO_EN;
        else
            info = Translator.PDF_INFO_MK;
        addUplatnicaTableContent(getOwnerInfo(language),fs,height,0);
        Date startdate = null;
        Date enddate= null;
        try {
            startdate = parseDate(date, "yyyy-MM-dd");
            enddate=parseDate(date, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int month = startdate.getMonth();
        enddate.setMonth(month+1);
        addUplatnicaTableContent(getBuyerInfo(),fs,height,2);
        addUplatnicaTableContent(info[3] + this.getBrfaktura(),fs,height,0);
        addUplatnicaTableContent(info[4] + getDate(startdate),fs,height,2);
        return this.getUplatnicaTable();
    }
    private  void addTableContent( String data, FontSelector fs,int height,int align) throws DocumentException {
        PdfPCell cell=new PdfPCell(new Phrase(fs.process(data)));
        cell.setHorizontalAlignment(align);
        cell.setFixedHeight(height);
        cell.setBorder(0);
        this.getTable().addCell(cell);
    }
    private  void addUplatnicaTableContent( String data, FontSelector fs,int height,int align) throws DocumentException {
        PdfPCell cell=new PdfPCell(new Phrase(fs.process(data)));
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setFixedHeight(height);
        cell.setBorder(0);
        this.getUplatnicaTable().addCell(cell);
    }
    @SuppressWarnings("deprecation")
    private  String getDate(Date date){
        String result="";
        if(date.getDate()<9)
            result+="0";
        result+=date.getDate();
        if((date.getMonth()+1)<9)
            result+=".0";
        else
            result+=".";
        result+=(date.getMonth()+1);
        return result+="."+(date.getYear()+1900);
    }
    private String getOwnerInfo(String language){
        String result="";
        if(language.equals("true"))
            infoForOwner = Translator.PDF_OWNERINFO_EN;
        else
            infoForOwner = Translator.PDF_OWNERINFO_MK;
        for(String string:this.infoForOwner)
            result+=string+" \n";
        return result;
    }
    private String getBuyerInfo(){
        return this.buyerInfo;
    }
    public void setBuyerInfo(String customerName, String customerDescription,String language) {
        String result = "";
        if(!customerName.equals(""))
            result += customerName + "\n";
        if(!customerDescription.equals(""))
            result += customerDescription + "\n";
        this.buyerInfo = result;
    }
}
