package pe.cibertec.edu.pe.citas.medicas.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;

import java.util.List;
import java.util.Optional;

public interface PacientesRepository extends JpaRepository<Pacientes, Integer> {
    @Query("SELECT p FROM Pacientes p WHERE p.nombre LIKE %:nombre%")
    List<Pacientes> buscarPaciente(@Param("nombre") String nombre);

    Optional<Pacientes> findByDni(String dni);
}
