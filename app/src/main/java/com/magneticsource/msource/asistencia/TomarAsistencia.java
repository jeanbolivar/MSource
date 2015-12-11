package com.magneticsource.msource.asistencia;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Profesor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeanManuel on 04/11/2015.
 */
public class TomarAsistencia {
    private Profesor profesor;
    private Asistencia asistencia;
    private List<String> asistentes;
    private Context context;

    public TomarAsistencia(Context context, Profesor profesor, Asistencia asistencia){
        this.profesor = profesor;
        this.context =context;
        this.asistentes =new ArrayList<String>();
        this.asistencia = asistencia;
    }

    public void recibirAsistente(String s){
        Toast.makeText(context, s.split(Datos.SEPARADOR1)[1], Toast.LENGTH_LONG).show();
        if(!tieneRepetido(s))
            asistentes.add(s);
    }

    public boolean tieneRepetido(String alumno){
        for(String s : asistentes)
            if(alumno.equals(s))
                return true;
        return false;
    }

    public void cerrarAsistencia(){
           grabarAsistencia();
    }

    private void grabarAsistencia(){
        Intent i = new Intent(context, ManejadorEnvioAsistencia.class);
        i.putExtra(Datos.USUARIO, profesor.getDni());
        i.putExtra(Datos.CLAVE, profesor.getClave());
        i.putExtra(Datos.ID_GRUPO, asistencia.getIdGrupo());

        if(asistentes.size()>0) {
            String[] asis = new String[asistentes.size()];
            for (int j = 0; j < asistentes.size(); j++) {
                asis[j] = asistentes.get(j);
            }
            i.putExtra(Datos.ASISTENTES, asis);
        }
        context.startService(i);
    }

}
