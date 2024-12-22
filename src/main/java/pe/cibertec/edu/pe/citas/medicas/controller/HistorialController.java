package pe.cibertec.edu.pe.citas.medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.cibertec.edu.pe.citas.medicas.DTO.HistorialDTO;
import pe.cibertec.edu.pe.citas.medicas.interfaces.HistorialRepository;
import pe.cibertec.edu.pe.citas.medicas.interfaces.PacientesRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Historial;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;
import pe.cibertec.edu.pe.citas.medicas.service.HistorialService;
import pe.cibertec.edu.pe.citas.medicas.service.PacienteService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialRepository historialRepository;
    @Autowired
    private HistorialService historialService;
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private PacienteService pacienteService;


    @GetMapping("/listar")
    public String listarHistorial(@RequestParam(required = false) String buscar, Model model) {
        List<HistorialDTO> historial;

        if (buscar != null && !buscar.isEmpty()) {
            // Si se hace una búsqueda, llamamos al repositorio para buscar los historiales
            historial = historialRepository.buscarHistorial(buscar)
                    .stream()
                    .map(HistorialDTO::new) // Convertimos cada Historial a HistorialDTO
                    .collect(Collectors.toList());
        } else {
            // Si no hay búsqueda, traemos todos los historiales
            historial = historialRepository.findAll()
                    .stream()
                    .map(HistorialDTO::new)
                    .collect(Collectors.toList());
        }

        model.addAttribute("historial", historial);
        return "listahistorial";
    }

    @GetMapping("/editar/{id}")
    public String editarHistorial(@PathVariable Integer id, Model model) {
        Historial historial = historialService.findById(id);
        if (historial != null) {
            model.addAttribute("historial", historial);
            return "editarhistorial";
        } else {
            return "redirect:/error";
        }
    }

     @GetMapping("/detalle/{id}")
     public String detalleHistorial(@PathVariable("id") Integer idHistorial, Model model){
        try{
            Historial historial = historialService.optenerHistorialPorId(idHistorial);
            model.addAttribute("historial",historial);
            return "detalleshistorial";
        }catch (RuntimeException e){
              model.addAttribute("error",e.getMessage());
              return "redirect:/historial/listar";
        }
     }

    @PostMapping("/actualizar")
    public String actualizarHistorial(@RequestParam Integer idHistorial,
                                      @RequestParam Integer idPaciente,
                                      @RequestParam String fecha,
                                      @RequestParam String enfermedad,
                                      @RequestParam String tratamiento,
                                      @RequestParam String observaciones,
                                      RedirectAttributes redirectAttributes) {


        try {
            Historial historial = new Historial();
            historial.setIdHistorial(idHistorial);
            //
            Pacientes paciente = pacientesRepository.findById(idPaciente)
                    .orElseThrow(() -> new RuntimeException("Paciente No Encontrado"));

            historial.setPaciente(paciente);

            //
            historial.setFecha(java.sql.Date.valueOf(fecha));
            historial.setEnfermedad(enfermedad);
            historial.setTratamiento(tratamiento);
            historial.setObservaciones(observaciones);

            Historial historialActualizado = historialService.actualizarHistorial(historial);

            redirectAttributes.addFlashAttribute("mensaje", "El Historial se actualizó correctamente.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar Historial.");
        }
        return "redirect:/historial/listar";
    }


    @GetMapping("/nuevo")
    public String nuevoHistorial(Model model) {
        model.addAttribute("historial", new Historial());
        return "nuevohistorial";  // Asegúrate de que esta vista exista
    }

    @PostMapping("/guardar")
    public String guardarHistorialClinico(@RequestParam Integer idPaciente,
                                          @RequestParam String fecha,
                                          @RequestParam String enfermedad,
                                          @RequestParam String tratamiento,
                                          @RequestParam String observaciones,
                                          RedirectAttributes redirectAttributes) {

        try {
            if (idPaciente == null || fecha == null || enfermedad == null || tratamiento == null || observaciones == null) {
                throw new RuntimeException("Todos los campos Obligatorios");
            }
            //
            Optional<Pacientes> paciente = pacienteService.obtenerPacientePorId(idPaciente);
            if (paciente.isEmpty()) {
                throw new RuntimeException("Paciente encontrado");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = null;
            try {
                localDate = LocalDate.parse(fecha, formatter);

            } catch (DateTimeParseException e) {
                throw new RuntimeException("Formato incorecto");
            }
            java.sql.Date fechaSql = java.sql.Date.valueOf(localDate);
            //
            Historial nuevoHistorial = new Historial();
            nuevoHistorial.setPaciente(paciente.orElse(null));
            nuevoHistorial.setFecha(fechaSql);
            nuevoHistorial.setEnfermedad(enfermedad);
            nuevoHistorial.setTratamiento(tratamiento);
            nuevoHistorial.setObservaciones(observaciones);
            //guardar el historial
            historialService.guardarHistorial(nuevoHistorial);

            redirectAttributes.addFlashAttribute("mensaje", "Historial guardado con éxito");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrio un Error Inesperado");
        }
        return "redirect:/historial/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarHistorial(@PathVariable("id") Integer idHistorial, RedirectAttributes redirectAttributes) {

        try {
            historialService.eliminarHistorial(idHistorial);
            redirectAttributes.addAttribute("mensaje", "Historial eliminada con Exito");
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", "Error al eliminada Historial");
        }
        return "redirect:/historial/listar";
    }

    @GetMapping("/obtenerHistorialPorId")
    public ResponseEntity<HistorialDTO> optenercitaporId(@RequestParam Integer idHistorial){
        Historial historial = historialService.optenerHistorialPorId(idHistorial);
        if(historial !=null){
            return ResponseEntity.ok(new HistorialDTO(historial));

        }
        return ResponseEntity.notFound().build();
    }

}
