package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FireBaseService {
    FirebaseDatabase db;

    /*
    He probado tanto con /key.json como con key.json, en ambos salta null pointer exception y si no pongo / salta además
    File not found exception
    Eso ha sido sin poner Objects.requireNonNull(), poniendolo sigue dando null pointer exception
    */
    public FireBaseService() throws IOException {
        File file = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("/key.json")).getFile()
        );

        FileInputStream fis = new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(fis))
                .setDatabaseUrl("https://discontrol-10e47.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDb() {
        return db;
    }

}