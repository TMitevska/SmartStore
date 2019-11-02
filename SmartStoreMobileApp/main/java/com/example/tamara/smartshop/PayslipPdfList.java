package com.example.tamara.smartshop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PayslipPdfList extends AppCompatActivity{
    private List<TableRow> rows = new ArrayList<TableRow>();
    private String userId;
    private String role;
    private Intent intent;
    private FirebaseFirestore db;
    private static FontSelector fs;
    private static BaseFont unicode;
    private static PdfWriter payslipWriter;
    private Document payslip;
    private static documentTable table;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_payslippdf);
        intent = getIntent();
        userId = intent.getStringExtra("userId");
        role = intent.getStringExtra("roleId");

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (role.equals("s1sze9GAHFXf4gMZ1X3K"))
        {
            nav_Menu.findItem(R.id.create).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.create:
                                intent = new Intent(getApplicationContext(), InvoiceActivity.class);
                                intent.putExtra("INVOICE_NUMBER", "");
                                intent.putExtra("COMPANY_NAME", "");
                                intent.putExtra("DESCRIPTION", "");
                                intent.putExtra("NUMBER_OF_PRODUCTS", "");
                                intent.putExtra("userId", userId);
                                intent.putExtra("roleId", role);
                                startActivity(intent);
                                return true;
                            case R.id.invoicelist:
                                intent = new Intent(getApplicationContext(), InvoicePdfList.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("roleId", role);
                                startActivity(intent);
                                return true;
                            case R.id.paysliplist:
                                intent = new Intent(getApplicationContext(), PayslipPdfList.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("roleId", role);
                                startActivity(intent);
                                return true;
                            case R.id.remoteControl:
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("roleId", role);
                                startActivity(intent);
                                return true;
                            case R.id.ai:
                                Toast.makeText(getApplicationContext(), "In progress....",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.logout:
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                return true;
                            default:
                                mDrawerLayout.closeDrawers();
                                return true;
                        }
                    }
                });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("document").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            createpayslipList(task.getResult());
                        }
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("roleId", role);
        startActivity(intent);
        finish();
    }
    private void createpayslipList(QuerySnapshot documentList) {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        TableLayout table = (TableLayout) findViewById(R.id.maintable);
        table.setPadding(5, 15, 5, 15);
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);
        for (QueryDocumentSnapshot document : documentList) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.LEFT);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.pdf);
            row.addView(imageView);

            TextView invoice = new TextView(this);
            invoice.setText("испратница_" + document.get("document_number") + ".pdf");
            invoice.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
            invoice.setTextColor(Color.BLACK);
            row.addView(invoice);

            ImageView download_document = new ImageView(this);
            download_document.setImageResource(R.drawable.download);
            download_document.setClickable(true);
            setdownloadDocumentOnClick(download_document, document,this);
            row.addView(download_document);
            if (!role.equals("s1sze9GAHFXf4gMZ1X3K")){
                ImageView delete_document = new ImageView(this);
                delete_document.setImageResource(R.drawable.delete);
                delete_document.setClickable(true);
                setDeleteDocumentOnClick(delete_document, document.getId());
                row.addView(delete_document);
            }


            rows.add(row);
            table.addView(row);
        }
    }

    private void setDeleteDocumentOnClick(final ImageView btn, final String documentId) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("document").document(documentId)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("document_product").whereEqualTo("documentId", documentId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("document_product").document(document.getId())
                                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i = new Intent(getApplicationContext(),InvoicePdfList.class);
                                                i.putExtra("userId", userId);
                                                i.putExtra("roleId", role);
                                                startActivity(i);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }
    private void setdownloadDocumentOnClick(final ImageView btn, final QueryDocumentSnapshot document,final Activity activity) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(activity,new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    else {

                    }

                    db.collection("document_product").whereEqualTo("documentId", document.getId()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<product> products = getProductList(task.getResult());
                                        Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
                                        payslip = new Document();
                                        File file = new File("/storage/emulated/0/Download/испратница"+document.get("document_number")+".pdf");
                                        try {
                                            payslipWriter = PdfWriter.getInstance(payslip, new FileOutputStream(file.getAbsoluteFile()));
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        payslipWriter.getAcroForm().setNeedAppearances(true);
                                        File fontfile = new File("res/font/arsenalregular.otf");
                                        try {
                                            unicode = BaseFont.createFont(fontfile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        fs = new FontSelector();
                                        fs.addFont(new Font(unicode));
                                        payslip.open();

                                        pdfHeaderModel headerdata= null;
                                        try {
                                            headerdata = new pdfHeaderModel(""+document.get("document_number"),""+document.get("company_name"),""+document.get("company_info"), (Timestamp) document.get("creation_date"));
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            payslip.add(headerdata.createUplatnicaHeader(payslip, fs, 100));
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            table=new documentTable(fs);
                                            table.writeDataForTable(fs,products,products.size());
                                            table.writeDataForTable2(fs);
                                            payslip.add(table.getTable1());
                                            writeSum();
                                            Phrase phrase;
                                            phrase = fs.process("Забелешка: Ослободување на обврска за ДДВ согласно член 24 од ЗДДВ.\n Испорака:\n EXW Куманово");
                                            Paragraph paragraph1 = new Paragraph(phrase);
                                            payslip.add(paragraph1);

                                            pdfFooterModel footerdata = new pdfFooterModel("Тамара Митевска");
                                            payslip.add(footerdata.writeUplatnicaFooter(fs));

                                            payslip.close();
                                            Toast.makeText(getApplicationContext(), "Преземање на испратница...", Toast.LENGTH_SHORT).show();

                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                } finally  {
                }
            }

        });
    }
    private List<product> getProductList(QuerySnapshot documentProductList) {
        List<product> productList = new ArrayList<product>();
        for (QueryDocumentSnapshot product : documentProductList) {
            product item = new product();
            item.setArtikal(product.get("product_name").toString());
            item.setCena(Float.parseFloat(product.get("product_price").toString()));
            item.setKolicina((int)Float.parseFloat(product.get("product_quantity").toString()));
            item.setMerka(product.get("product_type").toString());
            productList.add(item);
        }
        return productList;
    }

    public void writeSum() throws DocumentException{
        addSumContent(payslip, "Вкупно:     "+table.vkupno(), fs,0);
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