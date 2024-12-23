package com.example.quizapp;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    public static FirebaseFirestore  get_firestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> get_catList = new ArrayList<CategoryModel>();
    public static  List<TestModel> get_testList = new ArrayList<>();
    public static int get_selected_cat_index = 0;
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

//    public static void loadCategories(CompleteListener completeListener) {
//        get_catList.clear();
//
//        get_firestore.collection("quiz").get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();
//
//                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                        docList.put(doc.getId(), doc);
//                    }
//
//                    QueryDocumentSnapshot catListDoc = docList.get("Categories");
//
//                    // Validate if Categories document exists
//                    if (catListDoc == null) {
//                        completeListener.onFailure();
//                        return;
//                    }
//
//                    // Validate if Count field exists
//                    Long catCount = catListDoc.getLong("Count");
//                    if (catCount == null) {
//                        completeListener.onFailure();
//                        return;
//                    }
//
//                    for (int i = 1; i <= catCount; i++) {
//                        String catID = catListDoc.getString("Cat_ID" + i);
//
//                        // Validate if category ID exists
//                        if (catID == null) {
//                            completeListener.onFailure();
//                            return;
//                        }
//
//                        QueryDocumentSnapshot catDoc = docList.get(catID);
//
//                        // Validate if category document exists
//                        if (catDoc == null) {
//                            completeListener.onFailure();
//                            return;
//                        }
//
//                        String catName = catDoc.getString("Name");
//                        Long noOfTests = catDoc.getLong("No_of_Test");
//
//                        // Validate if fields exist in the category document
//                        if (catName == null || noOfTests == null) {
//                            completeListener.onFailure();
//                            return;
//                        }
//
//                        get_catList.add(new CategoryModel(catID, catName, noOfTests.intValue()));
//                    }
//
//                    completeListener.onSuccess();
//                })
//                .addOnFailureListener(e -> completeListener.onFailure());
//    }

    public static void loadCategories(CompleteListener completeListener){
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

                    //access field count under categories document
                    long catCount = catListDoc.getLong("Count");
                    for(int i=1; i<=catCount; i++){

                        //access field key  under categories document using count or their number
                        String catID = catListDoc.getString("Cat_ID" + String.valueOf(i));

                        //using that key access the them under document
                        QueryDocumentSnapshot catDoc = docList.get(catID);
                        int numOfTests = catDoc.getLong("No_of_Test").intValue();
                        String catName = catDoc.getString("Name");
                        get_catList.add(new CategoryModel(catID, catName, numOfTests));
                        }
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

    public static void loadTestData(CompleteListener completeListener){
        get_testList.clear();
        //collection name for tests from firestore
        get_firestore.collection("quiz").document(get_catList.get(get_selected_cat_index).getDocID())
                .collection("Tests_List").document("Tests_Info")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        int numOfTests = get_catList.get(get_selected_cat_index).getNoOfTests();

                        for (int i =1; i <= numOfTests ; i++ ){
                            get_testList.add(new TestModel(
                                    documentSnapshot.getString("Test" + String.valueOf(i)+ "_ID"),
                                    0,
                                    Objects.requireNonNull(documentSnapshot.getLong("Test" + String.valueOf(i) + "_Time")).intValue()
                            ));
                        }
                        completeListener.onSuccess();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });

    }
}
