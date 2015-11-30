package com.magneticsource.msource.ingreso;


import android.content.Context;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.magneticsource.msource.R;
import com.magneticsource.msource.conexion.UsuarioCliente;
import com.magneticsource.msource.control.Alumno;
import com.magneticsource.msource.control.Persona;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Profesor;
import com.magneticsource.msource.ui.AlumnoActivity;
import com.magneticsource.msource.ui.ProfesorActivity;

/**
 * Created by tony on 10/11/15.
 */
public class ObtenerInformacionTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog dialog;
    private String dni;
    private String clave;

    public ObtenerInformacionTask(ProgressDialog dialog, String dni, String clave){
        this.dialog =dialog;
        this.dni =dni;
        this.clave =clave;
        context =dialog.getContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle(R.string.mensaje_espere);
        dialog.setMessage(context.getString(R.string.mensaje_obtener_datos));
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        return UsuarioCliente.getInformacion(dni,clave);
    }

    @Override
    protected void onPostExecute(String informacion) {
        super.onPostExecute(informacion);
        if(informacion ==null){
            Toast.makeText(context, R.string.error_service, Toast.LENGTH_SHORT).show();
        } else {
            String[] info = informacion.split(Datos.SEPARADOR1);
            String tipo = info[5];
            informacion = "";
            for(int i=0;i<5;i++){
                informacion += info[i] + Datos.SEPARADOR1;
            }
            informacion += clave;
            Log.e("error",informacion);

            Datos datos =new Datos(context);
            datos.putString(Datos.TIPO_USUARIO, tipo);
            datos.putString(Datos.USUARIO,informacion);

            Intent i =null;
            Persona p=null;
            if(tipo.equals(Datos.TIPO_ALUMNO)){
                i=new Intent(context, AlumnoActivity.class);
                p = Alumno.fromString(informacion);
            } else if(tipo.equals(Datos.TIPO_PROFESOR)) {
                i = new Intent(context, ProfesorActivity.class);
                p = Profesor.fromString(informacion);
            }
            context.startActivity(i);
            Toast.makeText(context, context.getString(R.string.prompt_bienvenido) +" "+ p.getNombres(),Toast.LENGTH_SHORT).show();

        }
        dialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
