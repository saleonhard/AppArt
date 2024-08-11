package com.leonardo.ars.Controller.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leonardo.ars.Controller.Adapter.FotoAdapter;
import com.leonardo.ars.Controller.AdmiradoresActivity;
import com.leonardo.ars.Controller.ChatActivity;
import com.leonardo.ars.Controller.StartActivity;
import com.leonardo.ars.Model.Obra;

import com.leonardo.ars.Controller.EditPerfilActivity;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment {

    private RecyclerView recyclerViewSaves;
    private FotoAdapter postAdapterSaves;
    private List<Obra> minhasObras;

    private RecyclerView recyclerView;
    private FotoAdapter FotoAdapter;
    private List<Obra> minhasObrasList;

    private CircleImageView imagemPerfil;
    private ImageView logout;
    private TextView admiradores;
    private TextView following;
    private TextView obras;
    private TextView nome;
    private TextView bio;
    private TextView username;


    private ImageView minhasFotos;
    private ImageView savedPictures;

    private Button editPerfil;

    private FirebaseUser fUser;

    String perfilId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        String data = getContext().getSharedPreferences("PERFIL", Context.MODE_PRIVATE).getString("perfilId", "none");

        if (data.equals("none")) {

            perfilId = fUser.getUid();

        } else {
            perfilId = data;
            getContext().getSharedPreferences("PERFIL", Context.MODE_PRIVATE).edit().clear().apply();
        }

        imagemPerfil = view.findViewById(R.id.imagem_perfil);
        logout = view.findViewById(R.id.logout);
        admiradores = view.findViewById(R.id.admiradores);
        following = view.findViewById(R.id.admirando);
        obras = view.findViewById(R.id.obras);
        nome = view.findViewById(R.id.nome);
        bio = view.findViewById(R.id.bio);
        username = view.findViewById(R.id.username);
        minhasFotos = view.findViewById(R.id.minhas_obras);
        savedPictures = view.findViewById(R.id.obras_salvas);
        editPerfil = view.findViewById(R.id.edit_perfil);


        recyclerView = view.findViewById(R.id.recycler_view_minhas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        minhasObrasList = new ArrayList<>();
        FotoAdapter = new FotoAdapter(getContext(), minhasObrasList);
        recyclerView.setAdapter(FotoAdapter);

        recyclerViewSaves = view.findViewById(R.id.recycler_view_salvas);
        recyclerViewSaves.setHasFixedSize(true);
        recyclerViewSaves.setLayoutManager(new GridLayoutManager(getContext(), 3));


        recyclerViewSaves.setAdapter(postAdapterSaves);


        usuarioInfo();

        editPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = editPerfil.getText().toString();
                    //startActivity(new Intent(getContext(), editPerfilActivity.class));
                    Intent intent = new Intent(getContext(), EditPerfilActivity.class);
                    //startActivity(new Intent (ProfileFragment.this, editPerfilActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    startActivity(intent);

            }
        });


        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewSaves.setVisibility(View.GONE);

        minhasFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewSaves.setVisibility(View.GONE);


            }
        });

        savedPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerViewSaves.setVisibility(View.VISIBLE);
            }
        });

        admiradores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdmiradoresActivity.class);
                intent.putExtra("id", perfilId);
                intent.putExtra("title", "admiradores");
                startActivity(intent);
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdmiradoresActivity.class);
                intent.putExtra("id", perfilId);
                intent.putExtra("title", "followings");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void usuarioInfo() {

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(perfilId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                Picasso.get().load(usuario.getImagemUrl()).into(imagemPerfil);
                username.setText(usuario.getUsername());
                nome.setText(usuario.getNome());
                bio.setText(usuario.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
