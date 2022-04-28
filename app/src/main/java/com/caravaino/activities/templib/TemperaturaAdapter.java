package com.caravaino.activities.templib;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.caravaino.activities.R;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Luz;
import com.caravaino.model.Temperatura;

import org.w3c.dom.Text;

import java.util.List;

public class TemperaturaAdapter extends BaseAdapter {
    Activity context;
    List<Temperatura> temperaturaList;
    private static LayoutInflater inflater = null;
    Caravaino controller = Caravaino.getUnicaInstancia();
    public TemperaturaAdapter(Activity context, List<Temperatura> temperaturaList) {
        this.context = context;
        this.temperaturaList = temperaturaList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return temperaturaList.size();
    }

    @Override
    public Object getItem(int position) {
        return temperaturaList.get(position);
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

        Temperatura selectedTemperatura = temperaturaList.get(position);

        //HACER LISTENER DE CAMBIO DE switch
        labelTemperatura.setText(selectedTemperatura.getNombre());
        temperaturaValor.setText(Integer.toString(selectedTemperatura.getValor())+"º");
        //switch1.setChecked(selectedLuz.isTurned());
        //switch1.setText(selectedLuz.getNombre());
        return itemView;
    }
}