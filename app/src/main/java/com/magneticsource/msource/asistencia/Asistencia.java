package com.magneticsource.msource.asistencia;


import com.magneticsource.msource.control.Datos;

/**
 * Created by tony on 23/11/15.
 */
public class Asistencia {
    private String nombreCurso;
    private String idGrupo;
    private String idCurso;
    private String horaInicio;
    private String horaFin;
    private String nombreAula;

    private Asistencia(String nombreCurso, String idGrupo, String idCurso, String horaInicio, String horaFin, String nombreAula) {
        this.nombreCurso = nombreCurso;
        this.idGrupo = idGrupo;
        this.idCurso = idCurso;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombreAula = nombreAula;
    }

    public static Asistencia fromString(String string){
        String[] datos = string.split(Datos.SEPARADOR1);
        if(datos.length==7) {
            return new Asistencia(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5]);
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
}
