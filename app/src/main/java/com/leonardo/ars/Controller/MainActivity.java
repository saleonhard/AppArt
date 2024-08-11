package com.leonardo.ars.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leonardo.ars.Controller.Fragments.NotificacaoFragment;
import com.leonardo.ars.Controller.Fragments.PesquisaFragment;
import com.leonardo.ars.Controller.Fragments.HomeFragment;
import com.leonardo.ars.Controller.Fragments.NotificacaoFragment;
import com.leonardo.ars.Controller.Fragments.PerfilFragment;

import com.leonardo.ars.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.nav_pesquisar:
                        selectorFragment = new PesquisaFragment();
                        break;

                    case R.id.nav_add :
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this , ObraActivity.class));
                        break;

                    case R.id.nav_notificacao:
                        selectorFragment = new NotificacaoFragment();
                        break;

                    case R.id.nav_perfil:
                        selectorFragment = new PerfilFragment();
                        break;
                }

                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectorFragment).commit();
                }

                return  true;

            }
        });
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String profileId = intent.getString("usuarioId");

            getSharedPreferences("PERFIL", MODE_PRIVATE).edit().putString("perfilId", profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_perfil);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new HomeFragment()).commit();
        }
    }
}
