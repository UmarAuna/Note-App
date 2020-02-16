package com.auna.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AddNote extends AppCompatActivity {

    private EditText edtTitle, edtContent;
    private Button btnAdd, btnDelete;

    private String id = null;

    DatabaseReference noteRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        noteRef = database.getReference("Note");

        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnAdd.setOnClickListener((view)->add());

        if(getIntent() != null && getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
            btnAdd.setText("Update");
            noteRef.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Note note = dataSnapshot.getValue(Note.class);
                        edtTitle.setText(note.getTitle());
                        edtContent.setText(note.getContent());
                    }else{
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            btnDelete.setVisibility(View.GONE);
        }

        btnDelete.setOnClickListener(v -> delete());

    }

    public void delete(){
        String  _id = id;

        noteRef.child(_id).removeValue().addOnCompleteListener(task -> {
            finish();
            id = null;
            edtTitle.setText("");
            edtContent.setText("");
        });
    }

    public void add(){
        // Write a message to the database
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();

        if(id == null) {
            DatabaseReference newNote = noteRef.push();
            Note note = new Note(newNote.getKey(), title, content);
            newNote.setValue(note);

            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddNote.this, MainActivity.class));
        }else{
            Note note = new Note(id, title, content);
            noteRef.child(id).setValue(note);

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddNote.this, MainActivity.class));
        }

        edtTitle.setText("");
        edtContent.setText("");
    }
}
