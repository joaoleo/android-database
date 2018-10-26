package net.leocadio.joao.sistemadeferramentas.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import net.leocadio.joao.sistemadeferramentas.dao.ConfigFirebase;

@IgnoreExtraProperties
public class Usuario {

    private String id;
    private String email;
    private String senha;

    public Usuario() { }

    public void salvar() {
        DatabaseReference reference = ConfigFirebase.getFirebase();
        reference.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
