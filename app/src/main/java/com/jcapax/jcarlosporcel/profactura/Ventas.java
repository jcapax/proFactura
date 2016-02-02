package com.jcapax.jcarlosporcel.profactura;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Ventas extends AppCompatActivity {

    private int contadorProductos;

    Spinner spinnerProducto;

    String _productoSeleccionado;
    ImageButton bAdicionar;
    ImageButton bVender;
    ListView listViewProductos;
    EditText editTextCantidad;

    static String _imei;

    String _compa = null;

    String _prod = null;

    String res = null;

    String idVenta;

    String importeVenta;

    static ArrayList<String> arrayList = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdpater;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
/*
        Bundle extras = getIntent().getExtras();

        _imei = extras.getString("imei");

*/

        spinnerProducto   = (Spinner) findViewById(R.id.spinnerProducto);
        bAdicionar        = (ImageButton) findViewById(R.id.bAdicionar);
        bVender           = (ImageButton) findViewById(R.id.bVender);
        listViewProductos = (ListView) findViewById(R.id.listViewProductos);
        editTextCantidad  = (EditText) findViewById(R.id.editTextCantidad);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        _imei = telephonyManager.getDeviceId();

        final ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.producto, android.R.layout.simple_spinner_dropdown_item);


        spinnerProducto.setAdapter(adapter);

        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _productoSeleccionado = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), _productoSeleccionado,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




//        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);


        listViewProductos.setAdapter(arrayAdpater);

        bAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _compar = "PRODUCTO";

                if (!_compar.equals(_productoSeleccionado)) {

                    Boolean aux = false;

                    Iterator<String> i = arrayList.iterator();
                    while (i.hasNext()) {
                        String elemento = i.next();
                        String soloProducto[] = elemento.split("-");


//****************************************************************

                        _prod = soloProducto[0].trim();
                        _compa = _productoSeleccionado;

                        if (_prod.equals(_compa)) {
                            aux = true;
                        }
                    }


                    if(editTextCantidad.getText().toString().length() == 0){
                        aux = true;
                    }
//****************************************************************

                    if (!aux) {
                        arrayList.add(_productoSeleccionado + " - " + editTextCantidad.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "El Producto ya ha sido seleccionado o la Cantidad no es la Correcta", Toast.LENGTH_SHORT).show();
                    }

                    editTextCantidad.setText("");
                }

                arrayAdpater.notifyDataSetChanged();

                spinnerProducto.setAdapter(adapter);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(editTextCantidad.getWindowToken(), 0);

                contadorProductos = contadorProductos + 1;


            }
        });


        listViewProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int posicion, long arg3) {
                // TODO Auto-generated method stub

                arrayList.remove(posicion);
                arrayAdpater.notifyDataSetChanged();
                return false;

            }
        });

        bVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Ventas.this);
                dialogo1.setTitle("Atención");
                dialogo1.setMessage("¿Los datos registrados serán procesados, desea continuar?");
                dialogo1.setIcon(R.drawable.alerta);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        dialog = new ProgressDialog(Ventas.this);
                        dialog.setMessage("Favor Esperar");
                        dialog.setTitle("Registrando Venta");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();
//***************
/*
                        arrayAdpater.clear();
                        arrayAdpater.notifyDataSetChanged();
*/
//***************
                        Hilo1 hilo1 = new Hilo1();
                        hilo1.start();

                        //**********************************************************************************************************
                        //**********************************************************************************************************
                        //**********************************************************************************************************
/*
                        JSONArray jsonArray = new JSONArray();

                        JSONObject jsonMain = new JSONObject();


                        String[] detalle;

                        for(int i=0;i<arrayList.size();i++){
                            try {
                                JSONObject obj = new JSONObject();

                                detalle = arrayList.get(i).toString().split("-");

                                obj.put("nombreProducto", detalle[0].trim());
                                obj.put("cantidad",detalle[1].trim());


                                jsonArray.put(obj);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                        try {
                            jsonMain.put("productos",jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        HttpHandler httpHandler = new HttpHandler();
                        res = httpHandler.registarVenta("registroVenta.php", _imei, jsonArray.toString());

                        String[] aux = null;
                        aux = res.split("-");
                        idVenta      = aux[0];
                        importeVenta = aux[1];

                        Intent i = new Intent(getApplicationContext(), Facturacion.class);
                        i.putExtra("idVenta",  idVenta.toString());
                        i.putExtra("importeVenta", importeVenta.toString());
                        i.putExtra("jsonArray", jsonArray.toString());
                        startActivity(i);

                        arrayAdpater.clear();
                        arrayAdpater.notifyDataSetChanged();

                        finish();
*/

                        //**********************************************************************************************************
                        //**********************************************************************************************************
                        //**********************************************************************************************************

                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast  t = Toast.makeText(getApplicationContext(), "Venta Suspendida", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
                dialogo1.show();

            }
        });





    }

    private class Hilo1 extends  Thread{
        public void run(){


            JSONArray jsonArray = new JSONArray();

            //JSONObject jsonMain = new JSONObject();


            String[] detalle;

            for(int i=0;i<arrayList.size();i++){
                try {
                    JSONObject obj = new JSONObject();

                    detalle = arrayList.get(i).toString().split("-");

                    obj.put("nombreProducto", detalle[0].trim());
                    obj.put("cantidad",detalle[1].trim());


                    jsonArray.put(obj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            /*
            try {
                jsonMain.put("productos",jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */

            HttpHandler httpHandler = new HttpHandler();
            res = httpHandler.registarVenta("registroVenta.php", _imei, jsonArray.toString());

            String[] aux = null;
            aux = res.split("-");
            idVenta      = aux[0];
            importeVenta = aux[1];

            Intent i = new Intent(getApplicationContext(), Facturacion.class);
            i.putExtra("idVenta",  idVenta.toString());
            i.putExtra("importeVenta", importeVenta.toString());
            i.putExtra("jsonArray", jsonArray.toString());
            startActivity(i);

            //arrayAdpater.clear();
            //arrayAdpater.notifyDataSetChanged();

            finish();

            Ventas.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayAdpater.clear();
                    arrayAdpater.notifyDataSetChanged();

                }
            });

        }
    }





}


