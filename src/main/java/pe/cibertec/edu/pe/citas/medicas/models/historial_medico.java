package pe.cibertec.edu.pe.citas.medicas.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "historial_medico")
public class historial_medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;
    @Column(name = "id_doctor")
    private Integer idDoctor;
    @Column(name = "nombredoctor")
    private String nombreDoctor;
    @Column(name = "apellidodoctor")
    private String apellidoDoctor;
    @Column(name = "especilidad")
    private String especialidad;
    @Column(name = "id_paciente")
    private Integer idPaciente;
    @Column(name = "nombrepaciente")
    private String nombrePaciente;
    @Column(name = "apellidopaciente")
    private String apellidoPaciente;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @Column(name = "enfermedad")
    private String enfermedad;
    @Column(name = "tratamiento")
    private String tratamiento;
    @Column(name = "observaciones")
    private String observaciones;

    public historial_medico() {
    }

    public historial_medico(Integer idHistorial, Integer idDoctor, String nombreDoctor, String apellidoDoctor, String especialidad, Integer idPaciente, String nombrePaciente, String apellidoPaciente, Date fecha, String enfermedad, String tratamiento, String observaciones) {
        this.idHistorial = idHistorial;
        this.idDoctor = idDoctor;
        this.nombreDoctor = nombreDoctor;
        this.apellidoDoctor = apellidoDoctor;
        this.especialidad = especialidad;
        this.idPaciente = idPaciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.fecha = fecha;
        this.enfermedad = enfermedad;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getApellidoDoctor() {
        return apellidoDoctor;
    }

    public void setApellidoDoctor(String apellidoDoctor) {
        this.apellidoDoctor = apellidoDoctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
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
        return "historialclinico{" +
                "idHistorial=" + idHistorial +
                ", idDoctor=" + idDoctor +
                ", nombreDoctor='" + nombreDoctor + '\'' +
                ", apellidoDoctor='" + apellidoDoctor + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", idPaciente=" + idPaciente +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", apellidoPaciente='" + apellidoPaciente + '\'' +
                ", fecha=" + fecha +
                ", enfermedad='" + enfermedad + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
