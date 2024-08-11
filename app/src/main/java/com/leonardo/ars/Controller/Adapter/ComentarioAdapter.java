package com.leonardo.ars.Controller.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leonardo.ars.Controller.MainActivity;
import com.leonardo.ars.Model.Comentario;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private Context mContext;
    private List<Comentario> mComentarios;

    String postId;

    private FirebaseUser fUser;



    public ComentarioAdapter(Context mContext, List<Comentario> mComentarios , String postId) {
        this.mContext = mContext;
        this.mComentarios = mComentarios;
        this.postId = postId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comentario_item, parent, false);
        return new ComentarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        final Comentario comentario = mComentarios.get(position);

        holder.comentario.setText(comentario.getComentario());

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(comentario.getUsuario()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                holder.username.setText(usuario.getUsername());
                if (usuario.getImagemUrl().equals("default")) {
                    holder.imagemPerfil.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Picasso.get().load(usuario.getImagemUrl()).into(holder.imagemPerfil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("usuarioId", comentario.getUsuario());
                mContext.startActivity(intent);
            }
        });

        holder.imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("usuarioId", comentario.getUsuario());
                mContext.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (comentario.getUsuario().endsWith(fUser.getUid())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Do you want to delete?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("Comentarios")
                                    .child(postId).child(comentario.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(mContext, "Comentario deletado com sucesso!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            });
                        }
                    });

                    alertDialog.show();
                }

                return true;
            };
        });
    }

    @Override
    public int getItemCount() {
        return mComentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imagemPerfil;
        public TextView username;
        public TextView comentario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemPerfil = itemView.findViewById(R.id.imagem_perfil);
            username = itemView.findViewById(R.id.username);
            comentario = itemView.findViewById(R.id.comentario);
        }
    }

}
