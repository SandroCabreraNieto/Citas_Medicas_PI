package pe.cibertec.edu.pe.citas.medicas.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.edu.pe.citas.medicas.models.Historial;


import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    @Query("SELECT c FROM Citas c WHERE c.paciente.dni LIKE %:dni%")
    List<Historial> findByPacienteDni(@Param("dni") String dni);
    // Buscar citas por nombre del paciente o del médico
    @Query("SELECT c FROM Historial c WHERE c.paciente.nombre LIKE %:buscar% ")
    List<Historial> buscarHistorial(@Param("buscar") String buscar);
}
