package com.caravaino.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.caravaino.activities.luzlib.LuzAdapter;
import com.caravaino.model.Luz;

import java.util.LinkedList;

public class LucesActivity extends AppCompatActivity {
    private ListView luzRecycler;
    private LinkedList<Luz> lucesList= new LinkedList<>();
    private LuzAdapter luzAdapter;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luces);
        luzRecycler = (ListView)findViewById(R.id.lucesList);


        //Cargamos luces de prueba
        Luz luzPasillo = new Luz(0,true,100);
        luzPasillo.setNombre("Pasillo");
        Luz luzDelantera = new Luz(1,false,0);
        luzDelantera.setNombre("Delantera");
        Luz luzTrasera = new Luz(2,false,100);
        luzTrasera.setNombre("Trasera");
        Luz luzOtra = new Luz(3,true,100);
        luzOtra.setNombre("Otra");
        this.lucesList.add(luzPasillo);
        this.lucesList.add(luzDelantera);
        this.lucesList.add(luzTrasera);
        this.lucesList.add(luzOtra);


        luzRecycler.setVisibility(View.VISIBLE);
        luzAdapter = new LuzAdapter(this, lucesList);
        luzRecycler.setAdapter(luzAdapter);
    }
}
