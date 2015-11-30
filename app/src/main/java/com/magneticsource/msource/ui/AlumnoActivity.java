package com.magneticsource.msource.ui;

import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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

import java.nio.charset.Charset;
import java.util.Locale;


public class AlumnoActivity extends AppCompatActivity {
    private NfcAdapter mNfcAdapter;
    private TextView txvAlumno;
    private ImageView img;
    private Alumno alumno;
    private NdefMessage mNdefMessage;

    EditText textOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        txvAlumno = (TextView) findViewById(R.id.ala_txvAlumno);
        img =(ImageView) findViewById(R.id.ala_imvFoto);

        Datos d =new Datos(getApplicationContext());
        alumno = Alumno.fromString(d.getString(Datos.USUARIO));

        txvAlumno.setText(alumno.getNombreCompleto());

        if (alumno.getImageURL().length()>0) {
            ImageLoadTask load = new ImageLoadTask(alumno.getImageURL(),
                    img);
            load.execute();
        }

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        mNdefMessage = new NdefMessage(
                new NdefRecord[] {
                        createNewTextRecord(alumno.getDni()+Datos.SEPARADOR1+alumno.getNombreCompleto(), Locale.ENGLISH, true)});
    }

    public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char)(utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte)status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundNdefPush(this, mNdefMessage);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundNdefPush(this);
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
}
