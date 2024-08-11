package com.leonardo.ars.Controller;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.leonardo.ars.Controller.Adapter.ObraAdapter;
import com.leonardo.ars.Model.Obra;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;
import com.leonardo.ars.databinding.ObraDetalhesMainBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ObraDetalhesActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ObraDetalhesMainBinding binding;

    private String obraId;
    private RecyclerView recyclerView;

    public ImageView imagemPerfil;
    public ImageView obraImagem;
    public ImageView like;
    public ImageView comentario;
    public ImageView salvar;
    public ImageView mais;

    public TextView username;
    public TextView numDeAplausos;
    public TextView obraNome;
    public TextView descricao;
    public TextView numDeComentarios;
    SocialTextView valor;
    Toolbar toolbar;
    ExtendedFloatingActionButton fab;

    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obra_detalhes_main);


        Intent intent = getIntent();
        obraId = intent.getStringExtra("obraId");

        toolbar = findViewById(R.id.toolbar2);

        imagemPerfil = findViewById(R.id.imagem_perfil);
        obraImagem = findViewById(R.id.obra_imagem);
        like = findViewById(R.id.like);
        comentario = findViewById(R.id.comentario);
        salvar = findViewById(R.id.save);

        username = findViewById(R.id.username);
        numDeAplausos = findViewById(R.id.num_de_likes);
        valor = findViewById(R.id.valor);
        numDeComentarios = findViewById(R.id.num_de_comentarios);
        obraNome = findViewById(R.id.obra_nome);
        descricao = findViewById(R.id.descricao);
        fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("obraNome"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        valor.setText("R$ " + intent.getStringExtra("obraValor"));
        descricao.setText(intent.getStringExtra("obraDescricao"));
        Picasso.get().load(intent.getStringExtra("obraImagem")).into(obraImagem);

        userId = intent.getStringExtra("obraArtistaId");

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                username.setText(dataSnapshot.child("username").getValue().toString());
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                if (usuario.getImagemUrl().equals("default")) {
                    imagemPerfil.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Picasso.get().load(usuario.getImagemUrl()).into(imagemPerfil);
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*FirebaseDatabase.getInstance().getReference().child("Usuarios").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                Picasso.get().load(usuario.getImagemUrl()).into(imagemPerfil);
                username.setText(usuario.getUsername());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/




      /*  binding = ObraDetalhesMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setSupportActionBar(binding.toolbar);*/



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "ABRE O CHAT", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




}