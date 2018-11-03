package jpsoft.com.notesapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Nota;

public class AddNote_Activity extends AppCompatActivity {

    private DatabaseReference mNotes_Database;
    FirebaseAuth mAuth;
    ArrayList<Nota> ListaNotas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_);
        Button SaveButton=(Button) findViewById(R.id.savenote_btn);
        final EditText TitleEditText=(EditText) findViewById(R.id.edtext_title);
        final EditText BodyEditText=(EditText) findViewById(R.id.edtext_body);

        mAuth= FirebaseAuth.getInstance();
        mNotes_Database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Notes");

        mNotes_Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListaNotas= (ArrayList<Nota>) dataSnapshot.getValue();
                if(ListaNotas==null){


                    ListaNotas=new ArrayList<>();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String Titulo=TitleEditText.getText().toString();
                    String Cuerpo=BodyEditText.getText().toString();
                    Nota n=new Nota(Titulo,Cuerpo);
                    ListaNotas.add(n);
                    mNotes_Database.setValue(ListaNotas);
                    finish();

                }catch(Exception e){


                }
            }
        });
    }
}
