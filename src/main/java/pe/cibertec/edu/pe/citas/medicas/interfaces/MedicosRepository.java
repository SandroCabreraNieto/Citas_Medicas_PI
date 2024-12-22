package pe.cibertec.edu.pe.citas.medicas.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.edu.pe.citas.medicas.models.Medicos;

import java.util.List;
import java.util.Optional;

public interface MedicosRepository extends JpaRepository<Medicos, Integer> {
    // Método para buscar médicos por nombre o apellido (más completo)
    @Query("SELECT m FROM Medicos m WHERE m.nombre LIKE %:nombre%")
    List<Medicos> buscarMedico(@Param("nombre") String nombre);
    Optional<Medicos> findById(Integer idMedico);
}
