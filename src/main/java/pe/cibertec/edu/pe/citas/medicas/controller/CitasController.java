package pe.cibertec.edu.pe.citas.medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.cibertec.edu.pe.citas.medicas.DTO.CitaDTO;
import pe.cibertec.edu.pe.citas.medicas.interfaces.MedicosRepository;
import pe.cibertec.edu.pe.citas.medicas.interfaces.PacientesRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Citas;
import pe.cibertec.edu.pe.citas.medicas.interfaces.CitasRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;
import pe.cibertec.edu.pe.citas.medicas.service.CitasService;
import pe.cibertec.edu.pe.citas.medicas.service.MedicoService;
import pe.cibertec.edu.pe.citas.medicas.service.PacienteService;
import pe.cibertec.edu.pe.citas.medicas.models.Medicos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    private CitasService citasService;
    @Autowired
    private  CitasRepository citasRepository;
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private MedicosRepository medicosRepository;

    // Método para listar todas las citas

    @GetMapping("/listar")
    public String getCitas(@RequestParam(required = false) String buscar, Model model) {
        List<CitaDTO> citas;

        if (buscar != null && !buscar.trim().isEmpty()) {
            List<Citas> citasEncontradas = citasRepository.buscarCitas(buscar);
            citas = citasEncontradas.stream()
                    .map(CitaDTO::new)  // Convertir Citas a CitaDTO
                    .collect(Collectors.toList());
        } else {
            List<Citas> todasLasCitas = citasRepository.findAll();
            citas = todasLasCitas.stream()
                    .map(CitaDTO::new)  // Convertir Citas a CitaDTO
                    .collect(Collectors.toList());
        }

        model.addAttribute("citas", citas);
        return "listarcitas";
    }



    // Método para ver los detalles de una cita
    @GetMapping("/detalle/{id}")
    public String verDetalleCita(@PathVariable("id") Integer idCita, Model model) {
        try {
            Citas cita = citasService.obtenerCitaPorId(idCita);
            model.addAttribute("cita", cita);
            return "detalleCita";  // Redirige a detalleCita.html
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/citas/listar";
        }
    }

    // Método para editar una cita
    @GetMapping("/editar/{id}")
    public String editarCita(@PathVariable Integer id, Model model) {
        Citas cita = citasService.findById(id);  // Get the Citas object
        if (cita != null) {
            model.addAttribute("cita", cita);  // Add Citas object to the model
            return "editarCitas";  // Thymeleaf template name
        } else {
            return "redirect:/error";  // Redirect to error page if no Citas is found
        }
    }

    @PostMapping("/actualizar")
    public String actualizarCita(@RequestParam Integer idCita,
                                 @RequestParam Integer idPaciente,
                                 @RequestParam Integer idMedico,
                                 @RequestParam String fecha,
                                 @RequestParam String motivo,
                                 @RequestParam String estado,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Crear un objeto Citas con los nuevos datos
            Citas cita = new Citas();
            cita.setIdCita(idCita);

            // Asignamos el paciente y el médico (en este caso, no los creamos directamente)
            Pacientes paciente = pacientesRepository.findById(idPaciente)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            Medicos medico = medicosRepository.findById(idMedico)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

            cita.setPaciente(paciente);
            cita.setMedico(medico);

            // Asignamos los otros valores
            cita.setFecha(java.sql.Date.valueOf(fecha)); // Convertir la fecha a SQL Date
            cita.setMotivo(motivo);
            cita.setEstado(estado);

            // Usar la función del servicio para actualizar la cita
            Citas citaActualizada = citasService.actualizarCita(cita);

            // Agregar mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "La cita se actualizó correctamente.");
        } catch (Exception e) {
            // En caso de error
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la cita: " + e.getMessage());
        }

        return "redirect:/citas/listar"; // Redirigir a la lista de citas
    }





    @GetMapping("/nuevo")
    public String nuevaCita(Model model) {
        model.addAttribute("cita", new Citas());
        return "nuevoCita";  // Redirige a nuevoCita.html
    }

    @PostMapping("/guardar")
    public String guardarCita(@RequestParam Integer idPaciente, @RequestParam Integer idMedico,
                              @RequestParam String fecha, @RequestParam String motivo,
                              @RequestParam String estado, RedirectAttributes redirectAttributes) {
        try {
            // Validar que los parámetros no sean nulos
            if (idPaciente == null || idMedico == null || fecha == null || motivo == null || estado == null) {
                throw new RuntimeException("Todos los campos son obligatorios.");
            }

            // Buscar el paciente por su ID
            Optional<Pacientes> paciente = pacienteService.obtenerPacientePorId(idPaciente);
            if (paciente.isEmpty()) {
                throw new RuntimeException("Paciente no encontrado.");
            }

            // Buscar el médico por su ID
            Optional<Medicos> medico = medicoService.obtenerMedicoPorId(idMedico);
            if (medico.isEmpty()) {
                throw new RuntimeException("Médico no encontrado.");
            }
            // Convertir la fecha a java.sql.Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = null;
            try {
                localDate = LocalDate.parse(fecha, formatter);  // Parseamos solo la fecha sin hora
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Formato de fecha incorrecto. El formato debe ser yyyy-MM-dd.");
            }
            java.sql.Date fechaSql = java.sql.Date.valueOf(localDate);  // Convertimos a java.sql.Date

            // Crear una nueva cita
            Citas nuevaCita = new Citas();
            nuevaCita.setPaciente(paciente.orElse(null));  // Asigna el paciente encontrado
            nuevaCita.setMedico(medico.orElse(null));      // Asigna el médico encontrado
            nuevaCita.setFecha(fechaSql);    // Asigna la fecha convertida
            nuevaCita.setMotivo(motivo);      // Asigna el motivo
            nuevaCita.setEstado(estado);      // Asigna el estado
            // Guardar la cita
            citasService.guardarCita(nuevaCita);

            redirectAttributes.addFlashAttribute("mensaje", "Cita guardada con éxito");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado: " + e.getMessage());
        }
        return "redirect:/citas/listar";
    }


    // Eliminar una cita
    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable("id") Integer idCita, RedirectAttributes redirectAttributes) {
        try {
            citasService.eliminarCita(idCita);
            redirectAttributes.addFlashAttribute("mensaje", "Cita eliminada con éxito");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la cita");
        }
        return "redirect:/citas/listar";
    }
    @GetMapping("/obtenerCitaPorId")
    public ResponseEntity<CitaDTO> obtenerCitaPorId(@RequestParam Integer idCita) {
        Citas cita = citasService.obtenerCitaPorId(idCita); // Llamada al servicio para obtener la cita por ID
        if (cita != null) {
            return ResponseEntity.ok(new CitaDTO(cita));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/obtenerMedicoPorId")
    public ResponseEntity<Medicos> obtenerMedicoPorId(@RequestParam Integer id) {
        Medicos medico = medicoService.findById(id);  // Llama a tu servicio para obtener el médico
        if (medico != null) {
            return ResponseEntity.ok(medico);
        } else {
            return ResponseEntity.notFound().build();  // Si no se encuentra el médico
        }
    }

    @GetMapping("/obtenerPacientePorId")
    public ResponseEntity<Pacientes> obtenerPacientePorId(@RequestParam Integer id) {
        Pacientes paciente = pacienteService.findById(id);  // Llama a tu servicio para obtener el paciente
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.notFound().build();  // Si no se encuentra el paciente
        }
    }
}