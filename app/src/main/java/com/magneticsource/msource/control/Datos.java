package com.magneticsource.msource.control;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tony on 06/11/15.
 */
public class Datos {
    public static final String PREFERENCES = "Setting";
    public static final String TIPO_USUARIO = "TipoDeUsuario";
    public static final String TIPO_ALUMNO = "alumno";
    public static final String TIPO_PROFESOR = "profesor";
    public static final String USUARIO = "Usuario";
    public static final String ASISTENCIA = "Asistencia";
    public static final String ASISTENTES = "Asistentes";
    public static final String CLAVE = "clave";
    public static final String ID_GRUPO = "id_grupo";
    public static String SEPARADOR1 = "█";
    private Context context;
    private SharedPreferences preferences;
    public Datos(Context context){
        this.context =context;
        preferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences(){
        return preferences;
    }

    public void putString(String clave, String dato){
        preferences.edit().putString(clave, dato).commit();
    }

    public String getString(String clave){
        return preferences.getString(clave,null);
    }

    public void removeString(String clave) {
        preferences.edit().remove(clave).apply();
    }
}
