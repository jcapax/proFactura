package com.jcapax.jcarlosporcel.profactura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;




public class MainRegistro extends AppCompatActivity {

    EditText editText;
    Button button;
    Button bCerrar;
    TextView textView;

    Connection printerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);

        editText = (EditText) findViewById(R.id.editText);
        button   = (Button) findViewById(R.id.button);
        bCerrar = (Button) findViewById(R.id.bVentas);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());

                String macAddress = "AC:3F:A4:53:CB:99";

                printerConnection = new BluetoothConnection(macAddress);

                try{
                    printerConnection.open();

                    ZebraPrinter printer = null;
                    if (printerConnection.isConnected()) {
                        printer = ZebraPrinterFactory.getInstance(printerConnection);

                        imprimirCabecera(printerConnection);
                        imprimirCuerpo(printerConnection);

                        if(printer != null){
                            PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

                        }
                        printerConnection.close();
                    }
                }
                catch (ConnectionException e) {
                    e.printStackTrace();
                } catch (ZebraPrinterLanguageUnknownException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void imprimirCabecera(Connection printerConnection) throws ConnectionException {

        String cabecera =
                "^XA" +

                        "^POI^PW700^MNN^LL325^LH0,0" + "\r\n" +

                        "^FO0,50" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD SOCIEDAD INDUSTRIAL DEL SUR S.A.^FS" + "\r\n" +

                        "^FO0,80" + "\r\n" + "^A0,N,20,20" + "\r\n" + "^FD CASA MATRIZ 0 ^FS" + "\r\n" +

                        "^FO0,100" + "\r\n" + "^A0,N,20,20" + "\r\n" + "^FD DIRECCION: CALLE MAURO NUÑEZ N 16 ^FS" + "\r\n" +

/*                "^FO225,180" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDAcme Industries^FS" + "\r\n" +

                "^FO50,220" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDDelivery Date:^FS" + "\r\n" +

                "^FO225,220" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD%s^FS" + "\r\n" +

                "^FO50,273" + "\r\n" + "^A0,N,30,30" + "\r\n" + "^FDItem^FS" + "\r\n" +

                "^FO280,273" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDPrice^FS" + "\r\n" +
*/
                "^FO0,120" + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n"+ "^XZ";

        printerConnection.write(cabecera.getBytes());
    }

    public void imprimirCuerpo(Connection printerConnection) throws ConnectionException {

        HttpHandler http = new HttpHandler();

        String text = editText.getText().toString();

        String res = http.getInformacionBasica("basica.php", text);

        String cuerpo =
                "^XA" +

                        //"^POI^PW700^MNN^LL325^LH0,0" + "\r\n" +

                        "^FO0,30" + "\r\n" + "^A0,N,20,20" + "\r\n" + "^FD "+ res +" ^FS" + "\r\n" +

                        "^FO0,50" + "\r\n" + "^GB550,2,2,B,0^FS" + "\r\n"+ "^XZ";

        printerConnection.write(cuerpo.getBytes());

    }

}
