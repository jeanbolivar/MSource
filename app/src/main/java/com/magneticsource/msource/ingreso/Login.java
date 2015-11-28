package com.magneticsource.msource.ingreso;


import android.content.Context;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.magneticsource.msource.R;
import com.magneticsource.msource.conexion.UsuarioCliente;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Validador;
import com.magneticsource.msource.conexion.Conexion;

/**
 * Created by JeanManuel on 04/11/2015.
 */
public class Login {
    private Context context;
    private ProgressDialog dialog;

    public static boolean SesionActiva(Context context) {
        Datos d = new Datos(context);
        return d.getPreferences().contains(Datos.USUARIO);
    }

    public static String TipoUsuario(Context context){
        if(SesionActiva(context)){
            Datos d = new Datos(context);
            return d.getString(Datos.TIPO_USUARIO);
        } else return null;
    }

    public Login(ProgressDialog dialog){
        this.dialog =dialog;
        this.context = dialog.getContext();
    }

    public void iniciarSesion(String dni, String clave)
    {
        if(verificarDatos(dni,clave)){
            Conexion  c =new Conexion(context);
            if(c.verificarConexionInternet()){
                IniciarSesionTask iniciar =new IniciarSesionTask(dialog, dni, clave);
                iniciar.execute();
            } else {
                c.mostrarError();
            }

        }
    }

    private boolean verificarDatos(String dni, String clave){
        if(!Validador.validarDni(dni)){
            Toast.makeText(context, R.string.error_dni, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Validador.validarClave(clave)){
            Toast.makeText(context, R.string.error_clave, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void cerrarSesion(){
        Datos d =new Datos(context);
        d.removeString(Datos.USUARIO);
        d.removeString(Datos.TIPO_USUARIO);
    }


}
