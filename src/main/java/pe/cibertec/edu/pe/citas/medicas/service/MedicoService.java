package pe.cibertec.edu.pe.citas.medicas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.edu.pe.citas.medicas.interfaces.MedicosRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Medicos;


import java.util.*;

@Service
public class MedicoService {
    @Autowired
    private MedicosRepository medicosRepository;
    // Obtener todos los médicos
    public List<Medicos> obtenerTodos() {
        return medicosRepository.findAll();
    }

    public Medicos findById(Integer id) {
        return medicosRepository.findById(id).orElse(null);  // Devolverá el médico si lo encuentra o null si no
    }

    // Buscar médicos por nombre
    public List<Medicos> buscarMedicosPorNombre(String nombre) {
        return medicosRepository.buscarMedico(nombre);
    }

    // Agregar un nuevo médico
    public Medicos agregarMedico(Medicos medico) {
        return medicosRepository.save(medico);
    }

    public void guardarMedico(Medicos medico) {
        medicosRepository.save(medico);
    }


    // Obtener médico por ID
    public Optional<Medicos> obtenerMedicoPorId(Integer id) {
        return medicosRepository.findById(id);

    }

    // Eliminar un médico por ID
    public void eliminarMedico(Integer id) {
        if (medicosRepository.existsById(id)) {
            medicosRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("ID de médico no encontrado");
        }
    }

    // Método para obtener todos los médicos con sus especialidades
    public List<Map<String, String>> obtenerMedicosConEspecialidades() {
        List<Map<String, String>> medicosConEspecialidades = new ArrayList<>();
        List<Medicos> medicos = medicosRepository.findAll(); // Obtener todos los médicos

        for (Medicos medico : medicos) {
            Map<String, String> medicoData = new HashMap<>();
            medicoData.put("especialidad", medico.getEspecialidad());
            medicoData.put("nombre", medico.getNombre());
            medicoData.put("apellido",medico.getApellido());
            medicoData.put("idMedico", String.valueOf(medico.getIdMedico()));
            medicosConEspecialidades.add(medicoData);
        }

        return medicosConEspecialidades; // Devuelve la lista de médicos con sus especialidades
    }
}