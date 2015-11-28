package com.magneticsource.msource.conexion;

import org.ksoap2.serialization.SoapObject;

/**
 * Cliente del servicio web de usuario
 * @author Emanuel Rivera
 *
 */
public class LoginCliente extends ServicioWebCliente {
	private static String URL = HOST + "/Login.php?wsdl";

	public static Boolean verificarDatos(String dni, String clave) {
		String Metodo = "sesionValida";
		SoapObject request = new SoapObject(NAMESPACE, Metodo);

		request.addProperty(crearPropiedad("dni", dni, String.class));
		request.addProperty(crearPropiedad("clave", clave, String.class));

		return getBoolean(Metodo, request, URL);
	}
}
