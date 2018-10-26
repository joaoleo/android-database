package net.leocadio.joao.sistemadeferramentas.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import net.leocadio.joao.sistemadeferramentas.R;
import net.leocadio.joao.sistemadeferramentas.adapters.FerramentasAdapter;
import net.leocadio.joao.sistemadeferramentas.dao.ConfigFirebase;
import net.leocadio.joao.sistemadeferramentas.models.Ferramenta;

import java.util.ArrayList;

public class EditFerramenta extends AppCompatActivity {

    private Button btnVoltar;
    private ListView listView;
    private ArrayAdapter<Ferramenta> adapter;
    private ArrayList<Ferramenta> ferramentas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerFerramentas;
    private AlertDialog alerta;
    private Ferramenta toolEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferramenta_edit);

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        ferramentas = new ArrayList<Ferramenta>();
        listView = (ListView) findViewById(R.id.listViewFerramentas);
        adapter = new FerramentasAdapter(this, ferramentas);
        listView.setAdapter(adapter);

        firebase = ConfigFirebase.getFirebase().child("ferramentas");

        valueEventListenerFerramentas = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ferramentas.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Ferramenta ferramentaNovo = dados.getValue(Ferramenta.class);
                    ferramentas.add(ferramentaNovo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast("clicado em: " + position);
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerFerramentas);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerFerramentas);
    }
}
