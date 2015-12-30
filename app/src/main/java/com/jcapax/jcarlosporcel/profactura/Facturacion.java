package com.jcapax.jcarlosporcel.profactura;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jcapax.jcarlosporcel.profactura.listas.DetalleProductos;
import com.jcapax.jcarlosporcel.profactura.listas.Factura;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Facturacion extends AppCompatActivity {

    String idVenta;
    String importeVenta;

    EditText editTextIdVenta;
    TextView textViewImporteVenta;
    EditText editTextNit;
    static EditText editTextRazonSocail;
    ImageButton bFacturar;
    ImageButton bBuscar;

    ListView listViewProductos;

    //*********************************************
//*********************************************
    String id;
    int idSucursal;
    String especificacion;
    String correlativoSucursal;
    String fechaFactura;
    String nroFactura;
    String nroAutorizacion;
    String estado;
    String nit;
    String razonSocial;
    String importeTotal;
    String importeIce;
    String importeExportaciones;
    String importeVentasTasaCero;
    String importeSubtotal;
    String importeRebajas;
    String importeBaseDebitoFiscal;
    String debitoFiscal;
    String codigoControl;
    String fechaLimiteEmision;
    String idDosificacion;
    String literal;

    String detalleProductosRegistrados;
    String detalle;

    static String razonSocialEncontrado;


//******************************************
//******************************************

    Connection printerConnection;

    static ProgressDialog dialog;

    private static final String LOGTAG = "LogsAndroid";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion);

        Bundle extras = getIntent().getExtras();

        idVenta = extras.getString("idVenta");
        importeVenta = extras.getString("importeVenta");
        String cadena = extras.getString("jsonArray");

        //final ProgressDialog dialog = new ProgressDialog(Facturacion.this);

        //Log.e(LOGTAG, cadena);

        try {

            listViewProductos = (ListView) findViewById(R.id.listViewProductos);

            ArrayList<String> arrayList = new ArrayList();

            ArrayAdapter adapter;

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

            listViewProductos.setAdapter(adapter);


            JSONArray jsonArray = new JSONArray(cadena);
            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                arrayList.add(jsonObject.getString("nombreProducto").toString()+ " -" +
                            jsonObject.getString("cantidad").toString());
            }

            adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }


        editTextIdVenta = (EditText) findViewById(R.id.editTextIdVenta);
        textViewImporteVenta = (TextView) findViewById(R.id.textViewImporteVenta);
        editTextNit = (EditText) findViewById(R.id.editTextNit);
        editTextRazonSocail = (EditText) findViewById(R.id.editTextRazonSocial);


        editTextIdVenta.setText(idVenta);
        textViewImporteVenta.setText("IMPORTE TOTAL VENTA: "+importeVenta+" Bs.");

        bBuscar = (ImageButton) findViewById(R.id.bBuscar);

        dialog = new ProgressDialog(Facturacion.this);

        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.setMessage("Favor Esperar");
                dialog.setTitle("Verificando");
                dialog.setIndeterminate(true);
                dialog.show();

                Hilo1 hilo1 = new Hilo1();
                hilo1.start();

                /*

                String razonSocial;
                Editable _nit;

                _nit = editTextNit.getText();

                HttpHandler httpHandler = new HttpHandler();

                razonSocial = httpHandler.respuestaRazonSocial("respuestaRazonSocial.php", _nit.toString());

                editTextRazonSocail.setText(razonSocial);

                dialog.dismiss();

                */

            }
        });


        bFacturar = (ImageButton) findViewById(R.id.bFacturar);

        bFacturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Facturacion.this);
                dialogo1.setTitle("Atención");
                dialogo1.setMessage("¿Los datos registrados serán procesados, desea continuar?");
                dialogo1.setIcon(R.drawable.alerta);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                bFacturar.setVisibility(View.INVISIBLE);

                dialog.setMessage("Favor Esperar");
                dialog.setTitle("Verificando");
                dialog.setIndeterminate(true);
                dialog.show();

                String idVenta_;
                String razonSocial;
                String nit;

                idVenta_ = editTextIdVenta.getText().toString();
                razonSocial = editTextRazonSocail.getText().toString();
                nit = editTextNit.getText().toString();

                HttpHandler httpHandler = new HttpHandler();

                httpHandler.registarFactura("registroFactura.php",
                        idVenta_,
                        nit,
                        razonSocial);

                try {
                    leerFactura(idVenta_);

                    imprimir();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), Autenticacion.class);
                startActivity(intent);
                finish();

                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast t = Toast.makeText(getApplicationContext(), "Facturación Suspendida", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
                dialogo1.show();
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    private void prepararCuerpo() {

        HttpHandler httpHandler = new HttpHandler();

        InputStream is = null;

        String resultado = "fallo";
        DetalleProductos dp;

        String detalleCabe;

        String url = null;
        url = httpHandler._global;

        String param = "rest/jsonDetalleVenta.php?idVenta=" + editTextIdVenta.getText().toString().trim();

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url + param);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity contenido = response.getEntity();
            is = contenido.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        BufferedReader buferLector = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String linea = null;
        try {
            while ((linea = buferLector.readLine()) != null) {
                sb.append(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultado = sb.toString();


//          CONSTRUIMOS LA IMPRESION DE LOS PRODUCTOS REGISTRADOS

        try {
            JSONArray arrayJson = new JSONArray(resultado);
            int posicion = 0;
            String pos1, pos2;
            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject objetoJson = arrayJson.getJSONObject(i);
                dp = new DetalleProductos(objetoJson.getString("nombreProducto"),
                        objetoJson.getString("cantidad"),
                        objetoJson.getString("costoUnitario"),
                        objetoJson.getString("precioTotal"),
                        objetoJson.getString("iceTotal"),
                        objetoJson.getString("alicuota"));

                //  listaProductos.add(dp);

                pos1 = Integer.toString(posicion);
                pos2 = Integer.toString(posicion + 40);

                detalle = detalle + "^FO15," + pos1 + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD" +
                        objetoJson.getString("nombreProducto").toString() + "^FS" + "\r\n";

                detalle = detalle + "^FO160," + pos2 + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FD" +
                        objetoJson.getString("cantidad").toString() + "^FS" + "\r\n";

                detalle = detalle + "^FO260," + pos2 + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FD" +
                        objetoJson.getString("costoUnitario").toString() + "^FS" + "\r\n";

                detalle = detalle + "^FO350," + pos2 + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FD" +
                        objetoJson.getString("iceTotal").toString() + "^FS" + "\r\n";

                detalle = detalle + "^FO420," + pos2 + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FD" +
                        objetoJson.getString("alicuota").toString() + "^FS" + "\r\n";

                detalle = detalle + "^FO540," + pos2 + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FD" +
                        objetoJson.getString("precioTotal").toString() + "^FS" + "\r\n";

                posicion = posicion + 80;

                //Log.e(LOGTAG, objetoJson.getString("nombreProducto").toString() );
            }

            pos2 = Integer.toString(posicion + 10);

            detalle = detalle + "^FO0," + pos2 + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n" + "^XZ";

            detalleCabe = "^XA" + "^POI^PW750^MNN^LL" + pos2 + "^LH0,0" + "\r\n";

            detalle = detalleCabe + detalle;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    private void imprimir() {
        String macAddress = "AC:3F:A4:53:CB:99";

        printerConnection = new BluetoothConnection(macAddress);

        try {
            printerConnection.open();

            ZebraPrinter printer = null;
            if (printerConnection.isConnected()) {
                printer = ZebraPrinterFactory.getInstance(printerConnection);


                printCabecera(printerConnection);
                printCuerpo(printerConnection);
                printPie(printerConnection);

                if (printer != null) {
                    PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

                }
                printerConnection.close();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (ZebraPrinterLanguageUnknownException e) {
            e.printStackTrace();
        }


    }

    private void printPie(Connection printerConnection) throws ConnectionException {

        String nitSIDS = " 1016257022";

        String codigoQR = nitSIDS + '|' +
                nroFactura + '|' +
                nroAutorizacion + '|' +
                fechaFactura + '|' +
                importeTotal + '|' +
                importeBaseDebitoFiscal + '|' +
                codigoControl + '|' +
                nit + '|' +
                importeIce + '|' +
                importeVentasTasaCero + '|' +
                "0.00" + '|' +
                importeRebajas;


        String cabecera =
                "^XA" +

                        "^POI^PW750^MNN^LL720^LH0,0" + "\r\n" +

                        "^FO0,15" + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n" +

                        "^FO0,50" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD IMP. TOTAL: ^FS" + "\r\n" +

                        "^FO530,50" + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FDBs. " + importeTotal + "^FS" + "\r\n" +

                        "^FO0,85" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD IMP. BASE PARA CREDITO FISCAL: ^FS" + "\r\n" +

                        "^FO530,85" + "\r\n" + ",1^A0,N,25,25" + "\r\n" + "^FDBs. " + importeBaseDebitoFiscal + "^FS" + "\r\n" +

                        "^FO0,110" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD SON: " + literal + "/100 Bolivianos.^FS" + "\r\n" +

                        "^FO0,145" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD CODIGO DE CONTROL: ^FS" + "\r\n" +

                        "^FO280,145" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD" + codigoControl + "^FS" + "\r\n" +

                        "^FO0,180" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD FECHA LIMITE EMISION: ^FS" + "\r\n" +

                        "^FO280,180" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD" + fechaLimiteEmision + "^FS" + "\r\n" +

                        "\"^FO190,210^BQN,2,5^FD " + codigoQR + " ^FS\r\n" +

                        "^FO10,440" + "\r\n" + "^A0,N,23,23" + "\r\n" + "^FD ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS^FS" + "\r\n" +
                        "^FO80,470" + "\r\n" + "^A0,N,23,23" + "\r\n" + "^FD EL USO ILICITO DE ESTA SERA SANCIONADO^FS" + "\r\n" +
                        "^FO180,500" + "\r\n" + "^A0,N,23,23" + "\r\n" + "^FD DE ACUERDO A LEY^FS" + "\r\n" +

                        "^FO180,550" + "\r\n" + "^A0,N,23,23" + "\r\n" + "^FD SEGUNDA LEYENDA^FS" + "\r\n" +
                        "^FO180,590" + "\r\n" + "^A0,N,23,23" + "\r\n" + "^FD SEGUNDA LEYENDA^FS" + "\r\n" +
                        "^FO180,630" + "\r\n" + "^A0,N,23,23" + "\r\n" + "^FD SEGUNDA LEYENDA^FS" + "\r\n" +

                        "^FO0,650" + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n" + "^XZ";



        printerConnection.write(cabecera.getBytes());
    }

    private void printCuerpo(Connection printerConnection) throws ConnectionException {

        prepararCuerpo();
        printerConnection.write(detalle.getBytes());
    }

    private void printCabecera(Connection printerConnection) throws ConnectionException {
        String cabecera =
                "^XA" +

                        "^POI^PW750^MNN^LL710^LH0,0" + "\r\n" +

                        "^FO20,50" + "\r\n" + "^A0,N,35,35" + "\r\n" + "^FD SOCIEDAD INDUSTRIAL DEL SUR S.A.^FS" + "\r\n" +

                        "^FO200,90" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD CASA MATRIZ 0 ^FS" + "\r\n" +

                        "^FO100,130" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD DIRECCION: CALLE MAURO NUNEZ N 16 ^FS" + "\r\n" +

                        "^FO190,170" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD TELF. 64-41112^FS" + "\r\n" +

                        "^FO190,210" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD SUCRE - BOLIVIA ^FS" + "\r\n" +

                        "^FO0,270" + "\r\n" + "^A0,N,30,30" + "\r\n" + "^FD NIT: ^FS" + "\r\n" +

                        "^FO200,270" + "\r\n" + "^A0,N,30,30" + "\r\n" + "^FD 1016207022 ^FS" + "\r\n" +

                        "^FO0,310" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD NRO. FACTURA: ^FS" + "\r\n" +

                        "^FO200,310" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD " + nroFactura + " ^FS" + "\r\n" +

                        "^FO0,350" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD NRO. AUTORIZ.: ^FS" + "\r\n" +

                        "^FO200,350" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD " + nroAutorizacion + " ^FS" + "\r\n" +

                        "^FO0,390" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD ACT. ECONOMICA: ELAB. DE BEBIDAS DE MALTA ^FS" + "\r\n" +

                        "^FO210,450" + "\r\n" + "^A0,N,35,35" + "\r\n" + "^FD ORIGINAL ^FS" + "\r\n" +

                        "^FO0,500" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD FECHA EMISION: ^FS" + "\r\n" +

                        "^FO180,500" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD" + fechaFactura + "^FS" + "\r\n" +

                        "^FO0,540" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD NIT / CI: ^FS" + "\r\n" +

                        "^FO180,540" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD " + nit + "^FS" + "\r\n" +

                        "^FO0,580" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD RAZON SOCIAL^FS" + "\r\n" +

                        "^FO180,580" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD " + razonSocial + "^FS" + "\r\n" +

                        "^FO0,625" + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n" +

                        "^FO0,650" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD DETALLE ^FS" + "\r\n" +

                        "^FO120,650" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD CANT. ^FS" + "\r\n" +

                        "^FO220,650" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD P/U. ^FS" + "\r\n" +

                        "^FO300,650" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD ICE ^FS" + "\r\n" +

                        "^FO360,650" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD ALIC. ^FS" + "\r\n" +

                        "^FO430,650" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD SUB TOTAL. ^FS" + "\r\n" +

                        "^FO0,685" + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n" + "^XZ";

        printerConnection.write(cabecera.getBytes());
    }


    private void leerFactura(String idVenta_) throws JSONException {

        String datosFactura;
        String[] factura;

        String anno, mes, dia;

        HttpHandler httpHandler = new HttpHandler();

        datosFactura = httpHandler.obtenerFactura("rest/factura.php", idVenta_.trim());

        factura = datosFactura.split("/");

        nroFactura = factura[0].trim();
        nroAutorizacion = factura[1];
        nit = factura[2];
        razonSocial = factura[3].trim();
        fechaFactura = factura[4].replace("-", "/");
        codigoControl = factura[5];
        fechaLimiteEmision = factura[6].replace("-", "/");
        importeTotal = factura[7];
        importeBaseDebitoFiscal = factura[8];
        importeIce = factura[9];
        importeVentasTasaCero = factura[10];
        importeRebajas = factura[11];
        literal = factura[12];

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Facturacion Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jcapax.jcarlosporcel.profactura/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Facturacion Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jcapax.jcarlosporcel.profactura/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class Hilo1 extends  Thread{
        public void run(){
            //String razonSocial;
            Editable _nit;

            _nit = editTextNit.getText();

            HttpHandler httpHandler = new HttpHandler();

            razonSocialEncontrado = httpHandler.respuestaRazonSocial("respuestaRazonSocial.php", _nit.toString());

            //editTextRazonSocail.setText(razonSocial);

            dialog.dismiss();

            Facturacion.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    editTextRazonSocail.setText(razonSocialEncontrado);

                }
            });

        }
    }

}
