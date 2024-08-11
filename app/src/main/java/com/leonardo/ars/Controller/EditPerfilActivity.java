package com.leonardo.ars.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.leonardo.ars.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.leonardo.ars.Model.Usuario;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPerfilActivity extends AppCompatActivity {


    private ImageView fechar;
    private CircleImageView imagemPerfil;
    private TextView salvar;
    private TextView alterarFoto;
    private MaterialEditText nome;
    private MaterialEditText username;
    private MaterialEditText bio;

    private FirebaseUser fUser;

    private Uri mImagemUri;
    private StorageTask uploadTask;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle salvardInstanceState) {
        super.onCreate(salvardInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        fechar = findViewById(R.id.fechar);
        imagemPerfil = findViewById(R.id.imagem_perfil);
        salvar = findViewById(R.id.salvar);
        alterarFoto = findViewById(R.id.alterar_foto);
        nome = findViewById(R.id.nome);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference().child("Uploads");

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(fUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                nome.setText(usuario.getNome());
                username.setText(usuario.getUsername());
                bio.setText(usuario.getBio());
                Picasso.get().load(usuario.getImagemUrl()).into(imagemPerfil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        alterarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditPerfilActivity.this);
            }
        });

        imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditPerfilActivity.this);
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarPerfil();
            }
        });
    }

    private void atualizarPerfil() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nome", nome.getText().toString());
        map.put("username", username.getText().toString());
        map.put("bio", bio.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(fUser.getUid()).updateChildren(map);
    }

    private void uploadImagem() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Atualizando...");
        pd.show();

        if (mImagemUri != null) {
            final StorageReference fileRef = storageRef.child(System.currentTimeMillis() + ".jpeg");

            uploadTask = fileRef.putFile(mImagemUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return  fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String url = downloadUri.toString();

                        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(fUser.getUid()).child("imagemUrl").setValue(url);
                        pd.dismiss();
                    } else {
                        Toast.makeText(EditPerfilActivity.this, "Upload falhou!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Nenhuma imagem selecionada!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImagemUri = result.getUri();

            uploadImagem();
        } else {
            Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_SHORT).show();
        }
    }
}
