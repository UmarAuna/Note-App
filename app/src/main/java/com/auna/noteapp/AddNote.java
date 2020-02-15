package com.auna.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNote extends AppCompatActivity {

    private EditText edtTitle, edtContent;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener((view)->add());

    }

    public void add(){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference noteRef = database.getReference("Note");

        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();

        Note note = new Note(title, content);

        noteRef.push().setValue(note);

        edtTitle.setText("");
        edtContent.setText("");
    }
}
