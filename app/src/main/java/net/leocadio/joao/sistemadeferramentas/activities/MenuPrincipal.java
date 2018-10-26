package net.leocadio.joao.sistemadeferramentas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import net.leocadio.joao.sistemadeferramentas.R;
import net.leocadio.joao.sistemadeferramentas.dao.ConfigFirebase;

public class MenuPrincipal extends AppCompatActivity {

    private AlertDialog alerta;
    private FirebaseAuth usuarioFirebase;
    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        usuarioFirebase = ConfigFirebase.getFirebaseAutenticacao();

    }

    // definindo onClickListener para cada elemento
    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (finalI) {
                        case 0:
                            startActivity(new Intent(getApplicationContext(), CreateFerramenta.class));
                            break;
                        case 1:
                        case 2:
                        case 3:
                            startActivity(new Intent(getApplicationContext(), DelFerramenta.class));
                            break;
                        default:
                            break;

                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sobre) {
            exibeSobre();
        } else if (id == R.id.sair) {
            usuarioFirebase.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void exibeSobre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        //define o titulo
        builder.setTitle("Sobre");
        //mensagem
        builder.setMessage(R.string.about);
        //cria o alerta
        alerta = builder.create();
        //exibe o alerta
        alerta.show();
    }
}
