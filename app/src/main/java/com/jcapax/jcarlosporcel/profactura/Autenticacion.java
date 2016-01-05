package com.jcapax.jcarlosporcel.profactura;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Autenticacion extends AppCompatActivity {

    EditText editTextPatron;
    ImageButton bAutenticar;
    static String imei;

    private static final String LOGTAG = "LogsAndroid";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);

        editTextPatron = (EditText) findViewById(R.id.editTextPatron);
        bAutenticar = (ImageButton) findViewById(R.id.bAutenticar);


        final TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

       // imei = telephonyManager.getDeviceId();

        imei = telephonyManager.getDeviceId();



        bAutenticar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                ProgressDialog dialog = new ProgressDialog(Autenticacion.this);
                dialog.setMessage("Favor Esperar");
                dialog.setTitle("Autenticando Usuario");
                dialog.setIndeterminate(true);
                dialog.show();

                Hilo1 hilo1 = new Hilo1();
                hilo1.start();
/*
                String patron;
                String respuesta, aux;

                patron = editTextPatron.getText().toString();

                imei = telephonyManager.getDeviceId();

                HttpHandler handler = new HttpHandler();
                respuesta = handler.autenticar("autenticar.php", patron, imei);

                aux = patron+imei;

                if(aux.trim().equals(respuesta.trim())){
                    Intent intent = new Intent(getApplicationContext(), Ventas.class);
                    //intent.putExtra("imei", imei);
                    startActivity(intent);
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(), "Error de Patrón", Toast.LENGTH_SHORT);
                    t.show();
                }
*/




            }
        });


    }

    private class Hilo1 extends  Thread {
        public void run() {

            String patron;
            String respuesta, aux;

            patron = editTextPatron.getText().toString();

            //imei = telephonyManager.getDeviceId();

            HttpHandler handler = new HttpHandler();
            respuesta = handler.autenticar("autenticar.php", patron, imei);

            aux = patron+imei;

            if(aux.trim().equals(respuesta.trim())){
                Intent intent = new Intent(getApplicationContext(), Ventas.class);
                //intent.putExtra("imei", imei);
                startActivity(intent);
            }
            else{
                Toast t = Toast.makeText(getApplicationContext(), "Error de Patrón", Toast.LENGTH_SHORT);
                t.show();
            }

        }
    }
}
