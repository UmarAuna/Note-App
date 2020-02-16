package com.auna.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNote extends AppCompatActivity {

    private EditText edtTitle, edtContent;
    private Button btnAdd;

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
        btnAdd.setOnClickListener((view)->add());

        if(getIntent() != null && getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
            btnAdd.setText("Update");
            noteRef.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Note note = dataSnapshot.getValue(Note.class);
                    edtTitle.setText(note.getTitle());
                    edtContent.setText(note.getContent());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

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
        }else{
            Note note = new Note(id, title, content);
            noteRef.child(id).setValue(note);

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            finish();
        }

        edtTitle.setText("");
        edtContent.setText("");
    }
}
