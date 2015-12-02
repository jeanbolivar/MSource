package com.magneticsource.msource.ui;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.magneticsource.msource.R;
import com.magneticsource.msource.asistencia.AsistenciaActualTask;
import com.magneticsource.msource.conexion.Conexion;
import com.magneticsource.msource.control.Alumno;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Profesor;
import com.magneticsource.msource.ingreso.Login;

import java.util.Arrays;

public class ProfesorActivity extends AppCompatActivity {

    private TextView txvProfesor;
    private Profesor profesor;
    private ImageView img;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        txvProfesor= (TextView) findViewById(R.id.pra_txvProfesor);

        Datos d =new Datos(getApplicationContext());
        profesor = Profesor.fromString(d.getString(Datos.USUARIO));

        txvProfesor.setText(profesor.getNombreCompleto());
        img = (ImageView) findViewById(R.id.pra_imvFoto);

        if (profesor.getImageURL().length()>0) {
            ImageLoadTask load = new ImageLoadTask(profesor.getImageURL(),
                    img);
            load.execute();
        }

        dialog = new ProgressDialog(ProfesorActivity.this);


        Button b = (Button) findViewById(R.id.pra_btnCapturar);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AsistenciaActualTask task = new AsistenciaActualTask(dialog, profesor.getDni(), profesor.getClave());
                task.execute();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profesor, menu);
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
            Login.cerrarSesion(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Conexion.activarNFC(this);
    }
}
