package com.leonardo.ars.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leonardo.ars.Controller.Adapter.ObraAdapter;
import com.leonardo.ars.Controller.AdmiradoresActivity;
import com.leonardo.ars.Controller.ChatActivity;
import com.leonardo.ars.Controller.MainActivity;
import com.leonardo.ars.Controller.ObraActivity;
import com.leonardo.ars.Model.Obra;
import com.leonardo.ars.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewObras;
    private ObraAdapter obraAdapter;
    private List<Obra> obraList;



    private RecyclerView recyclerViewObrasLeilao;
    private ObraAdapter obraLeilaoAdapter;
    private List<Obra> obraLeilaoList;


    private List<String> admiradosList;

    private ImageView recados;

    private ImageView direta;
    private ImageView leiloes;

    private BottomNavigationView bottomNavigationView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);


        recados = view.findViewById(R.id.chat);

        recyclerViewObras = view.findViewById(R.id.recycler_view_vendas);
        recyclerViewObras.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewObras.setLayoutManager(linearLayoutManager);
        obraList = new ArrayList<>();
        obraAdapter = new ObraAdapter(getContext(), obraList);
        recyclerViewObras.setAdapter(obraAdapter);

        recyclerViewObrasLeilao = view.findViewById(R.id.recycler_view_leiloes);
        recyclerViewObrasLeilao.setHasFixedSize(true);
        recyclerViewObras.setLayoutManager(linearLayoutManager);
        obraLeilaoList = new ArrayList<>();
        obraLeilaoAdapter = new ObraAdapter(getContext(), obraLeilaoList);
        recyclerViewObrasLeilao.setAdapter(obraAdapter);




        admiradosList = new ArrayList<>();

        /* checkFollowingUsers();*/
        lerObras();


        recyclerViewObras.setVisibility(View.VISIBLE);
        recyclerViewObrasLeilao.setVisibility(View.GONE);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_direta:
                        recyclerViewObras.setVisibility(View.VISIBLE);
                        recyclerViewObrasLeilao.setVisibility(View.GONE);
                        break;

                    case R.id.nav_leilao:
                        recyclerViewObras.setVisibility(View.GONE);
                        recyclerViewObrasLeilao.setVisibility(View.VISIBLE);
                        break;
                }

               /* if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectorFragment).commit();
                }
*/
                return  true;

            }
        });



        recados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    /*private void checkFollowingUsers() {

        FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                admiradosList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    admiradosList.add(snapshot.getKey());
                }
                admiradosList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                lerObras();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    private void lerObras() {

        FirebaseDatabase.getInstance().getReference().child("Obras").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obraList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Obra obra = snapshot.getValue(Obra.class);
                    obraList.add(obra);

                    /*for (String id : admiradosList) {
                        if (obra.getArtista().equals(id)){
                            obraList.add(obra);
                        }
                    }*/
                }
                obraAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
