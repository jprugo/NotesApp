package jpsoft.com.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.AdaptadorNotas;
import Objects.Nota;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mNotes_Database;
    FirebaseAuth mAuth;
    ArrayList<Nota> ListaNotas;
    private RecyclerView RecyclerViewNotesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListaNotas=new ArrayList<>();

        RecyclerViewNotesList =(RecyclerView) findViewById(R.id.content_main_listview_notes);
        RecyclerViewNotesList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        RecyclerViewNotesList.setLayoutManager(llm);

        try{

            mAuth= FirebaseAuth.getInstance();
            mNotes_Database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Notes");

            mNotes_Database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ListaNotas=new ArrayList<>();// con esto se soluciona lo duplicado
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Nota n = data.getValue(Nota.class);
                        ListaNotas.add(n);
                    }
                    AdaptadorNotas adapter = new AdaptadorNotas(ListaNotas);
                    RecyclerViewNotesList.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        catch(Exception e){
            Log.i("Error",e.toString());

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainActivity.this, AddNote_Activity.class);
                startActivity(mainIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Cerrado", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(mainIntent);
        }
        return true;


    }
}
