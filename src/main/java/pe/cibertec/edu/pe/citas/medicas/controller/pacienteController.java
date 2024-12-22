package pe.cibertec.edu.pe.citas.medicas.controller;

import java.util.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.cibertec.edu.pe.citas.medicas.interfaces.PacientesRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;
import pe.cibertec.edu.pe.citas.medicas.service.MedicoService;
import pe.cibertec.edu.pe.citas.medicas.service.PacienteService;


@Controller
@RequestMapping("/pacientes")
public class pacienteController {

    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;


    // Método para listar pacientes, con opción de buscar por nombre
    @GetMapping("/listar")
    public String listarPacientes(@RequestParam(required = false) String buscar, Model model) {
        List<Pacientes> pacientes;
        if (buscar != null && !buscar.isEmpty()) {
            pacientes = pacientesRepository.buscarPaciente(buscar);  // Buscar pacientes por nombre
        } else {
            pacientes = pacientesRepository.findAll();  // Listar todos los pacientes
        }
        model.addAttribute("pacientes", pacientes);
        return "listaPaciente";  // Nombre de la vista Thymeleaf
    }

    // Método para mostrar formulario de agregar paciente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoPaciente(Model model) {
        Pacientes paciente = new Pacientes();  // Crear un nuevo objeto Pacientes
        model.addAttribute("paciente", paciente);  // Añadirlo al modelo
        return "agregarHistorialPaciente";  // Nombre de la vista Thymeleaf
    }

    // Método para guardar un nuevo paciente
    @PostMapping("/guardar")
    public String guardarPaciente(@ModelAttribute Pacientes paciente, RedirectAttributes redirectAttributes) {
        Optional<Pacientes> pacienteExistente = pacienteService.buscarPorDni(paciente.getDni());

        if (pacienteExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("registroStatus", "error");
            redirectAttributes.addFlashAttribute("mensaje", "El DNI ya existe.");
            return "redirect:/pacientes/nuevo";
        }

        pacienteService.guardarPaciente(paciente);
        redirectAttributes.addFlashAttribute("registroStatus", "success");
        redirectAttributes.addFlashAttribute("mensaje", "El registro se realizó con éxito.");
        return "redirect:/pacientes/listar";
    }



    // Método para editar un paciente
    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable Integer id, Model model) {
        // Find the patient by id
        Pacientes paciente = pacientesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de paciente no válido"));

        // Add the patient to the model so that Thymeleaf can bind it to the form
        model.addAttribute("paciente", paciente);

        // Return the name of the Thymeleaf template that will render the form
        return "editarPaciente"; // Nombre de la vista Thymeleaf
    }

    // Método para actualizar un paciente
    @PostMapping("/actualizar")
    public String actualizarPaciente(@ModelAttribute Pacientes paciente, RedirectAttributes redirectAttributes) {
        // Verificar si el paciente con el mismo DNI ya existe (excepto el paciente actual)
        Optional<Pacientes> pacienteExistente = pacienteService.buscarPorDni(paciente.getDni());

        if (pacienteExistente.isPresent() && !pacienteExistente.get().getIdPaciente().equals(paciente.getIdPaciente())) {
            redirectAttributes.addFlashAttribute("registroStatus", "error");
            redirectAttributes.addFlashAttribute("mensaje", "El DNI ya existe.");
            return "redirect:/pacientes/editar/" + paciente.getIdPaciente(); // Redirigir al formulario de edición
        }

        // Guardar o actualizar el paciente
        pacientesRepository.save(paciente);

        // Mensaje de éxito
        redirectAttributes.addFlashAttribute("registroStatus", "success");
        redirectAttributes.addFlashAttribute("mensaje", "La actualización se realizó con éxito.");

        return "redirect:/pacientes/listar"; // Redirigir al listado de pacientes
    }


    // Método para eliminar un paciente
    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        pacientesRepository.deleteById(id);  // Eliminar paciente de la base de datos
        redirectAttributes.addFlashAttribute("mensaje", "Paciente eliminado exitosamente");
        return "redirect:/pacientes/listar";  // Redirigir al listado de pacientes
    }

    // Método para ver detalles de un paciente
    @GetMapping("/detalle/{id}")
    public String mostrarDetallePaciente(@PathVariable("id") Integer id, Model model) {
        Optional<Pacientes> pacienteOpt = pacienteService.obtenerPacientePorId(id);

        if (pacienteOpt.isPresent()) {
            model.addAttribute("paciente", pacienteOpt.get()); // Agrega el paciente al modelo
            return "detallepaciente"; // Nombre de la vista de detalles
        } else {
            // Manejo del error si no se encuentra el paciente
            model.addAttribute("error", "Paciente no encontrado");
            return "error"; // Si no se encuentra el paciente, redirige a una página de error
        }

    }

    //
    // Endpoint para buscar paciente por DNI
    @GetMapping("/buscarPorDni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        Optional<Pacientes> paciente = pacienteService.buscarPorDni(dni);

        if (paciente.isPresent()) {
            Pacientes p = paciente.get();
            Map<String, Object> response = new HashMap<>();
            response.put("nombre", p.getNombre());
            response.put("apellido", p.getApellido());
            response.put("dni", p.getDni());
            return ResponseEntity.ok(response); // Devuelve solo los datos necesarios
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");
        }
    }

    // Listar médicos con sus especialidades
    @GetMapping("/medicosConEspecialidades")
    public ResponseEntity<List<Map<String, String>>> listarMedicosConEspecialidades() {
        return ResponseEntity.ok(medicoService.obtenerMedicosConEspecialidades());
    }

    @GetMapping("/obtenerPacientePorDni")
    public ResponseEntity<Map<String, String>> obtenerPacientePorDni(@RequestParam String dni) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorDni(dni));
    }



}