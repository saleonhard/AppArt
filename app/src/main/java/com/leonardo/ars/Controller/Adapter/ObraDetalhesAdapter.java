package com.leonardo.ars.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.leonardo.ars.Controller.AdmiradoresActivity;
import com.leonardo.ars.Controller.ComentarioActivity;
import com.leonardo.ars.Controller.Fragments.ObraDetalheFragment;
import com.leonardo.ars.Model.Obra;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ObraDetalhesAdapter extends RecyclerView.Adapter<ObraDetalhesAdapter.Viewholder> {

    private Context mContext;
    private List<Obra> mObras;

    private FirebaseUser firebaseUser;


    public ObraDetalhesAdapter(Context mContext, List<Obra> mObras) {
        this.mContext = mContext;
        this.mObras = mObras;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.obra_detalhes_main, parent, false);

        return new ObraDetalhesAdapter.Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NOME DA OBRA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


        final Obra obra = mObras.get(position);
        Picasso.get().load(obra.getImagemUrl()).into(holder.obraImagem);
        holder.toolbar.setTitle(obra.getNome());
        holder.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.obraNome.setText(obra.getNome());
        holder.valor.setText("R$ "+ obra.getValor().toString());
        holder.descricao.setText(obra.getDescricao().toString());
        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(obra.getArtista()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                if (usuario.getImagemUrl().equals("default")) {
                   holder.imagemPerfil.setImageResource(R.mipmap.ic_launcher);
                } else {
                    //Picasso.get().load(usuario.getImagemUrl()).placeholder(R.mipmap.ic_launcher).into(holder.imagemPerfil);
                }
                holder.username.setText(usuario.getUsername());
               
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Aplaudir(obra.getObraId(), holder.like);
        noOfLikes(obra.getObraId(), holder.numDeAplausos);
        Favoritar(obra.getObraId(), holder.salvar);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.like.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes")
                            .child(obra.getObraId()).child(firebaseUser.getUid()).setValue(true);

                    addNotificacao(obra.getObraId(), obra.getArtista());
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes")
                            .child(obra.getObraId()).child(firebaseUser.getUid()).removeValue();
                }

            }
        });

        holder.comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ComentarioActivity.class);
                intent.putExtra("obraId",obra.getObraId());
                intent.putExtra("autorId", obra.getArtista());
                mContext.startActivity(intent);
            }
        });

        holder.numDeComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ComentarioActivity.class);
                intent.putExtra("obraId", obra.getObraId());
                intent.putExtra("autorId", obra.getArtista());
                mContext.startActivity(intent);
            }
        });

        holder.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.salvar.getTag().equals("save")) {
                    FirebaseDatabase.getInstance().getReference().child("Saves")
                            .child(firebaseUser.getUid()).child(obra.getObraId()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Saves")
                            .child(firebaseUser.getUid()).child(obra.getObraId()).removeValue();
                }
            }
        });

        /*holder.imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
                        .edit().putString("profileId", obra.getArtista()).apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PerfilFragment()).commit();
            }
        });

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
                        .edit().putString("perfilId", obra.getArtista()).apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PerfilFragment()).commit();
            }
        });*/

       /* holder.valor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
                        .edit().putString("perfilId", obra.getArtista()).apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PerfilFragment()).commit();
            }
        });*/

        holder.obraImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mContext, ObraDetalhesActivity.class);
                mContext.startActivity(intent);*/
                /*mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("obraId", obra.getObraId()).apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ObraDetalheFragment()).commit();*/
            }
        });

        holder.numDeAplausos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdmiradoresActivity.class);
                intent.putExtra("id", obra.getArtista());
                intent.putExtra("title", "likes");
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mObras.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

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

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            toolbar = itemView.findViewById(R.id.toolbar2);
            imagemPerfil = itemView.findViewById(R.id.imagem_perfil);
            obraImagem = itemView.findViewById(R.id.obra_imagem);
            like = itemView.findViewById(R.id.like);
            comentario = itemView.findViewById(R.id.comentario);
            salvar = itemView.findViewById(R.id.save);
            //mais = itemView.findViewById(R.id.mais);

            username = itemView.findViewById(R.id.username);
            numDeAplausos = itemView.findViewById(R.id.num_de_likes);
            valor = itemView.findViewById(R.id.valor);
            numDeComentarios = itemView.findViewById(R.id.num_de_comentarios);
            obraNome = itemView.findViewById(R.id.obra_nome);
            descricao = itemView.findViewById(R.id.descricao);

        }
    }



    private void Favoritar (final String obraId, final ImageView image) {
        FirebaseDatabase.getInstance().getReference().child("Saves").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(obraId).exists()) {
                    image.setImageResource(R.drawable.ic_favorita_ativa);
                    image.setTag("saved");
                } else {
                    image.setImageResource(R.drawable.ic_favorita);
                    image.setTag("save");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Aplaudir(String obraId, final ImageView imageView) {
        FirebaseDatabase.getInstance().getReference().child("Likes").child(obraId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_aplaudir_ativo2);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_aplaudir_2);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void noOfLikes (String obraId, final TextView text) {
        FirebaseDatabase.getInstance().getReference().child("Likes").child(obraId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text.setText(dataSnapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
        private void addNotificacao(String obraId, String artistaId) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("usuarioId", artistaId);
        map.put("text", "aplaudiu sua obra.");
        map.put("obraId", obraId);
        map.put("postado", true);

        FirebaseDatabase.getInstance().getReference().child("Notificacoes").child(firebaseUser.getUid()).push().setValue(map);
    }
}
