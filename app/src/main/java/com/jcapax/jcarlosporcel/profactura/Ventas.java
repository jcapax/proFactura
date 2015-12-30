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

    String _imei;

    String _compa = null;

    String _prod = null;

    String res = null;

    String idVenta;

    String importeVenta;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

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




        final ArrayList<String> arrayList = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

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
//****************************************************************

                    if (!aux) {
                        arrayList.add(_productoSeleccionado + " - " + editTextCantidad.getText().toString());

                    } else {
                        Toast.makeText(getApplicationContext(), "El producto ya ha sido seleccionado", Toast.LENGTH_SHORT).show();
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

               /*
                contadorProductos = contadorProductos - 1;


                if (contadorProductos == 0) {
                    btnGetProducts.setVisibility(View.INVISIBLE);
                }

                */

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

                DialogoEnProgreso dialogoEnProgreso = new DialogoEnProgreso();
                        dialogoEnProgreso.execute();

/*
                        boolean aux1 = true;

                        dialog = new ProgressDialog(Ventas.this);
                        dialog.setMessage("Favor Esperar");
                        dialog.setTitle("Registrando Venta");
                        dialog.setIndeterminate(aux1);
                        dialog.show();
*/
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
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast  t = Toast.makeText(getApplicationContext(), "Venta Suspendida", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
                dialogo1.show();

                /*
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Confirmar")
                        .setMessage("Esta seguro de enviar los datos para la venta??")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getApplicationContext(), "posi",Toast.LENGTH_LONG).show();

                            }

                        }
                        )
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(getApplicationContext(), "nega",Toast.LENGTH_LONG).show();

                                    }

                                }
                        )
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
*/


//****************************************************************************************
                //****************************************************************************************
                // ****************************************************************************************








            }
        });





    }

    class DialogoEnProgreso extends AsyncTask<String, Void, Void>{

        ProgressDialog dialog = new ProgressDialog(Ventas.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            boolean aux1 = true;


            dialog.setMessage("Favor Esperar");
            dialog.setTitle("Registrando Venta");
            dialog.setIndeterminate(aux1);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //dialog.dismiss();
        }
    }

}


