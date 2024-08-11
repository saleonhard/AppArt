package com.leonardo.ars.Controller.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leonardo.ars.Controller.Adapter.NotificacaoAdapter;
import com.leonardo.ars.Model.Notificacao;
import com.leonardo.ars.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificacaoFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificacaoAdapter notificacaoAdapter;
    private List<Notificacao> notificacaoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacao, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificacaoList = new ArrayList<>();
        notificacaoAdapter = new NotificacaoAdapter (getContext(), notificacaoList);
        recyclerView.setAdapter(notificacaoAdapter);

        lerNotificacoes();

        return view;
    }

    private void lerNotificacoes() {

        FirebaseDatabase.getInstance().getReference().child("Notificacoes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    notificacaoList.add(snapshot.getValue(Notificacao.class));
                }

                Collections.reverse(notificacaoList);
                notificacaoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
