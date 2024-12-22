package pe.cibertec.edu.pe.citas.medicas.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Pacientes")
public class Pacientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer idPaciente; // ID del paciente

    @NotNull
    @Column(name = "dni", unique = true, nullable = false)
    private String dni; // DNI del paciente

    @Column(name = "nombre", nullable = false)
    private String nombre; // Nombre del paciente

    @Column(name = "apellido", nullable = false)
    private String apellido; // Apellido del paciente

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento; // Fecha de nacimiento

    @Column(name = "telefono")
    private String telefono; // Teléfono

    @Column(name = "email", unique = true)
    private String email; // Email

    @Column(name = "direccion")
    private String direccion; // Dirección

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Ignora las citas para evitar problemas de serialización
    private List<Citas> citas; // Listado de citas del paciente


    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public List<Citas> getCitas() {
        return citas;
    }

    public void setCitas(List<Citas> citas) {
        this.citas = citas;
    }

    @Override
    public String toString() {
        return "Pacientes{" +
                "idPaciente=" + idPaciente +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", citas=" + citas +
                '}';
    }
}