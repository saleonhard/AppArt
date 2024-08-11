package com.leonardo.ars.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;



import com.leonardo.ars.Controller.Fragments.PerfilFragment;
import com.leonardo.ars.Controller.MainActivity;
import com.leonardo.ars.Model.Usuario;
import com.leonardo.ars.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder>{

    private Context mContext;
    private List<Usuario> mUsuarios;
    private boolean isFargment;



    public UsuarioAdapter(Context mContext, List<Usuario> mUsuarios, boolean isFargment) {
        this.mContext = mContext;
        this.mUsuarios = mUsuarios;
        this.isFargment = isFargment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.usuario_item , parent , false);
        return new UsuarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

    

        final Usuario usuario = mUsuarios.get(position);
        holder.btnSeguir.setVisibility(View.VISIBLE);

        holder.username.setText(usuario.getUsername());
        holder.nome.setText(usuario.getNome());

        Picasso.get().load(usuario.getImagemUrl()).placeholder(R.mipmap.ic_launcher).into(holder.imagemPerfil);

        isFollowed(usuario.getId() , holder.btnSeguir);





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFargment) {
                    mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE).edit().putString("profileId", usuario.getId()).apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("publisherId", usuario.getId());
                    mContext.startActivity(intent);
                }
            }
        });

    }

    private void isFollowed(final String id, final Button btnSeguir) {

        

            
      

    }

    @Override
    public int getItemCount() {
        return mUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imagemPerfil;
        public TextView username;
        public TextView nome;
        public Button btnSeguir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemPerfil = itemView.findViewById(R.id.imagem_perfil);
            username = itemView.findViewById(R.id.username);
            nome = itemView.findViewById(R.id.nome);
            btnSeguir = itemView.findViewById(R.id.btn_seguir);
        }
    }

    private void addNotification(String userId) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("userid", userId);
        map.put("text", "started following you.");
        map.put("postid", "");
        map.put("isPost", false);

       
    }

}
