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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.agendavirtual.models.Tarea;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ListadoTareasFragment extends Fragment {

    ListView lstTareas;
    private List<Tarea> listaTarea = new ArrayList<Tarea>();
    ArrayAdapter<Tarea> arrayAdapterTarea;
    Tarea TareaSeleccionada;
    String id, nombre, fecha, hora, descripcion;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listado_tareas, container, false);

        lstTareas = v.findViewById(R.id.lstTareas);

        inicializarFirebase();
        listarDatos();

        lstTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TareaSeleccionada = (Tarea) adapterView.getItemAtPosition(i);
                id = TareaSeleccionada.getUid().trim();
                nombre = TareaSeleccionada.getNombre().trim();
                fecha = TareaSeleccionada.getFecha().trim();
                hora = TareaSeleccionada.getHora().trim();
                descripcion = TareaSeleccionada.getDescripcion().trim();

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("nombre", nombre);
                bundle.putString("fecha", fecha);
                bundle.putString("hora", hora);
                bundle.putString("descripcion", descripcion);

                getParentFragmentManager().setFragmentResult("key", bundle);

                cambiarFragment(new EditarTareaFragment());

            }
        });

        return v;
    }

    private void listarDatos() {
        databaseReference.child("Tarea").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaTarea.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()){
                    Tarea t = objSnapshot.getValue(Tarea.class);
                    listaTarea.add(t);

                    if (getContext()!=null){
                        arrayAdapterTarea = new ArrayAdapter<Tarea>(getContext(), android.R.layout.simple_list_item_1, listaTarea);
                        lstTareas.setAdapter(arrayAdapterTarea);
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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