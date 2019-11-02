package com.tamara.masters.smartshop;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.tamara.masters.smartshop.strings.Translator;

public class footer {

    private String owner;
    private  PdfPTable footerTable;
    public footer(){}

    public footer(String owner) {
        super();
        this.owner = owner;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public PdfPTable writeFakturaFooter(FontSelector fs, String language) throws DocumentException
    {	footerTable=new PdfPTable(2);
        footerTable.setWidthPercentage(100);
        footerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerTable.setWidths(new float[] {1,1});
        if(language.equals("true")){
            addFooterContent(footerTable, Translator.OVLASTENO_lICE_EN + "\n "+this.getOwner()+"\n\n\n\n"+drawLine(),fs);
            addFooterContent(footerTable, Translator.PRIMIL_EN + "\n\n\n\n\n"+drawLine(),fs);
        }
        else{
            addFooterContent(footerTable, Translator.OVLASTENO_lICE + "\n "+this.getOwner()+"\n\n\n\n"+drawLine(),fs);
            addFooterContent(footerTable, Translator.PRIMIL + "\n\n\n\n\n"+drawLine(),fs);
        }

        return footerTable;
    }
    public PdfPTable writeUplatnicaFooter(FontSelector fs, String language) throws DocumentException
    {	footerTable = new PdfPTable(2);
        footerTable.setWidthPercentage(100);
        footerTable.setSpacingBefore(100);
        footerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerTable.setWidths(new float[] {1,1});
        if(language.equals("true")){
            addFooterContent(footerTable, Translator.PREDAL_EN + ": \n\n\n\n"+drawLine(),fs);
            addFooterContent(footerTable, Translator.PRIMIL_EN + ": "+"\n\n\n\n\n"+drawLine(),fs);
        }
        else {
            addFooterContent(footerTable, Translator.PREDAL + ": \n\n\n\n"+drawLine(),fs);
            addFooterContent(footerTable, Translator.PRIMIL + ": "+"\n\n\n\n\n"+drawLine(),fs);
        }
        return footerTable;
    }
    private  void addFooterContent(PdfPTable footerTable, String data, FontSelector fs) throws DocumentException {
        PdfPCell cell=new PdfPCell(new Phrase(fs.process(data)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(100);
        cell.setBorder(0);
        footerTable.addCell(cell);
    }
    private String drawLine(){
        return "________________________";
    }
}
