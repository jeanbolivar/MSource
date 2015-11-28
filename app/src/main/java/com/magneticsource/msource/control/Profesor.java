package com.magneticsource.msource.control;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by tony on 02/11/15.
 */
public class Profesor extends Persona {
    Profesor(String dni, String nombres, String apellidoPa,
             String apellidoMa, String imageURL, String clave){
        super( dni,  nombres,  apellidoPa,
                 apellidoMa,  imageURL, clave);
    }

    public static Profesor fromString(String informacion){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(informacion);
            JSONArray array = (JSONArray)obj;
            if (array.size() == 6) {
                return new Profesor(array.get(0).toString(),
                        array.get(1).toString(),
                        array.get(2).toString(),
                        array.get(3).toString(),
                        array.get(4).toString(),
                        array.get(5).toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
