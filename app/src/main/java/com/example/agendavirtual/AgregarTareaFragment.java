package com.example.agendavirtual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agendavirtual.models.Tarea;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AgregarTareaFragment extends Fragment {

    Button btnAgregarTarea;
    EditText edtNombreTarea, edtFechaTarea, edtHoraTarea, edtDescripcionTarea;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_agregar_tarea, container, false);

        edtNombreTarea = v.findViewById(R.id.edtNombreTarea);
        edtFechaTarea = v.findViewById(R.id.edtFechaTarea);
        edtHoraTarea = v.findViewById(R.id.edtHoraTarea);
        edtDescripcionTarea = v.findViewById(R.id.edtDescripcionTarea);
        btnAgregarTarea = v.findViewById(R.id.btnAgregarTarea);

        inicializarFirebase();

        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NombreTarea = edtNombreTarea.getText().toString();
                String FechaTarea = edtFechaTarea.getText().toString();
                String HoraTarea = edtHoraTarea.getText().toString();
                String DescripcionTarea = edtDescripcionTarea.getText().toString();

                if (NombreTarea.equals("") || FechaTarea.equals("") || HoraTarea.equals("") || DescripcionTarea.equals("")){
                    validacion();
                }else {
                    Tarea t = new Tarea();
                    t.setUid(UUID.randomUUID().toString());
                    t.setNombre(NombreTarea);
                    t.setFecha(FechaTarea);
                    t.setHora(HoraTarea);
                    t.setDescripcion(DescripcionTarea);
                    databaseReference.child("Tarea").child(t.getUid()).setValue(t);
                    Toast.makeText(getContext(), "Se ha creado la tarea con exito", Toast.LENGTH_SHORT).show();
                    limpiarEditText();
                    cambiarFragment(new ListadoTareasFragment());

                }

            }
        });

        return v;

    }

    private void limpiarEditText() {
        edtNombreTarea.setText("");
        edtFechaTarea.setText("");
        edtHoraTarea.setText("");
        edtDescripcionTarea.setText("");
    }

    private void validacion() {
        String NombreTarea = edtNombreTarea.getText().toString();
        String FechaTarea = edtFechaTarea.getText().toString();
        String HoraTarea = edtHoraTarea.getText().toString();
        String DescripcionTarea = edtDescripcionTarea.getText().toString();

        if (NombreTarea.equals("")){
            edtNombreTarea.setError("Required");
        }else if (FechaTarea.equals("")) {
            edtFechaTarea.setError("Required");
        }else if (HoraTarea.equals("")){
            edtHoraTarea.setError("Required");
        }else if (DescripcionTarea.equals("")) {
            edtDescripcionTarea.setError("Required");
        }
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