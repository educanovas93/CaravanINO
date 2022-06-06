package com.caravaino.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.caravaino.activities.luzlib.LuzAdapter;
import com.caravaino.activities.luzlib.LuzAdapterMap;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Luz;
import com.caravaino.model.Temperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class LucesActivity extends AppCompatActivity {
    private ListView luzRecycler;
    private Map<Integer,Luz> mapaLuces = new HashMap<>();
    private LuzAdapterMap luzAdapter;
    Context context = this;
    Caravaino controlador = Caravaino.getUnicaInstancia();
    Timer timer;

    private Luz tratarLuz(String luz){
        //System.out.println(temperatura);
        String str = luz.replace("L","");
        //System.out.println(str);
        StringTokenizer tokens=new StringTokenizer(str," ");
        int id = Integer.parseInt(tokens.nextToken());
        //boolean isTurned = Boolean.parseBoolean(tokens.nextToken());
        int turned = Integer.parseInt(tokens.nextToken());
        boolean isTurned;
        if (turned == 1){
            isTurned = true;
        }else{
            isTurned = false;
        }
        int intensidad = Integer.parseInt(tokens.nextToken());
        Luz l = new Luz(id,isTurned,intensidad);
        l.setNombre("NuevaLuz"+id);
        System.out.println("Luz id"+l.getId()+" luzisturned "+l.isTurned()+ " luzintensidad "+l.getIntensidad());
        return l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luces);
        luzRecycler = (ListView)findViewById(R.id.lucesList);


        //Cargamos luces de prueba
//        Luz luzPasillo = new Luz(0,true,100);
//        luzPasillo.setNombre("Pasillo");
//        Luz luzDelantera = new Luz(1,false,0);
//        luzDelantera.setNombre("Delantera");
//        Luz luzTrasera = new Luz(2,false,100);
//        luzTrasera.setNombre("Trasera");
//        Luz luzOtra = new Luz(3,true,100);
//        luzOtra.setNombre("Otra");
//        this.lucesList.add(luzPasillo);
//        this.lucesList.add(luzDelantera);
//        this.lucesList.add(luzTrasera);
//        this.lucesList.add(luzOtra);


        luzRecycler.setVisibility(View.VISIBLE);
        luzAdapter = new LuzAdapterMap(this, mapaLuces);
        luzRecycler.setAdapter(luzAdapter);

        final Handler handler = new Handler();
        timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(controlador.getBtSocket().getInputStream()));
                            while(reader.ready()) {
                                String line = reader.readLine();
                                System.out.println(line);
                                //System.out.println(line);
                                StringTokenizer tokens=new StringTokenizer(line,"#");
                                while(tokens.hasMoreTokens()){
                                    String str = tokens.nextToken();
                                    System.out.println(str);
                                    if(str.charAt(0) == new Character('L')){
                                        //temperaturasList.add(tratarTemperatura(str));
                                        Luz l = tratarLuz(str);
                                        mapaLuces.put(l.getId(),l);
                                        luzAdapter.notifyDataSetChanged();
                                        if(mapaLuces.containsKey(l.getId())){
                                            mapaLuces.get(l.getId()).update(l.isTurned(),l.getIntensidad());

                                        }else{
                                            mapaLuces.put(l.getId(),l);
                                        }
                                    }
                                }





                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(task,1000,1000);
    }
}
