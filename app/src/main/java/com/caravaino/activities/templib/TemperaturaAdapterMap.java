package com.caravaino.activities.templib;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caravaino.activities.R;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Temperatura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemperaturaAdapterMap extends BaseAdapter {
    Activity context;
    Map<Integer,Temperatura> temperaturaMap;
    private static LayoutInflater inflater = null;
    Caravaino controller = Caravaino.getUnicaInstancia();
    public TemperaturaAdapterMap(Activity context, Map<Integer,Temperatura> temperaturaMap) {
        this.context = context;
        this.temperaturaMap = temperaturaMap;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return temperaturaMap.values().size();
    }

    @Override
    public Object getItem(int position) {
        List<Temperatura> l = new ArrayList(temperaturaMap.values());
        return l.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.temperatura_item, null) : itemView;

        TextView labelTemperatura = (TextView) itemView.findViewById(R.id.temperaturaLabel);
        TextView temperaturaValor = (TextView) itemView.findViewById(R.id.temperaturaValor);

        List<Temperatura> l = new ArrayList(temperaturaMap.values());
        Temperatura selectedTemperatura = l.get(position);

        //HACER LISTENER DE CAMBIO DE switch
        labelTemperatura.setText(selectedTemperatura.getNombre());
        temperaturaValor.setText(Double.toString(selectedTemperatura.getValor())+"º");
        //switch1.setChecked(selectedLuz.isTurned());
        //switch1.setText(selectedLuz.getNombre());
        return itemView;
    }
}