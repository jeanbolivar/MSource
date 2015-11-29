package com.magneticsource.msource.asistencia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.magneticsource.msource.R;
import com.magneticsource.msource.conexion.AsistenciaCliente;
import com.magneticsource.msource.conexion.LoginCliente;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.ingreso.ObtenerInformacionTask;
import com.magneticsource.msource.ui.CapturarActivity;

/**
 * Created by tony on 10/11/15.
 */
public class AsistenciaActualTask extends AsyncTask<String, Void, Asistencia> {
    private Context context;
    private ProgressDialog dialog;
    private String dni;
    private String clave;

    public AsistenciaActualTask(ProgressDialog dialog, String dni, String clave){
        this.dialog =dialog;
        this.dni =dni;
        this.clave =clave;
        context =dialog.getContext();
    }

    @Override
    protected void onPreExecute() {
        dialog.dismiss();
        dialog.setTitle(R.string.mensaje_espere);
        dialog.setMessage(context.getString(R.string.mensaje_obtener_curso));
        dialog.show();
    }

    @Override
    protected Asistencia doInBackground(String... params) {
        return AsistenciaCliente.getAsistencia(dni, clave);
    }

    @Override
    protected void onPostExecute(Asistencia asistencia) {
        super.onPostExecute(asistencia);
        if(asistencia==null){
            Toast.makeText(context, R.string.error_curso ,Toast.LENGTH_LONG).show();
            dialog.dismiss();
        } else{
            Intent i = new Intent(context, CapturarActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra(Datos.ASISTENCIA, asistencia);
            context.startActivity(i);
            dialog.dismiss();
        }

    }
}
