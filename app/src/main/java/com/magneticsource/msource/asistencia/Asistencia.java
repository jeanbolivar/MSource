package com.magneticsource.msource.asistencia;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.magneticsource.msource.control.Datos;

import java.io.Serializable;

/**
 * Created by tony on 23/11/15.
 */
public class Asistencia implements Parcelable {
    private String nombreCurso;
    private String nombreGrupo;
    private String idGrupo;
    private String idCurso;
    private String horaInicio;
    private String horaFin;
    private String nombreAula;

    private Asistencia(String nombreCurso, String nombreGrupo, String idGrupo, String idCurso, String horaInicio, String horaFin, String nombreAula) {
        this.nombreCurso = nombreCurso;
        this.nombreGrupo = nombreGrupo;
        this.idGrupo = idGrupo;
        this.idCurso = idCurso;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombreAula = nombreAula;
    }

    protected Asistencia(Parcel in) {
        nombreCurso = in.readString();
        nombreGrupo = in.readString();
        idGrupo = in.readString();
        idCurso = in.readString();
        horaInicio = in.readString();
        horaFin = in.readString();
        nombreAula = in.readString();
    }

    public static final Creator<Asistencia> CREATOR = new Creator<Asistencia>() {
        @Override
        public Asistencia createFromParcel(Parcel in) {
            return new Asistencia(in);
        }

        @Override
        public Asistencia[] newArray(int size) {
            return new Asistencia[size];
        }
    };

    public static Asistencia fromString(String string){
        String[] datos = string.split(Datos.SEPARADOR1);
        if(datos.length==7) {
            return new Asistencia(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5], datos[6]);
        } else {
            return null;
        }
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getNombreAula() {
        return nombreAula;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreCurso);
        dest.writeString(nombreGrupo);
        dest.writeString(idGrupo);
        dest.writeString(idCurso);
        dest.writeString(horaInicio);
        dest.writeString(horaFin);
        dest.writeString(nombreAula);
    }
}
