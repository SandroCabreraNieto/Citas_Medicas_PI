package pe.cibertec.edu.pe.citas.medicas.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Medicos")
public class Medicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer idMedico; // ID del médico

    @Column(name = "nombre", nullable = false)
    private String nombre; // Nombre del médico

    @Column(name = "apellido", nullable = false)
    private String apellido; // Apellido del médico

    @Column(name = "especialidad", nullable = false)
    private String especialidad; // Especialidad

    @Column(name = "telefono")
    private String telefono; // Teléfono del médico

    @Column(name = "email", unique = true)
    private String email; // Email del médico

    @Column(name = "direccion")
    private String direccion; // Dirección

    @Column(name = "disponibilidad")
    private String disponibilidad; // Disponibilidad

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<Citas> citas = new ArrayList<>();;


    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<Citas> getCitas() {
        return citas;
    }

    public void setCitas(List<Citas> citas) {
        this.citas = citas;
    }

    @Override
    public String toString() {
        return "Medicos{" +
                "idMedico=" + idMedico +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", disponibilidad='" + disponibilidad + '\'' +
                ", citas=" + citas +
                '}';
    }
}