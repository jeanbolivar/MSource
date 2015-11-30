package com.magneticsource.msource.control;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by tony on 02/11/15.
 */
public class Alumno extends Persona{
    Alumno(String dni, String nombres, String apellidoPa,
           String apellidoMa, String imageURL, String clave){
        super( dni,  nombres,  apellidoPa,
                 apellidoMa,  imageURL, clave);
    }

    public static Alumno fromString(String informacion){
        String[] info = informacion.split(Datos.SEPARADOR1);
        if(info.length==6){
            return new Alumno(info[0],info[1],info[2],info[3],info[4],info[5]);
        }
        return null;
    }

}
