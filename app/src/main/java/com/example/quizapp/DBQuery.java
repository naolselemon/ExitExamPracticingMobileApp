package com.example.quizapp;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DBQuery {
    public static FirebaseFirestore  get_firestore;
    public static List<CategoryModel> get_catList = new ArrayList<CategoryModel>();
    public static void createUserData(String email, String name, CompleteListener completeListener) {
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("Email_ID", email);
        userData.put("Name", name);
        userData.put("Total_Score", 0);

        DocumentReference useDoc = get_firestore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        WriteBatch batch = get_firestore.batch();
        batch.set(useDoc, userData);

        DocumentReference countDoc = get_firestore.collection("users").document("Total_Users");
        batch.update(countDoc, "Count", FieldValue.increment(1));

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>(){

                    @Override
                    public void onSuccess(Void unused) {
                        completeListener.onSuccess();
                    }
                })
        .addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                completeListener.onFailure();
            }
        });
    }

    public static void loadCategories(){
        get_catList.clear();
        //collection name for categories from firestore
        get_firestore.collection("quiz").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //data structure that contains documents of the collection that comes from firestore
                    Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                    //simplified for loop that store all documents in the collection in a data structure defined
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                        docList.put(doc.getId(), doc);
                        }
                    //filtering categories from the documents
                    QueryDocumentSnapshot catListDoc = docList.get("Categories");

                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
