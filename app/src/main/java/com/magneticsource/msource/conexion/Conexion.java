package com.magneticsource.msource.conexion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.widget.Toast;

import com.magneticsource.msource.R;

public class Conexion {

	private Context context;
	private boolean wifiConexion;
	private boolean mobilConexion;

	public Conexion(Context context) {
		this.context = context;
	}

	public boolean verificarConexionInternet() {
		wifiConexion = this.verificarWifiConexion();
		mobilConexion = this.verificarMobilConexion();
		return wifiConexion || mobilConexion;
	}

	private boolean verificarWifiConexion() {
		// Create object for ConnectivityManager class which returns network
		// related info
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// If connectivity object is not null
		if (connectivity != null) {
			// Get network info - WIFI internet access
			NetworkInfo info = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (info != null) {
				// Look for whether device is currently connected to WIFI
				// network
				if (info.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean verificarMobilConexion() {
		// Create object for ConnectivityManager class which returns network
		// related info
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// If connectivity object is not null
		if (connectivity != null) {
			// Get network info - Mobile internet access
			NetworkInfo info = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			if (info != null) {
				// Look for whether device is currently connected to Mobile
				// internet
				if (info.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean verificarAdaptadorNFC(){
		NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if(nfcAdapter!=null)
            return  true;
        Toast.makeText(context, R.string.error_adaptador_nfc, Toast.LENGTH_LONG).show();
        return false;
	}

	public void mostrarError(){
        Toast.makeText(context, R.string.error_internet, Toast.LENGTH_SHORT).show();
	}
}
