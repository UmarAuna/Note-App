package com.auna.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView nodata;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView noteRecycler = findViewById(R.id.recycler_notes);
         nodata = findViewById(R.id.no_data);
        NoteAdapter noteAdapter = new NoteAdapter(note -> {
            Intent intent = new Intent(this, AddNote.class);
            intent.putExtra("id", note.getId());
            startActivity(intent);
        });
        noteRecycler.setAdapter(noteAdapter);
        noteRecycler.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_goto_add).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddNote.class)));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference noteRef = database.getReference("Note");

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    //noteAdapter.setNotes(Arrays.asList(dataSnapshot.getValue(Note[].class)));
                    nodata.setVisibility(View.GONE);
                    List<Note> notes = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        notes.add(child.getValue(Note.class));
                    }
                    noteAdapter.setNotes(notes);
                }else {
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();
                Log.e("FIREBASE", databaseError.getMessage(), databaseError.toException());
            }
        });
    }
}
