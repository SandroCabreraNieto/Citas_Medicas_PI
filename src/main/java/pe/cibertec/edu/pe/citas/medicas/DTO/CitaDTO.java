package pe.cibertec.edu.pe.citas.medicas.DTO;

import pe.cibertec.edu.pe.citas.medicas.models.Citas;

import java.sql.Date;

public class CitaDTO {
    private Integer idCita;
    private String dniPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String nombreMedico;
    private String apellidoMedico;
    private String especialidad;
    private Date fecha;
    private String motivo;
    private String estado;

    // Constructor
    public CitaDTO(Citas cita) {
        this.idCita = cita.getIdCita();
        this.dniPaciente = cita.getPaciente().getDni();
        this.nombrePaciente = cita.getPaciente().getNombre();
        this.apellidoPaciente = cita.getPaciente().getApellido();
        this.nombreMedico = cita.getMedico().getNombre();
        this.apellidoMedico = cita.getMedico().getApellido();
        this.especialidad = cita.getMedico().getEspecialidad();
        this.fecha = cita.getFecha();
        this.motivo = cita.getMotivo();
        this.estado = cita.getEstado();
    }

    // Getters y setters
    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
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

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidoMedico() {
        return apellidoMedico;
    }

    public void setApellidoMedico(String apellidoMedico) {
        this.apellidoMedico = apellidoMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

