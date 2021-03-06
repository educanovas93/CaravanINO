package com.caravaino.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.caravaino.controller.Caravaino;

import java.io.IOException;
import java.util.UUID;


public class MainMenuActivity extends AppCompatActivity {

   // Button btnOn, btnOff, btnDis;
    Button On, Off, Discnt, Abt;
    TextView lucesText,tempText,levelText;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    Caravaino controlador = Caravaino.getUnicaInstancia();
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        //address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        address = Caravaino.getUnicaInstancia().getAddress();
        //view of the ledControl
        setContentView(R.layout.activity_main_menu);

        //call the widgets
        lucesText = (TextView)findViewById(R.id.luces);
        tempText = (TextView)findViewById(R.id.temperatura);
        levelText = (TextView) findViewById(R.id.nivelacion);
        new ConnectBT().execute(); //Call the class to connect

        lucesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLuces();
            }
        });

        tempText.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTemperaturas();
            }
        }));

        levelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNivelacion();
            }
        });

        //commands to be sent to bluetooth


    }

    private void Disconnect()
    {
        if (controlador.getBtSocket() != null) //If the btSocket is busy
        {
            try
            {
                controlador.getBtSocket().close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void turnOffLed()
    {
        if (controlador.getBtSocket() !=null)
        {
            try
            {
                controlador.getBtSocket().getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (controlador.getBtSocket() != null)
        {
            try
            {
                controlador.getBtSocket().getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    public  void about(View v)
    {
        if(v.getId() == R.id.abt_btn)
        {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
    }
    public  void goToLuces()
    {
            Intent i = new Intent(this, LucesActivity.class);
            startActivity(i);
    }
    public  void goToTemperaturas()
    {
        Intent i = new Intent(this, TemperaturaActivity.class);
        startActivity(i);
    }
    public void goToNivelacion(){
        Toast.makeText(getApplicationContext(),"Nivelaci??n en construcci??n",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainMenuActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (controlador.getBtSocket() == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                 controlador.setBtSocket(dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID));//create a RFCOMM (SPP) connection
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 controlador.getBtSocket().connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
