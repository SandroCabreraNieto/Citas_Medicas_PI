package pe.cibertec.edu.pe.citas.medicas.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.edu.pe.citas.medicas.models.Citas;

import java.util.List;

public interface CitasRepository extends JpaRepository<Citas, Integer> {
    @Query("SELECT c FROM Citas c WHERE c.paciente.dni LIKE %:dni%")
    List<Citas> findByPacienteDni(@Param("dni") String dni);

    // Buscar citas por nombre del paciente o del médico
    @Query("SELECT c FROM Citas c WHERE c.paciente.nombre LIKE %:buscar% OR c.medico.nombre LIKE %:buscar%")
    List<Citas> buscarCitas(@Param("buscar") String buscar);
}
