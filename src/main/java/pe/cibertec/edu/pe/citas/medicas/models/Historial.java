package pe.cibertec.edu.pe.citas.medicas.models;

import jakarta.persistence.*;

import java.sql.Date;


    @Entity
    @Table(name = "Historial")
    public class Historial {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_historial")
        private Integer idHistorial; // ID del historial

        @ManyToOne
        @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente", nullable = false)
        private Pacientes paciente; // Relación con la clase Pacientes

        @Column(name = "fecha", nullable = false)
        private Date fecha; // Fecha del historial

        @Column(name = "enfermedad")
        private String enfermedad; // Enfermedad del paciente

        @Column(name = "tratamiento")
        private String tratamiento; // Tratamiento para la enfermedad

        @Column(name = "observaciones")
        private String observaciones; // Observaciones adicionales


        public Integer getIdHistorial() {
            return idHistorial;
        }

        public void setIdHistorial(Integer idHistorial) {
            this.idHistorial = idHistorial;
        }

        public Pacientes getPaciente() {
            return paciente;
        }

        public void setPaciente(Pacientes paciente) {
            this.paciente = paciente;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        public String getEnfermedad() {
            return enfermedad;
        }

        public void setEnfermedad(String enfermedad) {
            this.enfermedad = enfermedad;
        }

        public String getTratamiento() {
            return tratamiento;
        }

        public void setTratamiento(String tratamiento) {
            this.tratamiento = tratamiento;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }

        @Override
        public String toString() {
            return "Historial{" +
                    "idHistorial=" + idHistorial +
                    ", paciente=" + paciente +
                    ", fecha=" + fecha +
                    ", enfermedad='" + enfermedad + '\'' +
                    ", tratamiento='" + tratamiento + '\'' +
                    ", observaciones='" + observaciones + '\'' +
                    '}';
        }
    }
