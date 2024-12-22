package pe.cibertec.edu.pe.citas.medicas.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cibertec.edu.pe.citas.medicas.interfaces.PacientesRepository;

import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;


import java.util.*;

@Service
public class PacienteService {

    @Autowired
    private PacientesRepository pacientesRepository;

    public Pacientes findById(Integer id) {
        return pacientesRepository.findById(id).orElse(null);  // Devolverá el paciente si lo encuentra o null si no
    }

    // Método para obtener todos los pacientes
    public List<Pacientes> obtenerTodos() {
        return pacientesRepository.findAll();  // Devuelve todos los pacientes
    }

    // Método para buscar pacientes por nombre
    public List<Pacientes> buscarPacientesPorNombre(String nombre) {
        return pacientesRepository.buscarPaciente(nombre);  // Utiliza el método personalizado
    }
    // Método para obtener un paciente por su DNI


    public void guardarPaciente(Pacientes paciente) {
        pacientesRepository.save(paciente);  // Guarda el paciente en la base de datos
    }
    public Optional<Pacientes> obtenerPacientePorId(Integer id) {
        return pacientesRepository.findById(id); // Retorna un Optional con el paciente
    }

    ///
    public Optional<Pacientes> buscarPorDni(String dni) {
        return pacientesRepository.findByDni(dni);
    }

    public Map<String, String> obtenerPacientePorDni(String dni) {
        List<Pacientes> pacientes = pacientesRepository.findAll();
        Map<String, String> pacienteData = new HashMap<>();

        System.out.println("Buscando paciente con DNI: " + dni);  // Verificar que el DNI recibido es correcto

        for (Pacientes paciente : pacientes) {
            System.out.println("Comprobando paciente con DNI: " + paciente.getDni());  // Verificar los DNI en la base de datos
            if (paciente.getDni().equals(dni)) {
                // Encontrado, retornamos los datos del paciente
                pacienteData.put("dni", paciente.getDni());
                pacienteData.put("nombre", paciente.getNombre());
                pacienteData.put("apellido", paciente.getApellido());
                pacienteData.put("idPaciente", String.valueOf(paciente.getIdPaciente()));
                return pacienteData;
            }
        }

        // Si no se encuentra el paciente
        pacienteData.put("error", "Paciente no encontrado");
        return pacienteData;
    }

}
