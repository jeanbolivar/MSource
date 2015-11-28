package com.magneticsource.msource.control;

/**
 * Created by JeanManuel on 02/11/2015.
 */
public class Persona {
        private String dni;
        private String nombres;
        private String apellidoPa;
        private String apellidoMa;
        private String imageURL;
        private String clave;

        public Persona(String dni, String nombres, String apellidoPa,
                    String apellidoMa, String imageURL, String clave) {
            super();
            this.dni = dni;
            this.nombres = nombres;
            this.apellidoPa = apellidoPa;
            this.apellidoMa = apellidoMa;
            this.imageURL = imageURL;
            this.clave = clave;
        }

        public String getDni() {
            return dni;
        }

        public String getNombres() {
            return nombres;
        }

        public String getClave() {
            return clave;
        }

        public String getApellidoPa() {
            return apellidoPa;
        }

        public String getApellidoMa() {
            return apellidoMa;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getNombreCompleto(){
            return getNombres() + " " + getApellidoPa()+ " " + getApellidoMa();
        }
}
