package com.magneticsource.msource.conexion;

import android.util.Log;

import com.magneticsource.msource.asistencia.Asistencia;
import com.magneticsource.msource.control.Datos;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ksoap2.serialization.SoapObject;

import java.sql.Time;
import java.util.LinkedList;

/**
 * Cliente del servicio web de usuario
 * @author César Calle
 *
 */
public class AsistenciaCliente extends ServicioWebCliente {
	private static String URL = HOST + "/Asistencia.php?wsdl";

	public static Asistencia getAsistencia(String dni_docente, String clave) {
		String Metodo = "getAsistencia";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);

		request.addProperty(crearPropiedad("dni_docente", dni_docente, String.class));
		request.addProperty(crearPropiedad("clave", clave, String.class));
        String s = getString(Metodo, request, URL);
        String result="";
        Asistencia asistencia = null;
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(s);
			JSONArray array = (JSONArray) obj;
            if(array.size()!=1){
                for (Object o : array) {
                    result += o.toString() + Datos.SEPARADOR1;
                }
                result = result.substring(0,result.length()-1);
                asistencia = Asistencia.fromString(result);
            }

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return asistencia;
	}

	public static boolean setAsistencia(String dni_docente, String clave_docente, String[] dni_alumnos, String token, String hora, String id_grupo) {
		String Metodo = "setAsistencia";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);
        LinkedList list = new LinkedList();
        for(String alumno :dni_alumnos){
            list.add(alumno);
        }
        String jsonAlumnos = JSONValue.toJSONString(list);

		request.addProperty(crearPropiedad("dni_docente", dni_docente, String.class));
		request.addProperty(crearPropiedad("clave_docente", clave_docente, String.class));
		request.addProperty(crearPropiedad("dni_alumnos", jsonAlumnos, String.class));
		request.addProperty(crearPropiedad("token", token, String.class));
		request.addProperty(crearPropiedad("hora", hora, String.class));
		request.addProperty(crearPropiedad("id_grupo", id_grupo, String.class));
        String s = getString(Metodo, request, URL);
		return s.equals("true");
	}
}
