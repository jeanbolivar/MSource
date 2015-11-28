package com.magneticsource.msource.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.magneticsource.msource.R;
import com.magneticsource.msource.conexion.Conexion;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.ingreso.Login;

public class MainActivity extends AppCompatActivity {

    private Login login;
    private ProgressDialog dialog;
    private EditText edtDni;
    private EditText edtClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Conexion c = new Conexion(getBaseContext());
        /*if(!c.verificarAdaptadorNFC()){
            finish();
        } else {*/

            if (Login.SesionActiva(getBaseContext())) {
                if (Login.TipoUsuario(getBaseContext()).equals(Datos.TIPO_ALUMNO)) {
                    Intent i = new Intent(getBaseContext(), AlumnoActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getBaseContext(), ProfesorActivity.class);
                    startActivity(i);
                }
            } else {

                setContentView(R.layout.activity_main);

                dialog = new ProgressDialog(MainActivity.this);

                login = new Login(dialog);
                Button b = (Button) findViewById(R.id.maa_btnEntrar);
                edtDni = (EditText) findViewById(R.id.maa_edtUsuario);
                edtClave = (EditText) findViewById(R.id.maa_edtClave);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        String dni = edtDni.getText().toString();
                        String clave = edtClave.getText().toString();
                        login.iniciarSesion(dni, clave);
                    }

                });
            }
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
