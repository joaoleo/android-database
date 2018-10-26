package net.leocadio.joao.sistemadeferramentas.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import net.leocadio.joao.sistemadeferramentas.R;
import net.leocadio.joao.sistemadeferramentas.dao.ConfigFirebase;

public class Login extends AppCompatActivity {

    private static final String TAG = "EmailSenha";

    private EditText txtEmail, txtSenha;
    private Button btnLogin;
    private TextView signUp;
    private ProgressBar progressBar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        btnLogin = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.signUp);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        progressBar = findViewById(R.id.progressBar);

        //tela de registro
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        //tela de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                if (validacao(email, senha)) {
                    validaLogin(email, senha);
                } else {
                    toast("Preencha os campos de e-mail e senha!");
                }
            }
        });
    }

    public void validaLogin(String email, String senha)
    {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword:success");
                            startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
                            toast("Login efetuado com sucesso");
                        } else {
                            Log.d(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            toast("Usuário ou senha inválidos");
                        }
                    }
                });
    }

    public boolean validacao(String email, String senha)
    {
        boolean valido = true;
        if (TextUtils.isEmpty(email)) {
            toast("Digite o E-mail");
            valido = false;
        }
        if (TextUtils.isEmpty(senha)) {
            toast("Digite a Senha!");
            valido = false;
        }
        return valido;
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
