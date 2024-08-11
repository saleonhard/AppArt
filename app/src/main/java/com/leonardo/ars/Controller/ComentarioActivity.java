package com.leonardo.ars.Controller;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leonardo.ars.Controller.Adapter.ComentarioAdapter;
import com.leonardo.ars.Model.Comentario;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComentarioActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private ComentarioAdapter comentarioAdapter;
    private List<Comentario> comentarioList;

    private EditText addComentario;
    private CircleImageView imagemPerfil;
    private TextView post;

    private String obraId;
    private String autorId;

    FirebaseUser fUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comentários");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        obraId = intent.getStringExtra("obraId");
        autorId = intent.getStringExtra("autorId");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        comentarioList = new ArrayList<>();
        comentarioAdapter = new ComentarioAdapter(this, comentarioList, obraId);

        recyclerView.setAdapter(comentarioAdapter);

        addComentario = findViewById(R.id.add_comentario);
        imagemPerfil = findViewById(R.id.imagem_perfil);
        post = findViewById(R.id.post);

        fUsuario = FirebaseAuth.getInstance().getCurrentUser();

        getUsuarioImagem();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addComentario.getText().toString())) {
                    Toast.makeText(ComentarioActivity.this, "Nenhum comentário adcionado!", Toast.LENGTH_SHORT).show();
                } else {
                    putComentario();
                }
            }
        });

        getComentario();

    }

    private void getComentario() {

        FirebaseDatabase.getInstance().getReference().child("Comentarios").child(obraId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comentarioList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comentario comentario = snapshot.getValue(Comentario.class);
                    comentarioList.add(comentario);
                }

                comentarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void putComentario() {

        HashMap<String, Object> map = new HashMap<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Comentarios").child(obraId);

        String id = ref.push().getKey();

        map.put("id", id);
        map.put("comentario", addComentario.getText().toString());
        map.put("usuario", fUsuario.getUid());

        addComentario.setText("");

        ref.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ComentarioActivity.this, "Comentário adicionado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ComentarioActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getUsuarioImagem() {

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(fUsuario.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

    }

}
