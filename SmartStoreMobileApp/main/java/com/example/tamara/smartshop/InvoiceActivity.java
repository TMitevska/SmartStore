package com.example.tamara.smartshop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

public class InvoiceActivity extends AppCompatActivity {
    private Intent intent;
    private final static int REQUEST_CODE_1 = -1;
    private String userId;
    private FirebaseFirestore db;
    private String role;
    private String invoiceNumber = "";
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_invoice);

        intent = getIntent();
        TextView companyName = (TextView) findViewById(R.id.company_name);
        TextView description = (TextView) findViewById(R.id.description);
        TextView productNumber = (TextView) findViewById(R.id.productnum);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        companyName.setText(intent.getStringExtra("COMPANY_NAME"));
        description.setText(intent.getStringExtra("DESCRIPTION"));
        productNumber.setText(intent.getStringExtra("NUMBER_OF_PRODUCTS"));
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
    public void onBackClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("roleId", role);
        startActivity(intent);
        finish();
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
    public void onnextClick(View view){
        final Intent intent = new Intent(this, InvoiceProductsActivity.class);
        final TextView companyName = (TextView) findViewById(R.id.company_name);
        TextView description = (TextView) findViewById(R.id.description);
        final TextView productNumber = (TextView) findViewById(R.id.productnum);
        intent.putExtra("COMPANY_NAME", companyName.getText().toString());
        intent.putExtra("DESCRIPTION", description.getText().toString());
        intent.putExtra("NUMBER_OF_PRODUCTS", productNumber.getText().toString());
        intent.putExtra("userId", userId);
        intent.putExtra("roleId", role);
        db.collection("remoteControl").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            invoiceNumber = task.getResult().getDocuments().get(0).get("document_number")+"_"+task.getResult().getDocuments().get(0).get("document_year");
                            intent.putExtra("INVOICE_NUMBER", invoiceNumber);
                            if( !companyName.getText().toString().equals("") && !productNumber.getText().toString().equals("")) {
                                startActivityForResult(intent, REQUEST_CODE_1);
                            }
                            else{
                                toast();
                            }
                        }
                    }
                });
    }
    public void toast(){
        Toast.makeText(this, "Внесете име на фирма и број на производи! ", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        switch (requestCode)
        {
            // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
            case REQUEST_CODE_1:
                TextView companyName = (TextView) findViewById(R.id.company_name);
                TextView description = (TextView) findViewById(R.id.description);
                TextView productNumber = (TextView) findViewById(R.id.productnum);
                if(resultCode == RESULT_OK)
                {
                    invoiceNumber=intent.getStringExtra("INVOICE_NUMBER");
                    companyName.setText(intent.getStringExtra("COMPANY_NAME"));
                    description.setText(intent.getStringExtra("DESCRIPTION"));
                    productNumber.setText(intent.getStringExtra("NUMBER_OF_PRODUCTS"));
                    userId = intent.getStringExtra("userId");
                    intent.putExtra("roleId", role);
                }
        }
    }
}