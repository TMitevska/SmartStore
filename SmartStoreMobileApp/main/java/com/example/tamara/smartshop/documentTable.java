package com.example.tamara.smartshop;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.ArrayList;
import java.util.List;

public class documentTable {

    private PdfPTable table1;
    private PdfPTable table2;
    private static List<tableData> productList=new ArrayList<tableData>();

    public documentTable(FontSelector fs) throws DocumentException {
        super();
        table1 = new PdfPTable(8);
        table1.setSpacingBefore(40);
        table1.setSpacingAfter(15);
        table1.setWidthPercentage(100);
        table1.setWidths(new float[] {1,4,2,3,3,2,3,2});
        table2 = new PdfPTable(4);
        table2.setSpacingBefore(15);
        table2.setSpacingAfter(50);
        table2.setWidthPercentage(60);
        table2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table2.setWidths(new float[] {2,3,2,3});
        createtableHeader(fs);
        createtable2Header(fs);
    }

    public PdfPTable getTable1() {
        return table1;
    }

    public void setTable1(PdfPTable table1) {
        this.table1 = table1;
    }

    public PdfPTable getTable2() {
        return table2;
    }

    public void setTable2(PdfPTable table2) {
        this.table2 = table2;
    }

    private void createtableHeader(FontSelector fs ){
        createTableHeaderColumn("Ред. бр.",fs);
        createTableHeaderColumn("Артикал",fs);
        createTableHeaderColumn("Ед. мера",fs);
        createTableHeaderColumn("Количина",fs);
        createTableHeaderColumn("Продажна цена без ДДВ",fs);
        createTableHeaderColumn("ДДВ%",fs);
        createTableHeaderColumnWithColSpan("Продажна вредност со ДДВ","Цена","Износ",fs);

    }
    private void createtable2Header(FontSelector fs){
        createTable2HeaderColumn("Стапка",fs);
        createTable2HeaderColumn("Износ без ДДВ",fs);
        createTable2HeaderColumn("ДДВ",fs);
        createTable2HeaderColumn("Износ со ДДВ",fs);
    }
    private void createTableHeaderColumn(String headerData,FontSelector fs){
        PdfPCell cell=new PdfPCell(new Phrase(fs.process(headerData)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(50);
        cell.setRowspan(2);
        table1.addCell(cell);
    }
    private void createTableHeaderColumnWithColSpan(String headerData,String headerData2,String headerData3,FontSelector fs){
        PdfPCell cell=new PdfPCell(new Phrase(fs.process(headerData)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table1.addCell(cell);
        PdfPCell cell2=new PdfPCell(new Phrase(fs.process(headerData2)));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell2.setFixedHeight(20);
        table1.addCell(cell2);
        PdfPCell cell3=new PdfPCell(new Phrase(fs.process(headerData3)));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell3.setFixedHeight(20);
        table1.addCell(cell3);
    }
    private void createTable2HeaderColumn(String headerData,FontSelector fs){
        PdfPCell cell=new PdfPCell(new Phrase(fs.process(headerData)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(30);
        table2.addCell(cell);
    }
    public void writeDataForTable2(FontSelector fs) throws DocumentException{
        writeTable2Data(fs.process("18.00"));
        writeTable2Data(fs.process(""+vkupnoBezDDV()));
        writeTable2Data(fs.process(""+vkupnoDDV()));
        writeTable2Data(fs.process(""+vkupno()));
    }
    public void writeDataForTable(FontSelector fs,List<product> products, int rowNumber) throws DocumentException{

        for (int i=0;i<rowNumber;i++){
            tableData row=getRowData(fs,products.get(i));
            writeTableData(fs.process(""+(i+1)+"."));
            writeTableData(row.getArtikal());
            writeTableData(row.getMerka());
            writeTableData(fs.process(""+row.getKolicina()));
            writeTableData(fs.process(""+row.getCenaBezDDV()));
            writeTableData(fs.process("18.00%"));
            writeTableData(fs.process(""+row.getCena()));
            writeTableData(fs.process(""+row.vkupenIznos()));
            productList.add(row);
        }
    }

    private tableData getRowData(FontSelector fs,product product){
        Phrase name=fs.process(product.getArtikal());
        Phrase merka=fs.process(product.getMerka().toLowerCase());
        int kolicina=product.getKolicina();
        float cena=product.getCena();
        return new tableData(name,merka,kolicina,cena);
    }
    private void writeTableData(Phrase data){
        PdfPCell cell=new PdfPCell(data);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);
    }
    private void writeTable2Data(Phrase data){
        PdfPCell cell=new PdfPCell(data);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);
    }
    public float vkupno(){
        float sum=0;
        for(tableData item:productList)
            sum+=item.vkupenIznos();
        return sum;
    }
    private float vkupnoBezDDV(){
        float sum=0;
        for(tableData item:productList)
            sum+=(item.getCenaBezDDV()*item.getKolicina());
        return sum;
    }
    private float vkupnoDDV(){
        return (float)vkupno()-vkupnoBezDDV();
    }


}

