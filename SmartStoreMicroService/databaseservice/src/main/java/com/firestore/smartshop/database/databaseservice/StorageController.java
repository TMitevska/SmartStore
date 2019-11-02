package com.firestore.smartshop.database.databaseservice;

import com.firestore.smartshop.database.databaseservice.models.SaleModel;
import com.firestore.smartshop.database.databaseservice.models.productModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin("*")
@Controller
public class StorageController {

    private Firestore db;

    public StorageController() {
        db = FirestoreClient.getFirestore();
    }

    @RequestMapping(value = "/storage/deleteProducts", method = RequestMethod.POST)
    @ResponseBody
    public void deleteProducts(){
            try {
                // retrieve a small batch of documents to avoid out-of-memory errors
                ApiFuture<QuerySnapshot> future = db.collection("product").limit(1000).get();
                int deleted = 0;

                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                System.out.println(documents.size());
                for (QueryDocumentSnapshot document : documents) {
                    ApiFuture<WriteResult> deleteproduct = db.collection("product").document(document.getId()).delete();
                    ++deleted;
                }
                if (deleted >= 1000) {
                    deleteProducts();
                }
            } catch (Exception e) {
                System.err.println("Error deleting collection : " + e.getMessage());
            }
    }

    @RequestMapping(value = "/storage/deletePredictions", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSales(){
        try {
            // retrieve a small batch of documents to avoid out-of-memory errors
            ApiFuture<QuerySnapshot> future = db.collection("sales").limit(100).get();
            int deleted = 0;
            // future.get() blocks on document retrieval
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                ApiFuture<WriteResult> deletesales = db.collection("sales").document(document.getId()).delete();
                ++deleted;
            }
            if (deleted >= 100) {
                deleteSales();
            }
            System.out.println(deleted);
        } catch (Exception e) {
            System.err.println("Error deleting collection : " + e.getMessage());
        }
        return "true";
    }

    @RequestMapping(value = "/storage/addProducts", method = RequestMethod.POST)
    @ResponseBody
    public String addProductsToStorage(@RequestParam("file") MultipartFile file){

        List<productModel> productList = new ArrayList<>();
        try {
            InputStream data = file.getInputStream();
            int i=0;
            boolean isHeader = true;
            String row = "";
            while((i = data.read()) !=-1 ) {  // to reach until the laste bytecode -1
                if((char)i != '\n' )
                    row += (char)i;
                else{
                    if(!isHeader){
                        System.out.println(row);
                        String[] productItem = row.split(",");
                        productModel product = new productModel(Integer.parseInt(productItem[0]),productItem[1],productItem[2],productItem[3]);
                        row="";
                        productList.add(product);
                    }else{
                        isHeader = false;
                        row="";
                    }
                }
            }
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in openning the file");
        }

        for(productModel product : productList) {
            Map<String, Object> db_product = new HashMap<>();
            db_product.put("item_nbr", product.getItem_nbr());
            db_product.put("family", product.getFamily());
            db_product.put("name", product.getName());
            db_product.put("unit", product.getUnit());
            db.collection("product").add(db_product);
        }
        return "true";
    }

    @RequestMapping(value = "/storage/addPredictedSales", method = RequestMethod.POST)
    @ResponseBody
    public String addPredictedSales(@RequestParam("file") MultipartFile file){
        List<SaleModel> salesList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        try {
            InputStream data = file.getInputStream();
            int i=0;
            boolean isHeader = true;
            String row = "";
            while((i = data.read()) !=-1 ) {  // to reach until the laste bytecode -1
                if((char)i != '\n' )
                    row += (char)i;
                else{
                    if(!isHeader){
                        System.out.println(row);
                        String[] saleItem = row.split(",");
                        SaleModel sale = new SaleModel(saleItem[0],Integer.parseInt(saleItem[1]),Float.parseFloat(df.format(Float.parseFloat(saleItem[2]))),saleItem[3]);
                        row="";
                        salesList.add(sale);
                    }else{
                        isHeader = false;
                        row="";
                    }
                }
            }
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in openning the file");
        }


        for(SaleModel sale : salesList) {
            System.out.println(sale.toString());
            Map<String, Object> db_sale = new HashMap<>();
            db_sale.put("date", sale.getDate());
            db_sale.put("item_nbr", sale.getItem_nbr());
            db_sale.put("unit_sales", Math.round(sale.getUnit_sales()));
            db_sale.put("year", sale.getYear());
            db.collection("sales").add(db_sale);
        }

        return "true";
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getSoldProducts", method = RequestMethod.GET)
    @ResponseBody
    public  String getSoldProducts(@RequestParam("itemId") String itemId) {
        System.out.println(itemId);
        ApiFuture<QuerySnapshot> query = db.collection("sales").whereEqualTo("item_nbr",Integer.parseInt(itemId)).orderBy("date").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> sales = querySnapshot.getDocuments();
            List<SaleModel> result = new ArrayList<>();
            for(QueryDocumentSnapshot sale : sales){

                SaleModel s = new SaleModel(sale.get("date").toString(),Integer.parseInt(sale.get("item_nbr").toString()),Float.parseFloat(sale.get("unit_sales").toString()));
                result.add(s);
            }
            Gson gson = new Gson();
            String jsonString = gson.toJson(result);
            System.out.println(jsonString);
            return jsonString;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    @ResponseBody
    public  String getAllProducts() {
        ApiFuture<QuerySnapshot> query = db.collection("product").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            List<QueryDocumentSnapshot> products = querySnapshot.getDocuments();
            List<productModel> result = new ArrayList<>();
            for(QueryDocumentSnapshot product : products){
                productModel s = new productModel(Integer.parseInt(product.get("item_nbr").toString()),product.get("family").toString(),product.get("name").toString(),product.get("unit").toString());
                result.add(s);
            }
            Gson gson = new Gson();
            String jsonString = gson.toJson(result);
            return jsonString;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getAllProductsPercent", method = RequestMethod.GET)
    @ResponseBody
    public  String getAllProductsPercent() {
        ApiFuture<QuerySnapshot> query = db.collection("sales").orderBy("item_nbr").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
            System.out.println(querySnapshot.size());
            List<QueryDocumentSnapshot> sales = querySnapshot.getDocuments();
            List<SaleModel> result = new ArrayList<>();
            int item_nbr = 0;
            int sum = 0 ;
            for(QueryDocumentSnapshot sale : sales){
                System.out.println("-->"+sale.get("year").toString());
                if(item_nbr > 0 && item_nbr != Integer.parseInt(sale.get("item_nbr").toString())) {
                    SaleModel s = new SaleModel();
                    s.setItem_nbr(item_nbr);
                    s.setUnit_sales((float) sum);
                    result.add(s);
                    item_nbr = Integer.parseInt(sale.get("item_nbr").toString());
                    sum = 0;
                }
                else if (item_nbr > 0){
                    sum += Integer.parseInt(sale.get("unit_sales").toString());
                }
                else{
                    item_nbr = Integer.parseInt(sale.get("item_nbr").toString());
                    sum += Integer.parseInt(sale.get("unit_sales").toString());
                }
            }

            SaleModel s = new SaleModel();
            s.setItem_nbr(item_nbr);
            s.setUnit_sales((float) sum);
            result.add(s);

            Gson gson = new Gson();
            String jsonString = gson.toJson(result);
            System.out.println(jsonString);
            return jsonString;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

}
