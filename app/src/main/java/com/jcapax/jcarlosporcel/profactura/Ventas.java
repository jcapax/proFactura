package com.jcapax.jcarlosporcel.profactura;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    Button bAdicionar;
    Button bVender;
    ListView listViewProductos;
    EditText editTextCantidad;

    String _imei;

    String _compa = null;

    String _prod = null;

    String res = null;

    String idVenta;

    String importeVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        spinnerProducto   = (Spinner) findViewById(R.id.spinnerProducto);
        bAdicionar        = (Button) findViewById(R.id.bAdicionar);
        bVender           = (Button) findViewById(R.id.bVender);
        listViewProductos = (ListView) findViewById(R.id.listViewProductos);
        editTextCantidad  = (EditText) findViewById(R.id.editTextCantidad);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        _imei = "123456";


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

        bVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                res = httpHandler.registarVenta("ventaTemp.php", _imei, jsonArray.toString());

                String[] aux = null;
                aux = res.split("-");
                idVenta      = aux[0];
                importeVenta = aux[1];

                Toast.makeText(getApplicationContext(), "Procesando Venta",Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), Facturacion.class);
                i.putExtra("idVenta",  idVenta.toString());
                i.putExtra("importeVenta", importeVenta.toString());
                startActivity(i);

            }
        });

        }
}


