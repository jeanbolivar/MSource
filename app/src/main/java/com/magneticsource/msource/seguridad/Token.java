package com.magneticsource.msource.seguridad;

import java.sql.Time;
import java.util.Date;

/**
 * Created by JeanManuel on 04/11/2015.
 */
public class Token {

    private Time hora;
    public Token(Time hora){
        this.hora = hora;
    }

    public String getToken()
    {
        //algoritmo de encriptacion para crear el token de acuerdo a la hora.
        String encriptacion = "";
        return encriptacion;
    }
}
