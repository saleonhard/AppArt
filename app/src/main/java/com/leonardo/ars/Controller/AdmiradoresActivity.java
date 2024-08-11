package com.leonardo.ars.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.leonardo.ars.Controller.Adapter.UsuarioAdapter;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;

import java.util.ArrayList;
import java.util.List;

public class AdmiradoresActivity extends AppCompatActivity {

    private  String id;
    private String titulo;
    private List<String> idList;

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> mUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admiradores);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        titulo = intent.getStringExtra("titulo");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUsuarios = new ArrayList<>();
        usuarioAdapter = new UsuarioAdapter(this, mUsuarios, false);
        recyclerView.setAdapter(usuarioAdapter);

        idList = new ArrayList<>();


    }






    }


