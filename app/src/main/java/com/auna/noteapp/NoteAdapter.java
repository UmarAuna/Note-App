package com.auna.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private NoteClickListener noteClickListener;

    public NoteAdapter(NoteClickListener noteClickListener){
        this.noteClickListener = noteClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.txtTitle.setText(note.getTitle());
        holder.txtContent.setText(note.getContent());
        holder.itemView.setOnClickListener(v -> noteClickListener.onNoteClicked(note));
    }

    @Override
    public int getItemCount() {
        if(notes == null){
            return 0;
        }
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtContent;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtContent = itemView.findViewById(R.id.txt_content);
        }
    }

    interface NoteClickListener{
        void onNoteClicked(Note note);
    }
}
