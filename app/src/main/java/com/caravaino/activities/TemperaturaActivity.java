package com.caravaino.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.caravaino.activities.luzlib.LuzAdapter;
import com.caravaino.activities.templib.TemperaturaAdapter;
import com.caravaino.model.Luz;
import com.caravaino.model.Temperatura;

import java.util.LinkedList;

public class TemperaturaActivity extends AppCompatActivity {
    private ListView temperaturaRecycler;
    private LinkedList<Temperatura> temperaturasList= new LinkedList<>();
    private TemperaturaAdapter temperaturaAdapter;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperaturas);
        temperaturaRecycler = (ListView)findViewById(R.id.temperaturaList);


        //Cargamos luces de prueba
        Temperatura tempPasillo = new Temperatura(0,"Pasillo",500);
        Temperatura tempTrasera = new Temperatura(1,"Trasera",100);
        Temperatura tempDelantera= new Temperatura(2,"Delantera",320);
        Temperatura tempOtra = new Temperatura(3,"Otra",250);

        this.temperaturasList.add(tempPasillo);
        this.temperaturasList.add(tempTrasera);
        this.temperaturasList.add(tempDelantera);
        this.temperaturasList.add(tempOtra);

        temperaturaRecycler.setVisibility(View.VISIBLE);
        temperaturaAdapter = new TemperaturaAdapter(this, temperaturasList);
        temperaturaRecycler.setAdapter(temperaturaAdapter);
    }
}
