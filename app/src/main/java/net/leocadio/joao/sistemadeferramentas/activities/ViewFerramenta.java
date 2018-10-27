package net.leocadio.joao.sistemadeferramentas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import net.leocadio.joao.sistemadeferramentas.R;

public class ViewFerramenta extends AppCompatActivity {

    private TextView ferramenta, fabricante, preco, cor, referencia;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferramenta_view);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ferramenta = (TextView) findViewById(R.id.ferramenta);
        fabricante = (TextView) findViewById(R.id.fabricante);
        preco = (TextView) findViewById(R.id.preco);
        cor = (TextView) findViewById(R.id.cor);
        referencia = (TextView) findViewById(R.id.referencia);

        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        if (bundle != null) {
            ferramenta.setText(String.valueOf(bundle.get("ferramenta")));
            fabricante.setText(String.valueOf(bundle.get("fabricante")));
            preco.setText(String.valueOf(bundle.get("preco")));
            cor.setText(String.valueOf(bundle.get("cor")));
            referencia.setText(String.valueOf(bundle.get("referencia")));
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PesquisaFerramenta.class));
            }
        });
    }
}
