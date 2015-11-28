package com.magneticsource.msource.ui;

import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.magneticsource.msource.control.Alumno;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.R;
import com.magneticsource.msource.control.Persona;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.widget.EditText;
import android.widget.Toast;


public class AlumnoActivity extends AppCompatActivity implements
        CreateNdefMessageCallback{

    private TextView txvAlumno;
    private Alumno alumno;

    EditText textOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        txvAlumno = (TextView) findViewById(R.id.ala_txvAlumno);

        Datos d =new Datos(getApplicationContext());
        alumno = Alumno.fromString(d.getString(Datos.USUARIO));

        txvAlumno.setText(alumno.getNombreCompleto());

        /*NfcAdapter adpater = NfcAdapter.getDefaultAdapter(this);
        adpater.setNdefPushMessageCallback(this,this);
        //adpater.setOnNdefPushCompleteCallback(this,this);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alumno, menu);
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

    /*@Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG).show();
            }
        });

    }*/

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        String text = ("Beam up" + "Beam time: " + System.currentTimeMillis());
        NdefMessage msg= new NdefMessage(
                new NdefRecord[] { NdefRecord.createMime(
                        "text/plain", text.getBytes()
                )}
        );
        return msg;

        /*String stringOut = textOut.getText().toString();
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[] {},
                bytesOut);

        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;*/
    }
}
