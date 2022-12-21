package com.example.agendavirtual;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agendavirtual.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cambiarFragment(new ListadoTareasFragment());

        binding.menuInferior.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.menuListadoTareas:
                    cambiarFragment(new ListadoTareasFragment());
                    break;
                case R.id.menuAgregarTarea:
                    cambiarFragment(new AgregarTareaFragment());
                    break;
                case R.id.menuNotas:
                    cambiarFragment(new Notas());
                    break;
            }
            return true;
        });

    }

    private void cambiarFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmContenedorPrincipal, fragment);
        fragmentTransaction.commit();

    }


}