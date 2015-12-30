package com.jcapax.jcarlosporcel.profactura;

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

    private static final String LOGTAG = "LogsAndroid";

    String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);

        editTextPatron = (EditText) findViewById(R.id.editTextPatron);
        bAutenticar = (ImageButton) findViewById(R.id.bAutenticar);

        final TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);



        bAutenticar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String patron;
                String respuesta, aux;

                patron = editTextPatron.getText().toString();

                imei = telephonyManager.getDeviceId();

                HttpHandler handler = new HttpHandler();
                respuesta = handler.autenticar("autenticar.php", patron, imei);

                aux = patron+imei;

                if(aux.trim().equals(respuesta.trim())){
                    Intent intent = new Intent(getApplicationContext(), Ventas.class);
                    startActivity(intent);
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(), "Error de Patrón", Toast.LENGTH_SHORT);
                    t.show();
                }





            }
        });
    }
}
