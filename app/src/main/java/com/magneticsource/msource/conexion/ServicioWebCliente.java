package com.magneticsource.msource.conexion;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Cliente de servicio web
 * 
 * @author César Calle
 * 
 */
public class ServicioWebCliente {
	protected static String NAMESPACE = "http://services.academico.ulasalle.com";
	protected static String HOST = "http://www.nfcattack.com/www/";

	protected static String getString(String metodo, SoapObject peticion,
			String url) {
		String resTxt = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(peticion);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

		try {
			androidHttpTransport.call(metodo, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			resTxt = null;
		}
		return resTxt;
	}

	protected static Boolean getBoolean(String metodo, SoapObject peticion,
			String url) {
		Boolean resTxt = false;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(peticion);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

		try {
			androidHttpTransport.call(metodo, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = Boolean.valueOf(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			resTxt = null;
		}
		return resTxt;
	}

	protected static Integer getInteger(String metodo, SoapObject peticion,
			String url) {
		Integer resTxt = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(peticion);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

		try {
			androidHttpTransport.call(metodo, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = Integer.valueOf(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			resTxt = null;
		}
		return resTxt;
	}

	/**
	 * Crea un propertyinfo
	 * @param nombre nombre
	 * @param valor el dato a enviar
	 * @param tipo tipo de dato
	 * @return propertyinfo
	 */
	protected static PropertyInfo crearPropiedad(String nombre, Object valor,
												 Object tipo) {
		PropertyInfo p = new PropertyInfo();
		p.setName(nombre);
		p.setValue(valor);
		p.setType(tipo);
		return p;
	}
}
