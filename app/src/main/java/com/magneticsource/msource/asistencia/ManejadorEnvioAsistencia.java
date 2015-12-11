package com.magneticsource.msource.asistencia;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.magneticsource.msource.R;
import com.magneticsource.msource.conexion.AsistenciaCliente;
import com.magneticsource.msource.conexion.Conexion;
import com.magneticsource.msource.control.Datos;
import com.magneticsource.msource.seguridad.Encryptador;
import com.magneticsource.msource.seguridad.Token;
import com.magneticsource.msource.ui.CapturarActivity;
import com.magneticsource.msource.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tony on 23/11/15.
 */
public class ManejadorEnvioAsistencia extends Service{

    private boolean termino  = false;
    private String dni_docente;
    private String id_grupo;
    private String clave;
    private String[] alumnos_dni;
    private String[] alumnos_nombres;
    private String hora;
    private Token token;

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dni_docente = intent.getStringExtra(Datos.USUARIO);
        clave = intent.getStringExtra("clave");
        id_grupo = intent.getStringExtra("id_grupo");

        String[] asistentes = intent.getStringArrayExtra(Datos.ASISTENTES);
        if(asistentes==null){
            asistentes = alumnos_dni = alumnos_nombres = new String[0];
        } else {
            alumnos_dni = new String[asistentes.length];
            alumnos_nombres = new String[asistentes.length];
            for (int i = 0; i < alumnos_dni.length; i++) {
                String[] alumno = asistentes[i].split(Datos.SEPARADOR1);
                alumnos_dni[i] = alumno[0];
                alumnos_nombres[i] = alumno[1];
            }
        }

        hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        token =new Token(hora);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                while (!termino){
                    Conexion c =new Conexion(getBaseContext());
                    if(c.verificarConexionInternet()){
                            if(AsistenciaCliente.setAsistencia(dni_docente, clave, alumnos_dni, token.getToken(),hora,id_grupo)){
                            int cantidad = alumnos_dni.length;
                            String cantidadAlumnos = cantidad+" alumno"+ (cantidad==1?"":"");
                            String ticker= getString(R.string.mensaje_asistenciaTomada) + " para "+ cantidadAlumnos;
                            String subtext = "Se tomó la asistencia a "+cantidadAlumnos;

                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 1, intent, 0);
                            Notification.Builder builder = new Notification.Builder(getBaseContext());

                            if(alumnos_nombres.length>0) {
                                String detalle = "";
                                for (String nombre :
                                        alumnos_nombres) {
                                    detalle += nombre + "\n";
                                }
                                detalle = detalle.substring(0, detalle.length() - 1);
                                builder.setStyle(new Notification.BigTextStyle()
                                        .bigText(detalle));
                            }
                            builder.setAutoCancel(true);
                            builder.setTicker(ticker);
                            builder.setContentTitle(getString(R.string.app_name));
                            builder.setContentText(getString(R.string.mensaje_asistenciaTomada));
                            builder.setSmallIcon(R.drawable.share);
                            builder.setContentIntent(pendingIntent);
                            builder.setOngoing(false);
                            builder.setSubText(subtext);   //API level 16
                            builder.setNumber(100);
                            builder.build();

                            Notification myNotication = builder.getNotification();
                            manager.notify(11, myNotication);
                        }
                        else {
                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 1, intent, 0);
                            Notification.Builder builder = new Notification.Builder(getBaseContext());

                            builder.setAutoCancel(true);
                            builder.setTicker("No se pudo guardar");
                            builder.setContentTitle(getString(R.string.app_name));
                            builder.setContentText("Algo paso con el servicio");
                            builder.setSmallIcon(R.drawable.share);
                            builder.setContentIntent(pendingIntent);
                            builder.setOngoing(false);
                            builder.setNumber(100);
                            builder.build();

                            Notification myNotication = builder.getNotification();
                            manager.notify(11, myNotication);
                        } termino = true;
                    } else {
                        try {
                            Thread.sleep(1000*60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
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
