package pe.cibertec.edu.pe.citas.medicas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.edu.pe.citas.medicas.DTO.HistorialDTO;
import pe.cibertec.edu.pe.citas.medicas.interfaces.HistorialRepository;
import pe.cibertec.edu.pe.citas.medicas.interfaces.PacientesRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Historial;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;
    @Autowired
    private PacientesRepository pacientesRepository;


    public List<HistorialDTO> optenerHistorial(){
        List<Historial> historials = historialRepository.findAll();

        List<HistorialDTO> historialDTOS = new ArrayList<>();
        for (Historial historial : historials){
            historialDTOS.add(new HistorialDTO(historial));
        }
        return historialDTOS;

    }
     public  Historial findById(Integer id){
        return historialRepository.findById(id).orElse(null);
     }

     public Historial optenerHistorialPorId(Integer idHistorial){
        return historialRepository.findById(idHistorial).orElseThrow(()-> new RuntimeException("Historial no Encontrado"));
     }

     public Historial guardarHistorial(Historial historial){

         Pacientes paciente = pacientesRepository.findByDni(historial.getPaciente().getDni())
                 .orElseThrow(() -> new RuntimeException("Paciente no Encontrado"));
         historial.setPaciente(paciente);
         return historialRepository.save(historial);
     }

     public  Historial actualizarHistorial(Historial historial){
         Optional<Historial> historialExiste = historialRepository.findById(historial.getIdHistorial());

         if (historialExiste.isEmpty()){
             throw  new RuntimeException("Cita no Encontrada");
         }
         Historial historialActualizar = historialExiste.get();

         //si el paciente a cambiado validamos y asignamos
         if(historial.getPaciente() != null){
             Pacientes paciente = pacientesRepository.findByDni(historial.getPaciente().getDni())
                     .orElseThrow(()-> new RuntimeException("Paciente no Encontrado"));
             historialActualizar.setPaciente(paciente);
         }

         //actualizar otros campos
         historialActualizar.setFecha(historial.getFecha());
         historialActualizar.setEnfermedad(historial.getEnfermedad());
         historialActualizar.setTratamiento(historial.getTratamiento());
         historialActualizar.setObservaciones(historial.getObservaciones());

         return historialRepository.save(historialActualizar);
     }

     public void eliminarHistorial(Integer idHistorial){
        if(!historialRepository.existsById(idHistorial)){
           throw new RuntimeException("Historial no encontrado");
        }
        historialRepository.deleteById(idHistorial);
     }
}
