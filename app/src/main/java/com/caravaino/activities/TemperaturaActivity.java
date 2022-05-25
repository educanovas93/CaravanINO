package com.caravaino.activities;

import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.caravaino.activities.luzlib.LuzAdapter;
import com.caravaino.activities.templib.TemperaturaAdapter;
import com.caravaino.activities.templib.TemperaturaAdapterMap;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Luz;
import com.caravaino.model.Temperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class TemperaturaActivity extends AppCompatActivity {
    private ListView temperaturaRecycler;
    private LinkedList<Temperatura> temperaturasList= new LinkedList<>();
    List<Temperatura> tempList;

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
        Temperatura temp = new Temperatura(id,"prueba",valor/10.0);
        System.out.println("Temperatura id"+temp.getId()+" Tempvalor"+temp.getValor());
        return temp;
    }

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
        this.mapaTemperaturas.put(tempPasillo.getId(),tempPasillo);
        this.mapaTemperaturas.put(tempTrasera.getId(),tempTrasera);
        this.mapaTemperaturas.put(tempDelantera.getId(),tempDelantera);
        this.mapaTemperaturas.put(tempOtra.getId(),tempOtra);



        temperaturaRecycler.setVisibility(View.VISIBLE);
        temperaturaAdapter = new TemperaturaAdapterMap(this, mapaTemperaturas);
        //tempList = new LinkedList<>(mapaTemperaturas.values());
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
                                    //System.out.println(str);
                                    if(str.charAt(0) == new Character('T')){
                                        //temperaturasList.add(tratarTemperatura(str));
                                        Temperatura t = tratarTemperatura(str);
                                        mapaTemperaturas.put(t.getId(),t);
                                        temperaturaAdapter.notifyDataSetChanged();
                                        if(mapaTemperaturas.containsKey(t.getId())){
                                            mapaTemperaturas.get(t.getId()).setValor(t.getValor());
                                        }else{
                                            mapaTemperaturas.put(t.getId(),t);
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
