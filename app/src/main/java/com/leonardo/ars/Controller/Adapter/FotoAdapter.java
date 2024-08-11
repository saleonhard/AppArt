package com.leonardo.ars.Controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.leonardo.ars.Controller.Fragments.ObraDetalheFragment;
import com.leonardo.ars.Model.Obra;
import com.leonardo.ars.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {

    private Context mContext;
    private List<Obra> mObras;

    public FotoAdapter(Context mContext, List<Obra> mObras) {
        this.mContext = mContext;
        this.mObras = mObras;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.foto_item, parent, false);
        return  new FotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Obra obra = mObras.get(position);
        Picasso.get().load(obra.getImagemUrl()).placeholder(R.mipmap.ic_launcher).into(holder.obraImagem);

        holder.obraImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("Obraid", obra.getObraId()).apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ObraDetalheFragment()).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mObras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView obraImagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            obraImagem = itemView.findViewById(R.id.obra_imagem);
        }
    }

}
