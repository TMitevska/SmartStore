package com.example.tamara.smartshop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceProductsActivity extends AppCompatActivity {

    private String invoiceNumber = "";
    private String companyName = "";
    private String description = "";
    private int numProducts = 0;
    private String userId;
    private String role;
    private Intent intent;
    private List<TableRow> rows = new ArrayList<TableRow>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_invoiceproducts);

        intent = getIntent();
        invoiceNumber = intent.getStringExtra("INVOICE_NUMBER");
        companyName = intent.getStringExtra("COMPANY_NAME");
        description = intent.getStringExtra("DESCRIPTION");
        numProducts = Integer.parseInt(intent.getStringExtra("NUMBER_OF_PRODUCTS"));
        userId = intent.getStringExtra("userId");
        role = intent.getStringExtra("roleId");

        TableLayout table = (TableLayout) findViewById(R.id.maintable);
        table.setPadding(15,15,15,15);
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow rowLabels = new TableRow(this);
        rowLabels.setGravity(Gravity.CENTER);

        TextView empty = new TextView(this);
        TextView title = new TextView(this);
        title.setText("Производи");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView no = new TextView(this);
        no.setText("Ред. бр.");
        no.setTypeface(Typeface.DEFAULT_BOLD);
        no.setTextColor(Color.BLACK);

        TextView product_name = new TextView(this);
        product_name.setText("Име на производ");
        product_name.setTypeface(Typeface.DEFAULT_BOLD);
        product_name.setTextColor(Color.BLACK);

        TextView type = new TextView(this);
        type.setText("Мерка");
        type.setTypeface(Typeface.DEFAULT_BOLD);
        type.setTextColor(Color.BLACK);

        TextView quantity = new TextView(this);
        quantity.setText("Количина");
        quantity.setTypeface(Typeface.DEFAULT_BOLD);
        quantity.setTextColor(Color.BLACK);

        TextView price = new TextView(this);
        price.setText("Цена");
        price.setTypeface(Typeface.DEFAULT_BOLD);
        price.setTextColor(Color.BLACK);

        rowTitle.addView(no);
        rowTitle.addView(product_name);
        rowTitle.addView(type);
        rowTitle.addView(quantity);
        rowTitle.addView(price);
        table.addView(rowTitle);


        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        for(int i=0;i<numProducts;i++){
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            row.setPadding(5,5,5,5);

            TextView number = new TextView(this);
            number.setText((i+1)+". ");
            number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
            number.setTypeface(Typeface.DEFAULT_BOLD);
            number.setWidth(screenWidth/8);
            number.setHeight(100);
            number.setTextColor(Color.BLACK);
            number.setGravity(Gravity.CENTER);
            row.addView(number);

            EditText name = new EditText(this);
            name.setWidth(screenWidth/5);
            name.setTag("name"+i);
            name.setHeight(100);
            name.setTextColor(Color.BLACK);
            row.addView(name);

            EditText productType = new EditText(this);
            productType.setWidth(screenWidth/4);
            productType.setTag("type"+i);
            productType.setHeight(100);
            productType.setTextColor(Color.BLACK);
            row.addView(productType);

            EditText productQuantity = new EditText(this);
            productQuantity.setWidth(screenWidth/5);
            productQuantity.setTag("quantity"+i);
            productQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
            productQuantity.setHeight(100);
            productQuantity.setTextColor(Color.BLACK);
            row.addView(productQuantity);

            EditText productPrice = new EditText(this);
            productPrice.setWidth(screenWidth/5);
            productPrice.setTag("price"+i);
            productPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
            productPrice.setHeight(100);
            productPrice.setTextColor(Color.BLACK);
            row.addView(productPrice);

            rows.add(row);
            table.addView(row);
        }

    }

    public void onBackClick(View view){
        Intent intent = new Intent(this, InvoiceActivity.class);
        intent.putExtra("INVOICE_NUMBER", invoiceNumber);
        intent.putExtra("COMPANY_NAME", companyName);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("NUMBER_OF_PRODUCTS", numProducts);
        intent.putExtra("userId", userId);
        intent.putExtra("roleId", role);
        setResult(RESULT_OK, intent);
        finish();
    }
    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, InvoiceActivity.class);
        intent.putExtra("INVOICE_NUMBER", invoiceNumber);
        intent.putExtra("COMPANY_NAME", companyName);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("NUMBER_OF_PRODUCTS", numProducts);
        intent.putExtra("userId", userId);
        intent.putExtra("roleId", role);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onNextClick(View view){

        TableLayout table = (TableLayout) findViewById(R.id.maintable);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        Map<String, Object> document = new HashMap<>();
        document.put("document_number", invoiceNumber);
        document.put("company_name", companyName);
        document.put("company_info", description);
        document.put("creation_date", Calendar.getInstance().getTime());
        document.put("userId",userId);

        DocumentReference document_number = db.collection("remoteControl").document("zsCuvUFPbBrU6NkE3gX6");
        document_number.update("document_number", Integer.parseInt(invoiceNumber.split("_")[0])+1);

        db.collection("document")
                .add(document)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        insertProducts(documentReference.getId(),rows.size());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


    }

    private void insertProducts(String documentId, int rowNumber){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        for(int i=0;i<rows.size();i++){
            pdfproductModel product = new pdfproductModel();
            TableRow row = rows.get(i);
            EditText name = (EditText) row.findViewWithTag("name"+i);
            EditText type = (EditText) row.findViewWithTag("type"+i);
            EditText quantity = (EditText) row.findViewWithTag("quantity"+i);
            EditText price = (EditText) row.findViewWithTag("price"+i);

            product.setProductName(name.getText().toString());
            product.setProductType(type.getText().toString());
            product.setProductQuantity(Float.parseFloat(quantity.getText().toString()));
            product.setProductPrice(Float.parseFloat(price.getText().toString()));

            Map<String, Object> document_product = new HashMap<>();
            document_product.put("product_name", name.getText().toString());
            document_product.put("product_type", type.getText().toString());
            document_product.put("product_quantity", Float.parseFloat(quantity.getText().toString()));
            document_product.put("product_price", Float.parseFloat(price.getText().toString()));
            document_product.put("documentId",documentId);

            db.collection("document_product")
                    .add(document_product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("roleId", role);
        startActivity(intent);

        this.finish();
    }
}
