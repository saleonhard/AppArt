package com.leonardo.ars.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hendraanggrian.appcompat.socialview.Hashtag;
import com.hendraanggrian.appcompat.widget.HashtagArrayAdapter;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.leonardo.ars.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.List;

public class ObraActivity extends AppCompatActivity {

    private Uri imagemUri;
    private String imagemUrl;

    private ImageView fechar;
    private ImageView imagemAdd;
    private TextView publicar;
    SocialAutoCompleteTextView descricao;

    SocialAutoCompleteTextView valor;
    SocialAutoCompleteTextView nome;


    private RadioGroup radioGroup;
    private RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra);

        fechar = findViewById(R.id.fechar);
        imagemAdd = findViewById(R.id.imagem_add);
        publicar = findViewById(R.id.publicar);
        descricao = findViewById(R.id.descricao);
        nome = findViewById(R.id.obra_nome);
        valor = findViewById(R.id.valor);

        radioGroup = (RadioGroup) findViewById(R.id.rGroup);



        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ObraActivity.this , MainActivity.class));
                finish();
            }
        });

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        CropImage.activity().start(ObraActivity.this);
    }

    private void upload() {


        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        Toast.makeText(ObraActivity.this,
                radioButton.getText(), Toast.LENGTH_SHORT).show();


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Enviando...");
        pd.show();



        if (imagemUri != null){
            final StorageReference filePath = FirebaseStorage.getInstance().getReference("Obras").child(System.currentTimeMillis() + "." + getFileExtension(imagemUri));

            StorageTask uploadtask = filePath.putFile(imagemUri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    imagemUrl = downloadUri.toString();


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Obras");
                    String obraId = ref.push().getKey();

                    HashMap<String , Object> map = new HashMap<>();
                    map.put("obraId" , obraId);
                    map.put("imagemUrl" , imagemUrl);
                    map.put("nome" , nome.getText().toString());
                    map.put("valor" , valor.getText().toString());
                    map.put("descricao" , descricao.getText().toString());
                    map.put("artista" , FirebaseAuth.getInstance().getCurrentUser().getUid());

                    ref.child(obraId).setValue(map);

                    DatabaseReference mHashTagRef = FirebaseDatabase.getInstance().getReference().child("HashTags");
                    List<String> hashTags = descricao.getHashtags();
                    if (!hashTags.isEmpty()){
                        for (String tag : hashTags){
                            map.clear();

                            map.put("tag" , tag.toLowerCase());
                            map.put("obraId" , obraId);

                            mHashTagRef.child(tag.toLowerCase()).child(obraId).setValue(map);
                        }
                    }

                    pd.dismiss();
                    startActivity(new Intent(ObraActivity.this , MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ObraActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Nenhuma imagem foi selecionada!", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri) {
        System.out.print(MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri)));
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imagemUri = result.getUri();

            imagemAdd.setImageURI(imagemUri);
        } else {
            Toast.makeText(this, "Tente novamente!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ObraActivity.this , MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayAdapter<Hashtag> hashtagAdapter = new HashtagArrayAdapter<>(getApplicationContext());

        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hashtagAdapter.add(new Hashtag(snapshot.getKey() , (int) snapshot.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        descricao.setHashtagAdapter(hashtagAdapter);
    }
    
}
