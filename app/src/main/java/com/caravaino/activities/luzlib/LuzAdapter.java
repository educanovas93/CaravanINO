package com.caravaino.activities.luzlib;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.caravaino.activities.R;


import com.caravaino.model.Luz;

import java.util.List;

public class LuzAdapter extends BaseAdapter {
    Activity context;
    List<Luz> lucesList;
    private static LayoutInflater inflater = null;

    public LuzAdapter(Activity context, List<Luz> lucesList) {
        this.context = context;
        this.lucesList = lucesList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return lucesList.size();
    }

    @Override
    public Object getItem(int position) {
        return lucesList.get(position);
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

        Luz selectedLuz = lucesList.get(position);
        slider.setProgress(50);

        //HACER LISTENER DE CAMBIO DE switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

            }
        });


        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                System.err.println("Progress: "+seekBar.getProgress());
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


        switch1.setChecked(selectedLuz.isTurned());
        switch1.setText(selectedLuz.getNombre());
        return itemView;
    }
}