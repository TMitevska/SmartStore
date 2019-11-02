package com.example.tamara.smartshop;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    public String userId;
    public String role;
    private FirebaseFirestore db;
    private final static int REQUEST_CODE_1 = -1;
    private Intent intent;
    private Switch device1;
    private Switch device2;
    private Switch device3;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        device1 = (Switch) findViewById(R.id.switch2);
        device2 = (Switch) findViewById(R.id.switch3);
        device3 = (Switch) findViewById(R.id.switch4);

        db.collection("remoteControl").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().getDocuments().get(0).get("music").equals("true")){
                                device2.setChecked(true);
                            }
                            if(task.getResult().getDocuments().get(0).get("light").equals("true")){
                                device3.setChecked(true);
                            }
                            if(task.getResult().getDocuments().get(0).get("camera").equals("true")){
                                device1.setChecked(true);
                            }
                        }
                    }
                });
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

        final DocumentReference docRef = db.collection("remoteControl").document("zsCuvUFPbBrU6NkE3gX6");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    System.out.println("-->listen");
                    if(snapshot.get("music").equals("true")){
                        device2.setChecked(true);
                    }
                    else{
                        device2.setChecked(false);
                    }
                    if(snapshot.get("light").equals("true")){
                        device3.setChecked(true);
                    }
                    else{
                        device3.setChecked(false);
                    }
                    if(snapshot.get("camera").equals("true")){
                        device1.setChecked(true);
                    }
                    else{
                        device1.setChecked(false);
                    }
                }
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("control");
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
    public void  updateRemoteControl(View view){
        Switch device = (Switch) findViewById(view.getId());
        System.out.println("-->click  "+view.getId()+"  "+ device.isChecked());
        String status = "false";
        if(device.isChecked())
            status = "true";
        DocumentReference remotedevice = db.collection("remoteControl").document("zsCuvUFPbBrU6NkE3gX6");
        if(device2.getId() == view.getId())
            remotedevice.update("music", status);
        else if(device3.getId() == view.getId())
            remotedevice.update("light", status);
        else if(device1.getId() == view.getId())
            remotedevice.update("camera", status);
    }

}
