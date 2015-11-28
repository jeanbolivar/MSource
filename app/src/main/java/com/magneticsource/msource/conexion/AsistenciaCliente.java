package com.magneticsource.msource.conexion;

import com.magneticsource.msource.asistencia.Asistencia;

import org.ksoap2.serialization.SoapObject;

import java.sql.Time;

/**
 * Cliente del servicio web de usuario
 * @author César Calle
 *
 */
public class AsistenciaCliente extends ServicioWebCliente {
	private static String URL = HOST + "/services/usuario?wsdl";

	public static Asistencia getAsistencia(String dni_docente, String clave) {
		String Metodo = "getAsistencia";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);

		request.addProperty(crearPropiedad("dni_docente", dni_docente, String.class));
		request.addProperty(crearPropiedad("clave", clave, String.class));

		String datos = getString(Metodo, request, URL);
		return Asistencia.fromString(datos);
	}

	public static boolean setAsistencia(String dni_docente, String clave_docente, String[] dni_alumnos, String token, Time hora) {
		String Metodo = "setAsistencia";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);

		request.addProperty(crearPropiedad("dni_docente", dni_docente, String.class));
		request.addProperty(crearPropiedad("clave_docente", clave_docente, String.class));
		request.addProperty(crearPropiedad("dni_alumnos", dni_alumnos, String[].class));
		request.addProperty(crearPropiedad("token", token, String.class));
		request.addProperty(crearPropiedad("hora", hora, Time.class));

		return getBoolean(Metodo, request, URL);
	}
}
