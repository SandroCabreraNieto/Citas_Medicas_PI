package pe.cibertec.edu.pe.citas.medicas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.edu.pe.citas.medicas.DTO.CitaDTO;
import pe.cibertec.edu.pe.citas.medicas.interfaces.CitasRepository;
import pe.cibertec.edu.pe.citas.medicas.interfaces.MedicosRepository;
import pe.cibertec.edu.pe.citas.medicas.interfaces.PacientesRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Citas;
import pe.cibertec.edu.pe.citas.medicas.models.Medicos;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CitasService {

    @Autowired
    private CitasRepository citasRepository;
    @Autowired
    private MedicosRepository medicoRepository;
    @Autowired
    private PacientesRepository pacientesRepository;

    // Obtener todas las citas
    public List<CitaDTO> obtenerCitas() {
        List<Citas> citas = citasRepository.findAll();
        // Convertir a CitaDTO
        List<CitaDTO> citasDTO = new ArrayList<>();
        for (Citas cita : citas) {
            citasDTO.add(new CitaDTO(cita));
        }
        return citasDTO;
    }

    public Citas findById(Integer id) {
        return citasRepository.findById(id).orElse(null);
    }
    // Obtener cita por ID
    public Citas obtenerCitaPorId(Integer idCita) {
        return citasRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    // Guardar una nueva cita o actualizar una existente
    // Guardar una nueva cita
    public Citas guardarCita(Citas cita) {
        // Validar paciente
        Pacientes paciente = pacientesRepository.findByDni(cita.getPaciente().getDni())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Validar médico
        Medicos medico = medicoRepository.findById(cita.getMedico().getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        // Completar información de la cita
        cita.setPaciente(paciente); // Asignar el paciente a la cita
        cita.setMedico(medico); // Asignar el médico a la cita

        // Guardar la cita
        return citasRepository.save(cita);
    }


    // Actualizar una cita existente
    public Citas actualizarCita(Citas cita) {
        // Verificar si la cita existe
        Optional<Citas> citaExistente = citasRepository.findById(cita.getIdCita());

        if (citaExistente.isEmpty()) {
            throw new RuntimeException("Cita no encontrada");
        }

        Citas citaAActualizar = citaExistente.get();

        // Si el paciente se ha cambiado, validamos y asignamos el nuevo paciente
        if (cita.getPaciente() != null) {
            Pacientes paciente = pacientesRepository.findByDni(cita.getPaciente().getDni())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            citaAActualizar.setPaciente(paciente);
        }

        // Si el médico se ha cambiado, validamos y asignamos el nuevo médico
        if (cita.getMedico() != null) {
            Medicos medico = medicoRepository.findById(cita.getMedico().getIdMedico())
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
            citaAActualizar.setMedico(medico);
        }

        // Actualizar otros campos
        citaAActualizar.setFecha(cita.getFecha());
        citaAActualizar.setMotivo(cita.getMotivo());
        citaAActualizar.setEstado(cita.getEstado());

        // Guardar la cita actualizada
        return citasRepository.save(citaAActualizar);
    }

    // Eliminar una cita
    public void eliminarCita(Integer idCita) {
        if (!citasRepository.existsById(idCita)) {
            throw new RuntimeException("Cita no encontrada");
        }
        citasRepository.deleteById(idCita);
    }


    // Obtener paciente por DNI
    public Pacientes obtenerPacientePorDni(String dni) {
        return pacientesRepository.findByDni(dni).orElse(null);
    }

    // Obtener médico por ID
    public Medicos obtenerMedicoPorId(Integer idMedico) {
        return medicoRepository.findById(idMedico).orElse(null);
    }
}
