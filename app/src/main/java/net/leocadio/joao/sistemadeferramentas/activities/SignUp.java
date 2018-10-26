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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import net.leocadio.joao.sistemadeferramentas.R;
import net.leocadio.joao.sistemadeferramentas.dao.ConfigFirebase;
import net.leocadio.joao.sistemadeferramentas.models.Usuario;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "EmailSenha";

    private EditText txtEmail, txtSenha;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseApp.initializeApp(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnSignUp = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();

                usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);

                if (validacao(email, senha)) {
                    progressBar.setVisibility(View.VISIBLE);

                    autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                    autenticacao.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        toast("Conta cadastrada com sucesso!");
                                        usuario.salvar();
                                        startActivity(new Intent(SignUp.this, Login.class));
                                    } else {
                                        Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                        toast("Erro ao efetuar o cadastro!");
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public boolean validacao(String email, String senha) {
        boolean valido = true;
        if (TextUtils.isEmpty(email)) {
            toast("Digite o E-mail");
            valido = false;
        }

        if (TextUtils.isEmpty(senha)) {
            toast("Digite a senha!");
            valido = false;
        }
        if (senha.length() < 6) {
            toast("Senha muito curta, mínimo 6 caracteres!");
            valido = false;
        }
        return valido;
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
