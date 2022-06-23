package com.caravaino.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.caravaino.activities.templib.TemperaturaAdapterMap;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Temperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class TemperaturaActivity extends AppCompatActivity {
    private ListView temperaturaRecycler;

    private Map<Integer,Temperatura> mapaTemperaturas = new HashMap();
    private TemperaturaAdapterMap temperaturaAdapter;
    Caravaino controlador = Caravaino.getUnicaInstancia();
    Context context = this;
    Timer timer;

    private Temperatura tratarTemperatura(String temperatura){
        //System.out.println(temperatura);
        String str = temperatura.replace("T","");
        //System.out.println(str);
        StringTokenizer tokens=new StringTokenizer(str," ");
        int id = Integer.parseInt(tokens.nextToken());
        double valor = Double.parseDouble(tokens.nextToken());
        Temperatura temp = new Temperatura(id,"NuevaTemp"+id,valor/10.0);
        System.out.println("Temperatura id"+temp.getId()+" Tempvalor"+temp.getValor());
        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperaturas);
        temperaturaRecycler = (ListView)findViewById(R.id.temperaturaList);


        //Cargamos luces de prueba
//        Temperatura tempPasillo = new Temperatura(0,"Pasillo",50);
//        Temperatura tempTrasera = new Temperatura(1,"Trasera",10);
//        Temperatura tempDelantera= new Temperatura(2,"Delantera",32);
//        Temperatura tempOtra = new Temperatura(3,"Otra",25);
//
//
//        this.mapaTemperaturas.put(tempPasillo.getId(),tempPasillo);
//        this.mapaTemperaturas.put(tempTrasera.getId(),tempTrasera);
//        this.mapaTemperaturas.put(tempDelantera.getId(),tempDelantera);
//        this.mapaTemperaturas.put(tempOtra.getId(),tempOtra);



        temperaturaRecycler.setVisibility(View.VISIBLE);
        temperaturaAdapter = new TemperaturaAdapterMap(this, mapaTemperaturas);
        temperaturaRecycler.setAdapter(temperaturaAdapter);


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
                                //System.out.println(line);
                                StringTokenizer tokens=new StringTokenizer(line,"#");
                                while(tokens.hasMoreTokens()){
                                    String str = tokens.nextToken();
                                    System.out.println(str);
                                    if(str.charAt(0) == new Character('T')){
                                        //temperaturasList.add(tratarTemperatura(str));
                                        Temperatura t = tratarTemperatura(str);
                                        //mapaTemperaturas.put(t.getId(),t);

                                        if(mapaTemperaturas.containsKey(t.getId())){
                                            mapaTemperaturas.get(t.getId()).setValor(t.getValor());
                                            temperaturaAdapter.notifyDataSetChanged();
                                        }else{
                                            mapaTemperaturas.put(t.getId(),t);
                                            temperaturaAdapter.notifyDataSetChanged();
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
