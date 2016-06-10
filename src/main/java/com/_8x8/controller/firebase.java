package com._8x8.controller;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class firebase {

    @RequestMapping(value = "/database", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserSession() {

        ClassLoader classLoader = getClass().getClassLoader();
        String Url = classLoader.getResource("serviceAccountCredentials.json").getPath();

        try {
            FirebaseOptions options;
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream(Url))
                    .setDatabaseUrl("https://zpay-4b5d6.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(firebase.class.getName()).log(Level.SEVERE, null, ex);
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("oAuthRecord/");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                Object document = ds.getValue();
                System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
