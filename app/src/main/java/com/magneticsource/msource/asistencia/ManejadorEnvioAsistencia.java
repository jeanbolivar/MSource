package com.magneticsource.msource.asistencia;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.magneticsource.msource.conexion.AsistenciaCliente;
import com.magneticsource.msource.conexion.Conexion;
import com.magneticsource.msource.control.Datos;

/**
 * Created by tony on 23/11/15.
 */
public class ManejadorEnvioAsistencia extends Service{

    private static final String TAG = "ManejadorEnvioAsistencia";
    private boolean termino  = false;

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        final String dni_docente = intent.getStringExtra(Datos.USUARIO);
        final String[] asistentes = intent.getStringArrayExtra("asistentes");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                while (!termino){
                    Conexion c =new Conexion(getBaseContext());
                    if(c.verificarConexionInternet()){
                        if(AsistenciaCliente.setAsistencia(dni_docente, "", asistentes,"",null)){
                            termino = true;
                        }
                    } else {
                        try {
                            Thread.sleep(1000*60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
