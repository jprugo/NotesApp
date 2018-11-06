package Adapters;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import Objects.Nota;
import jpsoft.com.notesapp.R;

public class AdaptadorNotas extends RecyclerView.Adapter {
    List<Nota> mNotes;
    private DatabaseReference mNotes_Database;
    FirebaseAuth mAuth;



    public AdaptadorNotas(List<Nota> mNotes) {
        this.mNotes = mNotes;
        mAuth= FirebaseAuth.getInstance();
        mNotes_Database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Notes");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_note, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Nota nota=mNotes.get(position);
        ((NoteHolder) holder).onBindViewHolder((NoteHolder) holder,position);

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    //clase
    private class NoteHolder extends RecyclerView.ViewHolder  {

        TextView TvtTitle;
        TextView TvBody;
        FloatingActionButton fabDelete;
        public NoteHolder(View view) {
            super(view);
            TvtTitle =(TextView) view.findViewById(R.id.textview_note_title);
            TvBody =(TextView) view.findViewById(R.id.textview_note_body);
            fabDelete=(FloatingActionButton) view.findViewById(R.id.fab_delete);

        }


        public void onBindViewHolder(final NoteHolder viewHolder, final int i) {

            /**
             * HashMap<String, String> userMap = new HashMap<>();
             *                     userMap.put("name", display_name);
             *                     userMap.put("device_token", device_token);
             * */
            Nota c = (Nota) mNotes.get(i);


            viewHolder.TvtTitle.setText(c.getTitulo());
            viewHolder.TvBody.setText(c.getCuerpo());
            fabDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        mNotes.remove(i);
                        mNotes_Database.setValue(mNotes);


                    }catch(Exception e){


                    }
                }
            });


        }

    }
}
