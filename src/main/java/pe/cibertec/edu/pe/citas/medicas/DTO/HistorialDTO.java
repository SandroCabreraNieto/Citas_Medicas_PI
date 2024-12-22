package pe.cibertec.edu.pe.citas.medicas.DTO;

import pe.cibertec.edu.pe.citas.medicas.models.Historial;

import java.sql.Date;


public class HistorialDTO {
    private Integer idHistorial;
    private String dniPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private Date fecha;
    private String enfermedad;
    private String tratamiento;
    private String observaciones;

    // Constructor
    public HistorialDTO(Historial historial) {
        this.idHistorial = historial.getIdHistorial();
        this.dniPaciente = historial.getPaciente().getDni();
        this.nombrePaciente = historial.getPaciente().getNombre();
        this.apellidoPaciente = historial.getPaciente().getApellido();
        this.fecha = historial.getFecha();
        this.enfermedad = historial.getEnfermedad();
        this.tratamiento = historial.getTratamiento();
        this.observaciones = historial.getObservaciones();
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
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
}
