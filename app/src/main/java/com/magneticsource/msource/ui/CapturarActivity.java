package com.magneticsource.msource.ui;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.magneticsource.msource.R;
import com.magneticsource.msource.asistencia.Asistencia;
import com.magneticsource.msource.asistencia.ManejadorEnvioAsistencia;
import com.magneticsource.msource.asistencia.TomarAsistencia;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Profesor;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class CapturarActivity extends AppCompatActivity {

    private Profesor profesor;
    private ArrayList<String> dni_alumnos;
    private NfcAdapter nfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;
    private TextView txvNombreCurso;
    private TextView txvNombreGrupo;
    private TextView txvHora;
    private TextView txvAula;
    private Button btnTerminar;
    private Asistencia asistencia;
    private TomarAsistencia tomarAsistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar);

        Intent intent = getIntent();

        asistencia = (Asistencia) (intent.getParcelableExtra(Datos.ASISTENCIA));
        if(asistencia==null){
            Toast.makeText(this, R.string.error_curso, Toast.LENGTH_LONG);
            finish();
        } else {
            btnTerminar =(Button) findViewById(R.id.caa_btnTerminarCaptura);
            txvNombreCurso = (TextView) findViewById(R.id.caa_txvCurso);
            txvNombreGrupo = (TextView) findViewById(R.id.caa_txvGrupo);
            txvHora = (TextView) findViewById(R.id.caa_txvInicioFin);
            txvAula = (TextView) findViewById(R.id.caa_txvAula);

                txvHora.setText(asistencia.getHoraInicio() + " - " + asistencia.getHoraFin());
            txvNombreCurso.setText(asistencia.getNombreCurso());
            txvNombreGrupo.setText(asistencia.getNombreGrupo());
            txvAula.setText(asistencia.getNombreAula());

            Datos d  =new Datos(getBaseContext());
            profesor = Profesor.fromString(d.getString(Datos.USUARIO));
            tomarAsistencia =new TomarAsistencia(getBaseContext(), profesor, asistencia);

            loadIntentFilter();

            btnTerminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tomarAsistencia.cerrarAsistencia();
                    finish();
                }
            });
        }
    }

    private void loadIntentFilter() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(CapturarActivity.this,
                    R.string.error_adaptador_nfc,
                    Toast.LENGTH_LONG).show();
            finish();
        }

        // create an intent with tag data and deliver to this activity
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // set an intent filter for all MIME data
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefIntent.addDataType("*/*");
            mIntentFilters = new IntentFilter[]{ndefIntent};
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNFCTechLists = new String[][]{new String[]{NfcF.class.getName()}};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_capturar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

       */ return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String s="";
        // parse through all NDEF messages and their records and pick text type only
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (data != null) {
            try {
                for (int i = 0; i < data.length; i++) {
                    NdefRecord [] recs = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < recs.length; j++) {
                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {
                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200) == 0) ? new String("UTF-8") : new String("UTF-16");
                            int langCodeLen = payload[0] & 0077;

                            s= new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1,
                                            textEncoding);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }
        }
        tomarAsistencia.recibirAsistente(s);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null)
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }

}

