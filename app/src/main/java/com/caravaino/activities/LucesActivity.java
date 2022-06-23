package com.caravaino.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.caravaino.activities.luzlib.LuzAdapterMap;
import com.caravaino.controller.Caravaino;
import com.caravaino.model.Luz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
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
    private static boolean run = true;
    Timer timer;

    private Luz tratarLuz(String luz){
        //System.out.println(temperatura);
        try {
            String str = luz.replace("L","");
            System.err.println("luz token : "+luz);
            //System.out.println(str);
            StringTokenizer tokens=new StringTokenizer(str," ");
            int id = Integer.parseInt(tokens.nextToken());
            //boolean isTurned = Boolean.parseBoolean(tokens.nextToken());
            String token = tokens.nextToken();
            System.err.println("TOKENNNN : "+token);
            int turned = Integer.parseInt(token);
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
        }catch (Exception e){
            System.err.println("la trama para parsear la luz no ha llegado correctamente -> trama : " + luz);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        run = false;
        finish();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luces);
        luzRecycler = (ListView)findViewById(R.id.lucesList);

        run = true;

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
                        if(run) {
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
                                            Luz l = tratarLuz(str);
                                            if(l != null && !luzAdapter.modifying) {
                                                //mapaLuces.put(l.getId(), l);
                                                //luzAdapter.notifyDataSetChanged();
                                                if (mapaLuces.containsKey(l.getId())) {
                                                    mapaLuces.get(l.getId()).update(l.isTurned(), l.getIntensidad());
                                                    luzAdapter.notifyDataSetChanged();
                                                } else {
                                                    mapaLuces.put(l.getId(), l);
                                                    luzAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                });
            }
        };
        timer.schedule(task,1000,1000);

    }
}
