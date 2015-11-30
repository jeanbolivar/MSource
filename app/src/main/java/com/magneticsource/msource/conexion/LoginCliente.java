package com.magneticsource.msource.conexion;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.magneticsource.msource.R;
import com.magneticsource.msource.control.Alumno;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.control.Persona;
import com.magneticsource.msource.control.Profesor;
import com.magneticsource.msource.ui.AlumnoActivity;
import com.magneticsource.msource.ui.ProfesorActivity;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ksoap2.serialization.SoapObject;

/**
 * Cliente del servicio web de usuario
 * @author Emanuel Rivera
 *
 */
public class LoginCliente extends ServicioWebCliente {
	private static String URL = HOST + "/Login.php?wsdl";

	public static Boolean verificarDatos(String dni, String clave) {
		String Metodo = "login";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);

		request.addProperty(crearPropiedad("dni", dni, String.class));
		request.addProperty(crearPropiedad("clave", clave, String.class));
		String s = getString(Metodo,request,URL);
		s=s==null?"false":s;
		return s.equals("true");
	}
}
