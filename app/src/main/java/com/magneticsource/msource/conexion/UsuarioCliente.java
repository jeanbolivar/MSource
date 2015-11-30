package com.magneticsource.msource.conexion;

import android.util.Log;

import com.magneticsource.msource.control.Datos;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ksoap2.serialization.SoapObject;

/**
 * Cliente del servicio web de usuario
 * @author César Calle
 *
 */
public class UsuarioCliente extends ServicioWebCliente {
	private static String URL = HOST + "/Persona.php?wsdl";

	public static String getInformacion(String dni,String clave) {
		String Metodo = "getDatos";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);

		request.addProperty(crearPropiedad("dni", dni, String.class));
		request.addProperty(crearPropiedad("clave", clave, String.class));
        String s = getString(Metodo, request, URL);
        String result="";
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(s);
			JSONArray array = (JSONArray) obj;
            for(Object o : array) {
                result+=o.toString()+ Datos.SEPARADOR1;
            }
            result = result.substring(0,result.length()-1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
