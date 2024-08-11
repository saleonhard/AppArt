package com.leonardo.ars.Controller;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.leonardo.ars.R;

import java.util.HashMap;

public class RegistrarActivity extends AppCompatActivity {

    private EditText username;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button registrar;
    private TextView loginUsuario;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        username = findViewById(R.id.username);
        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        registrar = findViewById(R.id.registrar);
        loginUsuario = findViewById(R.id.login_usuario);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        loginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrarActivity.this , LoginActivity.class));
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = username.getText().toString();
                String txtNome = nome.getText().toString();
                String txtEmail = email.getText().toString();
                String txtSenha = senha.getText().toString();

                if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtNome)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtSenha)){
                    Toast.makeText(RegistrarActivity.this, "Credenciais Vazias!", Toast.LENGTH_SHORT).show();
                } else if (txtSenha.length() < 6){
                    Toast.makeText(RegistrarActivity.this, "Senha muito curta!", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUser(txtUsername , txtNome , txtEmail , txtSenha);
                }
            }
        });
    }

    private void registrarUser(final String username, final String nome, final String email, String senha) {

        pd.setMessage("Por favor, espere!");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email , senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String , Object> map = new HashMap<>();
                map.put("nome" , nome);
                map.put("email", email);
                map.put("username" , username);
                map.put("id" , mAuth.getCurrentUser().getUid());
                map.put("bio" , "");
                map.put("imagemUrl" , "default");

                mRootRef.child("Usuarios").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            Toast.makeText(RegistrarActivity.this, "Atualize o perfil " +
                                    "para melhorar sua experiência", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrarActivity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegistrarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}




