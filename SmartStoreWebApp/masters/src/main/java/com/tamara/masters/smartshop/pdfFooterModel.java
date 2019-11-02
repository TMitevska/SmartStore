package com.tamara.masters.smartshop;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class pdfFooterModel {
    private String owner;

    public pdfFooterModel(){}

    public pdfFooterModel(String owner) {
        super();
        this.owner = owner;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public PdfPTable writeFakturaFooter(FontSelector fs) throws DocumentException
    {	PdfPTable footerTable=new PdfPTable(2);
        footerTable.setWidthPercentage(100);
        footerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerTable.setWidths(new float[] {1,1});

        addFooterContent(footerTable, "Овластено лице:\n "+this.getOwner()+"\n\n\n\n"+drawLine(),fs);
        addFooterContent(footerTable, "Примил:\n\n\n\n\n"+drawLine(),fs);

        return footerTable;
    }
    public PdfPTable writeUplatnicaFooter(FontSelector fs) throws DocumentException
    {	PdfPTable footerTable=new PdfPTable(2);
        footerTable.setWidthPercentage(100);
        footerTable.setSpacingBefore(100);
        footerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerTable.setWidths(new float[] {1,1});

        addFooterContent(footerTable, "Предал: \n\n\n\n"+drawLine(),fs);
        addFooterContent(footerTable, "Примил: "+"\n\n\n\n\n"+drawLine(),fs);

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
