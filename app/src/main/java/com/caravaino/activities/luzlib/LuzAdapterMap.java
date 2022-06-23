package com.caravaino.activities.luzlib;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.caravaino.activities.R;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Luz;
import com.caravaino.model.Temperatura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LuzAdapterMap extends BaseAdapter {
    Activity context;
    Map<Integer, Luz> lucesMap;
    private static LayoutInflater inflater = null;
    public static boolean modifying = false;
    Caravaino controller = Caravaino.getUnicaInstancia();
    public LuzAdapterMap(Activity context, Map<Integer,Luz> lucesMap) {
        this.context = context;
        this.lucesMap = lucesMap;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return lucesMap.size();
    }

    @Override
    public Object getItem(int position) {
        List<Luz> l = new ArrayList(lucesMap.values());
        return l.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.luz_item, null) : itemView;
        Switch switch1 = (Switch) itemView.findViewById(R.id.switch1);
        SeekBar slider = (SeekBar) itemView.findViewById(R.id.slider);


        Luz selectedLuz = (Luz)getItem(position);

        //HACER LISTENER DE CAMBIO DE switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                modifying = true;
                selectedLuz.setTurned(isChecked);
                selectedLuz.setIntensidad(slider.getProgress());
                System.err.println("STATUS: "+selectedLuz.getStatus());
                controller.sendMessageBt(selectedLuz.getStatus());
                modifying = false;
            }

        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                System.err.println("Progress: "+seekBar.getProgress());
                selectedLuz.setIntensidad(seekBar.getProgress());
                System.err.println("STATUS: "+selectedLuz.getStatus());
                controller.sendMessageBt(selectedLuz.getStatus());
                //Toast.makeText(context.getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(context.getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(context.getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        System.err.println("STATUS LUZ : "+selectedLuz.getStatus());
        switch1.setChecked(selectedLuz.isTurned());
        switch1.setText(selectedLuz.getNombre());
        slider.setProgress(selectedLuz.getIntensidad());
        return itemView;
    }
}