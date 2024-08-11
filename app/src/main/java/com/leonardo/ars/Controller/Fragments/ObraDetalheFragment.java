package com.leonardo.ars.Controller.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leonardo.ars.Controller.Adapter.ObraAdapter;
import com.leonardo.ars.Controller.Adapter.ObraDetalhesAdapter;
import com.leonardo.ars.Model.Obra;
import com.leonardo.ars.R;

import java.util.ArrayList;
import java.util.List;

public class ObraDetalheFragment extends Fragment {

    private String obraId;
    private RecyclerView recyclerView;
    private ObraDetalhesAdapter obraDetalhesAdapter;
    private List<Obra> obraList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obra_detalhe,container, false);


        obraId = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("obraId", "none");

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        obraList = new ArrayList<>();
        obraDetalhesAdapter = new ObraDetalhesAdapter(getContext(), obraList);
        recyclerView.setAdapter(obraDetalhesAdapter);

        FirebaseDatabase.getInstance().getReference().child("Obras").child(obraId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obraList.clear();
                obraList.add(dataSnapshot.getValue(Obra.class));

                obraDetalhesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
