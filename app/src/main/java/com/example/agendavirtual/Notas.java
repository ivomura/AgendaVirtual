package com.example.agendavirtual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.agendavirtual.models.recordatoriosModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Notas extends Fragment {

    EditText txtRecordatorio,txtDescripcion;
    Button btnAgregarAprendizaje;
    ListView list_recordatorio;
    Button btn_editar;
    Button btn_borrar;
    private final List<recordatoriosModel> listaRecodatorio = new ArrayList<recordatoriosModel>();
    ArrayAdapter<recordatoriosModel> arrayAdapterRecordatorios;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    recordatoriosModel notaSeleccionada;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_notas, container, false);
        txtRecordatorio = v.findViewById(R.id.txtRecordatorio);
        txtDescripcion = v.findViewById(R.id.txtDescripcion);
        btnAgregarAprendizaje = v.findViewById(R.id.btnAgregarAprendizaje);
        list_recordatorio = v.findViewById(R.id.list_recordatorio);
        btn_editar = v.findViewById(R.id.btn_editar);
        btn_borrar = v.findViewById(R.id.btn_borrar);

        inicializarFirebase();
        Lista();

        list_recordatorio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notaSeleccionada = (recordatoriosModel) parent.getItemAtPosition(position);
                txtRecordatorio.setText(notaSeleccionada.getNombre());
                txtDescripcion.setText(notaSeleccionada.getDescripcion());




            }


        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordatoriosModel r = new recordatoriosModel();
                r.setUid(notaSeleccionada.getUid());
                r.setNombre(txtRecordatorio.getText().toString().trim());
                r.setDescripcion(txtDescripcion.getText().toString().trim());
                databaseReference.child("Nota").child(r.getUid()).setValue(r);
                Toast.makeText(getContext(),"Actualizado",Toast.LENGTH_LONG).show();
                vaciarCasilla();
            }
        });

        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordatoriosModel n = new recordatoriosModel();
                n.setUid(notaSeleccionada.getUid());
                n.setNombre(txtRecordatorio.getText().toString().trim());
                n.setDescripcion(txtDescripcion.getText().toString().trim());
                databaseReference.child("Nota").child(n.getUid()).removeValue();
                Toast.makeText(getContext(),"Eliminado",Toast.LENGTH_LONG).show();
                vaciarCasilla();
            }
        });


        btnAgregarAprendizaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recordatorio = txtRecordatorio.getText().toString().trim();
                String descripcion = txtDescripcion.getText().toString().trim();

                if (recordatorio.isEmpty()&& descripcion.isEmpty()){

                }else{
                    recordatoriosModel p = new recordatoriosModel();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(recordatorio);
                    p.setDescripcion(descripcion);
                    databaseReference.child("Nota").child(p.getUid()).setValue(p);
                    Toast.makeText(getContext(),"Se a creado nota con exito", Toast.LENGTH_LONG).show();
                    vaciarCasilla();

                }




            }

        });




        return v;
    }
    private void Lista(){
            databaseReference.child("Nota").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaRecodatorio.clear();

                for (DataSnapshot objSnapshot : snapshot.getChildren()){
                    recordatoriosModel p = objSnapshot.getValue(recordatoriosModel.class);
                    listaRecodatorio.add(p);


                    arrayAdapterRecordatorios = new ArrayAdapter<recordatoriosModel>(getContext(), android.R.layout.simple_list_item_1, listaRecodatorio);
                    list_recordatorio.setAdapter(arrayAdapterRecordatorios);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void vaciarCasilla() {
        txtRecordatorio.setText("");
        txtDescripcion.setText("");
    }



    private void inicializarFirebase() {
        FirebaseApp.getApps(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    ;

}
