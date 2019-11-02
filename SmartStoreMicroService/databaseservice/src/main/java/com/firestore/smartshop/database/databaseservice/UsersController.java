package com.firestore.smartshop.database.databaseservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    private InputStream serviceAccount;
    private Firestore db;

    private void initDb(){
        db = FirestoreClient.getFirestore();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String getAllUsers(Map<String, Object> model) throws ExecutionException, InterruptedException {
        initDb();
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        String result = "";
        for (QueryDocumentSnapshot document : documents) {
            result += "User: " + document.getId() + "\n";
        }
        return result;
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/addUserInDatabase", method = RequestMethod.POST)
    @ResponseBody
    public boolean addUser(Map<String, Object> model, @RequestParam("role") String role, @RequestParam("phone") String phone,@RequestParam("name") String name, @RequestParam("password") String pass,@RequestParam("email") String email, @RequestParam("address") String address) throws ExecutionException, InterruptedException, IOException {
        initDb();
        ApiFuture<QuerySnapshot> query = db.collection("users").whereEqualTo("email", email).get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        if (documents.size() > 0)
            return false;
        else {
            CollectionReference docRef = db.collection("users");

            Map<String, Object> user = new HashMap<>();
            user.put("username", name);
            user.put("address", address);
            user.put("email", email);
            user.put("phone", phone);
            user.put("password", pass);
            user.put("roleId", role);

            ApiFuture<DocumentReference> result = docRef.add(user);
            return true;
        }
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    @ResponseBody
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String pass) throws ExecutionException, InterruptedException, IOException {
        initDb();
        ApiFuture<QuerySnapshot> query = db.collection("users").whereEqualTo("email", email).whereEqualTo("password",pass).get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        if (documents.size() > 0)
            return documents.get(0).getId()+":"+documents.get(0).get("roleId");
        else
            return "";
    }



}
