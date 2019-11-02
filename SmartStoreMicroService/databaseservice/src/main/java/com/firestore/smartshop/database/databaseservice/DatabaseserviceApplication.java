package com.firestore.smartshop.database.databaseservice;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jdk.nashorn.internal.runtime.Source;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

@SpringBootApplication
public class DatabaseserviceApplication extends SpringBootServletInitializer {

        private static String raspberryUrl = "https://smartshop.localtunnel.me";

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DatabaseserviceApplication.class);
    }

        public static void main(String[] args) {

            InputStream serviceAccount = null;
            try {
                serviceAccount = new FileInputStream("C:\\Users\\Tamara\\Desktop\\smartshop.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            GoogleCredentials credentials = null;
            try {
                credentials = GoogleCredentials.fromStream(serviceAccount);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("remoteControl").document("zsCuvUFPbBrU6NkE3gX6");

            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirestoreException e) {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        try {
                            URL url = new URL(raspberryUrl + "/devices/"+snapshot.get("music")+"/"+snapshot.get("light")+"/"+snapshot.get("camera"));

                            System.out.println(raspberryUrl + "/devices/"+snapshot.get("music")+"/"+snapshot.get("light")+"/"+snapshot.get("camera"));

                            HttpURLConnection con = null;
                            con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            int responseCode = con.getResponseCode();

                            System.out.println("\nSending 'GET' request to URL : " + url);
                            System.out.println("Response Code : " + responseCode);

                            con.disconnect();
                        } catch (IOException e1) {

                        }
                    } else {
                        System.out.print("Current data: null");
                    }
                }
            });

            SpringApplication.run(DatabaseserviceApplication.class, args);
    }



    }


