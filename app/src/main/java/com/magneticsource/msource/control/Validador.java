package com.magneticsource.msource.control;

/**
 * Created by tony on 10/11/15.
 */
public class Validador {

    public static boolean validarDni(String dni){
        return dni.length()>0&&dni.length()<16;
    }

    public static boolean validarClave(String clave){
        return clave.length()>0;
    }
}
