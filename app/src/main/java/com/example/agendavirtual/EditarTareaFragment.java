package com.example.agendavirtual;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendavirtual.models.Tarea;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarTareaFragment extends Fragment {

    EditText edtNombreTarea, edtFechaTarea, edtHoraTarea, edtDescripcionTarea;
    Button btnGuardarCambios, btnBorrarTarea;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editar_tarea, container, false);

        edtNombreTarea = v.findViewById(R.id.edtEdicionNombreTarea);
        edtFechaTarea = v.findViewById(R.id.edtEdicionFechaTarea);
        edtHoraTarea = v.findViewById(R.id.edtEdicionHoraTarea);
        edtDescripcionTarea = v.findViewById(R.id.edtEdicionDescripcionTarea);
        btnGuardarCambios = v.findViewById(R.id.btnGuardarCambios);
        btnBorrarTarea = v.findViewById(R.id.btnBorrarTarea);

        inicializarFirebase();

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String id = result.getString("id");
                String nombre = result.getString("nombre");
                String fecha = result.getString("fecha");
                String hora = result.getString("hora");
                String descripcion = result.getString("descripcion");

                edtNombreTarea.setText(nombre);
                edtFechaTarea.setText(fecha);
                edtHoraTarea.setText(hora);
                edtDescripcionTarea.setText(descripcion);

                btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Tarea t = new Tarea();
                        t.setUid(id.trim());
                        t.setNombre(edtNombreTarea.getText().toString().trim());
                        t.setFecha(edtFechaTarea.getText().toString().trim());
                        t.setHora(edtHoraTarea.getText().toString().trim());
                        t.setDescripcion(edtDescripcionTarea.getText().toString().trim());
                        databaseReference.child("Tarea").child(t.getUid()).setValue(t);
                        Toast.makeText(getContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
                        cambiarFragment(new ListadoTareasFragment());
                    }
                });

                btnBorrarTarea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Tarea t = new Tarea();
                        t.setUid(id.trim());
                        databaseReference.child("Tarea").child(t.getUid()).removeValue();
                        Toast.makeText(getContext(), "Tarea Eliminada", Toast.LENGTH_SHORT).show();
                        cambiarFragment(new ListadoTareasFragment());
                    }
                });


            }
        });

        return v;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void cambiarFragment(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frmContenedorPrincipal, fragment);
        fragmentTransaction.commit();

    }

}