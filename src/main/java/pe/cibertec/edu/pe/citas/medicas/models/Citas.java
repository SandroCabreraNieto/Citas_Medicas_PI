package pe.cibertec.edu.pe.citas.medicas.models;

import jakarta.persistence.*;

import java.sql.Date;  // Usa java.sql.Date para la fecha


@Entity
@Table(name = "Citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita; // ID de la cita

    @ManyToOne
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente", nullable = false)
    private Pacientes paciente; // Relación con el paciente

    @ManyToOne
    @JoinColumn(name = "id_medico", referencedColumnName = "id_medico", nullable = false)
    private Medicos medico; // Relación con el médico

    @Column(name = "fecha", nullable = false)
    private Date fecha; // Fecha de la cita

    @Column(name = "motivo")
    private String motivo; // Motivo de la cita

    @Column(name = "estado", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'Pendiente'")
    private String estado; // Estado de la cita

    // Getters and Setters (or @Getter and @Setter if using Lombok)


    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
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

    @Override
    public String toString() {
        return "Citas{" +
                "idCita=" + idCita +
                ", paciente=" + paciente +
                ", medico=" + medico +
                ", fecha=" + fecha +
                ", motivo='" + motivo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}