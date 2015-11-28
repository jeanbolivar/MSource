package com.magneticsource.msource.asistencia;

import android.content.Context;
import android.content.Intent;

import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Profesor;

import java.util.List;

/**
 * Created by JeanManuel on 04/11/2015.
 */
public class TomarAsistencia {
    private Profesor profesor;
    private List<String> asistentes;
    private Context context;

    public TomarAsistencia(Context context, Profesor profesor){
        this.profesor = profesor;
        this.context =context;
    }
    public void iniciarAsistencia(){}
    public void recibirAsistente(String dni){}
    public void cerrarAsistencia(){}
    public void grabarAsistencia(){
        Intent intent = new Intent(context, ManejadorEnvioAsistencia.class);
        intent.putExtra(Datos.USUARIO, profesor.getDni());
        String[] asisten=new String[asistentes.size()];
        asistentes.toArray(asisten);
        intent.putExtra("Alumnos", asisten);
        context.startService(intent);
    }

}
