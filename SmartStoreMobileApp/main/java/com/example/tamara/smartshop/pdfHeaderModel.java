package com.example.tamara.smartshop;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class pdfHeaderModel {
        public static final String[] PDF_INFO_MK = {"Фактура бр: ","Датум: ", "Валута: ","Испратница бр: ","Време на издавање: "};
        private PdfPTable table;
        private PdfPTable uplatnicaTable;
        private String brfaktura;
        private Date creationDate;
        private String buyerInfo;
        private String[] infoForOwner={"Паметна продавница", "Србо Томовиќ", "Даночен број: МК: 4017993112636", "Жиро сметка: 320100004042185" ,"Банка: Централна Кооперативна Банка"};
        private String[] info;
        public pdfHeaderModel(){}
        public pdfHeaderModel( String brfaktura, String customerName, String customerDescription, com.google.firebase.Timestamp date ) throws DocumentException, ParseException {
            super();
            this.brfaktura = brfaktura;
            this.table=new PdfPTable(2);
            this.table.setWidthPercentage(100);
            this.table.setWidths(new float[] {1,1});
            this.uplatnicaTable=new PdfPTable(2);
            this.uplatnicaTable.setWidthPercentage(100);
            Date startdate = null;
            try {
                startdate = parseDate(date.toDate().toString(), "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.creationDate = startdate;

            this.uplatnicaTable.setWidths(new float[] {1,1});
            setBuyerInfo(customerName, customerDescription);
        }
        private Date parseDate(String date, String format) throws ParseException
        {
            System.out.println(date);
            SimpleDateFormat formatter = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            return formatter.parse(date);
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
        public PdfPTable createFakturaHeader(Document document, FontSelector fs, int height ) throws DocumentException{
            info = PDF_INFO_MK;
            addTableContent(getOwnerInfo(),fs,height,0);
            String creationdate = getDate(creationDate);
            Date enddate=creationDate;
            enddate.setMonth(enddate.getMonth()+1);
            addTableContent(info[0] + this.getBrfaktura()+" \n"+ info[1] +creationdate+" \n"+ info[2] +getDate(enddate)+" \n\n\n"+getBuyerInfo(),fs,height,2);
            return this.getTable();
        }
        @SuppressWarnings("deprecation")
        public PdfPTable createUplatnicaHeader(Document document,FontSelector fs,int height ) throws DocumentException{
            info = PDF_INFO_MK;
            addUplatnicaTableContent(getOwnerInfo(),fs,height,0);
            addUplatnicaTableContent(getBuyerInfo(),fs,height,2);
            addUplatnicaTableContent(info[3] + this.getBrfaktura(),fs,height,0);
            addUplatnicaTableContent(info[4] + getDate(creationDate),fs,height,2);
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
        private String getOwnerInfo(){
            String result="";
            for(String string:this.infoForOwner)
                result+=string+" \n";
            return result;
        }
        private String getBuyerInfo(){
            return this.buyerInfo;
        }
        public void setBuyerInfo(String customerName,String customerDescription) {
            String result = "";
            if(!customerName.equals(""))
                result += customerName + "\n";
            if(!customerDescription.equals(""))
                result += customerDescription + "\n";
            this.buyerInfo = result;
        }
}
