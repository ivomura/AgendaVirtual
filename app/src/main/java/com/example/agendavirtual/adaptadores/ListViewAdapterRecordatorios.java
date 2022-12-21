package com.example.agendavirtual.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agendavirtual.R;
import com.example.agendavirtual.recordatorios;

import java.util.ArrayList;

public class ListViewAdapterRecordatorios extends BaseAdapter {

    Context context;
    ArrayList<recordatorios> recordatoriosData;
    LayoutInflater layoutInflater;
    recordatorios recordatoriosModel;

    public ListViewAdapterRecordatorios(Context context, ArrayList<recordatorios> recordatoriosData) {
        this.context = context;
        this.recordatoriosData = recordatoriosData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                context.LAYOUT_INFLATER_SERVICE
        );

    }

    @Override
    public int getCount() {
        return recordatoriosData.size();
    }

    @Override
    public Object getItem(int position) {
        return recordatoriosData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_aprendizaje,null,true);

        }
        TextView nombres = rowView.findViewById(R.id.nombres);
        TextView descripciones = rowView.findViewById(R.id.descripciones);

        recordatoriosModel = recordatoriosData.get(position);
        nombres.setText(recordatoriosModel.getNombrenota());
        nombres.setText(recordatoriosModel.getDescripcion());

        return rowView;
    }
}
