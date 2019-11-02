package com.firestore.smartshop.database.databaseservice;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Controller
public class DocumentController {

    private Firestore db;

    public DocumentController() {
        db = FirestoreClient.getFirestore();
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/deleteDocument", method = RequestMethod.POST)
    @ResponseBody
    public void deleteDocument(@RequestParam("id") String id) {
        ApiFuture<WriteResult> query = db.collection("document").document(id).delete();
        ApiFuture<QuerySnapshot> query2 = db.collection("document_product").whereEqualTo("documentId",id).get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query2.get();
            List<QueryDocumentSnapshot> products = querySnapshot.getDocuments();
            for(QueryDocumentSnapshot product : products){
                ApiFuture<WriteResult> deleteproduct = db.collection("document_product").document(product.getId()).delete();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/addDocument", method = RequestMethod.POST)
    @ResponseBody
    public String addDocument(@RequestParam("invoiceNumber") String invoiceNumber,@RequestParam("companyName") String companyName,@RequestParam("description") String description,@RequestParam("userId") String userId) throws ExecutionException, InterruptedException {
        Map<String, Object> document = new HashMap<>();
        document.put("document_number", invoiceNumber);
        document.put("company_name", companyName);
        document.put("company_info", description);
        document.put("creation_date", new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(new Date()));
        document.put("userId",userId);
        ApiFuture<QuerySnapshot> query = db.collection("remoteControl").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            DocumentReference contact = db.collection("remoteControl").document(documents.get(0).getId());
            contact.update("document_number", Integer.parseInt(invoiceNumber.split("_")[0])+1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return db.collection("document").add(document).get().getId();
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getDocument", method = RequestMethod.POST)
    @ResponseBody
    public  String getDocument(@RequestParam("id") String id) {
        ApiFuture<DocumentSnapshot> query = db.collection("document").document(id).get();
        DocumentSnapshot querySnapshot = null;
        String result = "";
        try {
            querySnapshot = query.get();
            result += querySnapshot.getData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result.replace("{","").replace("}","").replace(", ",",");
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getDocumentProducts", method = RequestMethod.POST)
    @ResponseBody
    public  String getDocumentProducts(@RequestParam("id") String id) {
        ApiFuture<QuerySnapshot> query = db.collection("document_product").whereEqualTo("documentId",id).get();
        QuerySnapshot querySnapshot = null;
        String result = "";
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> products = querySnapshot.getDocuments();
            for(QueryDocumentSnapshot product : products){
                result += product.getData();
                result=result.replace(", ",":")+",";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result.replace("{","").replace("}","").replace(" ","");
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getDocuments", method = RequestMethod.POST)
    @ResponseBody
    public  String getDocuments(@RequestParam("userId") String userId) {
        ApiFuture<QuerySnapshot> query = db.collection("document").orderBy("document_number").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            List<String> result = new ArrayList<>();
            for(QueryDocumentSnapshot doc : documents){
                String[] products = getDocumentProducts(doc.getId()).split(",");
                float total = 0;
                for(String product : products){
                    String[] productInfo = product.split(":");
                    total += Float.parseFloat(productInfo[4].split("=")[1]) * Float.parseFloat(productInfo[2].split("=")[1]);
                }

                result.add(doc.getId()+":"+doc.get("document_number")+":"+doc.get("company_name")+":"+total);
            }
            Gson gson = new Gson();
            String jsonString = gson.toJson(result); // Convert car object to JSON string
            return jsonString;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/addProducts", method = RequestMethod.POST)
    @ResponseBody
    public String addProducts( @RequestParam Map<String,String> allRequestParams,  Map<String, Object> model){

        for(int i=0;i<Integer.parseInt(allRequestParams.get("numberOfProducts"));i++) {
            Map<String, Object> document_product = new HashMap<>();
            document_product.put("product_name", allRequestParams.get("productName"+(i+1)));
            document_product.put("product_type", allRequestParams.get("productType"+(i+1)));
            document_product.put("product_quantity", allRequestParams.get("productNumber"+(i+1)));
            document_product.put("product_price", allRequestParams.get("productValue"+(i+1)));
            document_product.put("documentId",allRequestParams.get("documentid"));
            db.collection("document_product").add(document_product);
        }
       return "true";
    }
}
