package com.firestore.smartshop.database.databaseservice;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class RaspberryController {
    private Firestore db;

    public RaspberryController() {
        db = FirestoreClient.getFirestore();
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getRemoteDevicesStatus", method = RequestMethod.GET)
    @ResponseBody
    public String getRemoteDevicesStatus(){

        ApiFuture<QuerySnapshot> query = db.collection("remoteControl").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            Gson gson = new Gson();
            String jsonString = gson.toJson(documents.get(0).getData()); // Convert car object to JSON string
            return documents.get(0).getId()+":"+jsonString.replace("\"","").replace("{","").replace("}","").replace(",",":");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/changeRemoteDevicesStatus", method = RequestMethod.GET)
    @ResponseBody
    public String changeRemoteDevicesStatus(@RequestParam("status") String status,@RequestParam("controlId") String id){
        String[] result = status.split(",");
        DocumentReference contact = db.collection("remoteControl").document(id);
        contact.update("camera", result[2]);
        contact.update("light", result[1]);
        contact.update("music", result[0]);

        return "";
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getdocumentNumber", method = RequestMethod.GET)
    @ResponseBody
    public String getdocumentNumber(){

        ApiFuture<QuerySnapshot> query = db.collection("remoteControl").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            Gson gson = new Gson();
            String jsonString = gson.toJson(documents.get(0).get("document_number")+"_"+documents.get(0).get("document_year")); // Convert car object to JSON string
            return jsonString.replace("\"","");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }
}
