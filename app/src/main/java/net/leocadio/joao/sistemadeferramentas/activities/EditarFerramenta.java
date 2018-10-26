package net.leocadio.joao.sistemadeferramentas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

import net.leocadio.joao.sistemadeferramentas.R;
import net.leocadio.joao.sistemadeferramentas.dao.ConfigFirebase;
import net.leocadio.joao.sistemadeferramentas.models.Ferramenta;

public class EditarFerramenta extends AppCompatActivity {

    private EditText ferramenta, fabricante, preco, cor, referencia;
    private Button btnCancelar, btnAtualizar;
    private DatabaseReference firebase;
    private Ferramenta tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferramenta_editar);

        FirebaseApp.initializeApp(this);

        //formulario
        ferramenta = (EditText) findViewById(R.id.ferramenta);
        fabricante = (EditText) findViewById(R.id.fabricante);
        preco = (EditText) findViewById(R.id.preco);
        cor = (EditText) findViewById(R.id.cor);
        referencia = (EditText) findViewById(R.id.referencia);
        //botoes
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            ferramenta.setText(String.valueOf(bundle.get("ferramenta")));
            fabricante.setText(String.valueOf(bundle.get("fabricante")));
            preco.setText(String.valueOf(bundle.get("preco")));
            cor.setText(String.valueOf(bundle.get("cor")));
            referencia.setText(String.valueOf(bundle.get("referencia")));
        }

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditFerramenta.class));
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ferramenta tool = new Ferramenta();
                tool.setKey(ferramenta.getText().toString());
                tool.setFerramenta(ferramenta.getText().toString());
                tool.setFabricante(fabricante.getText().toString());
                tool.setPreco(preco.getText().toString());
                tool.setCor(cor.getText().toString());
                tool.setReferencia(referencia.getText().toString());
                //salva no banco
                atualizarDB(tool);
            }
        });
    }

    public boolean atualizarDB(Ferramenta ferramenta)
    {
        try {
            firebase = ConfigFirebase.getFirebase().child("ferramentas");
            firebase.child(ferramenta.getKey()).setValue(ferramenta);
            toast("Ferrameta atualizada com sucesso!");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
