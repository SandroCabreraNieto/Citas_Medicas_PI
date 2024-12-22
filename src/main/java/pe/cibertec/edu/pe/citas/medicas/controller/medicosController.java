package pe.cibertec.edu.pe.citas.medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.cibertec.edu.pe.citas.medicas.interfaces.MedicosRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Citas;
import pe.cibertec.edu.pe.citas.medicas.models.Medicos;
import pe.cibertec.edu.pe.citas.medicas.models.Pacientes;
import pe.cibertec.edu.pe.citas.medicas.service.MedicoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medicos")
public class medicosController {

    @Autowired
    private MedicosRepository Repo;
    @Autowired
    private MedicoService medicoService;

    @GetMapping("/listar")
    public String listarMedicos(@RequestParam(required = false) String buscar, Model model) {
        List<Medicos> medicos;
        if (buscar != null && !buscar.isEmpty()) {
            medicos = medicoService.buscarMedicosPorNombre(buscar);
        } else {
            medicos = medicoService.obtenerTodos();
        }
        model.addAttribute("medicos", medicos);
        return "listamedicos"; // Devuelve la vista para listar médicos
    }


    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoMedico(Model model) {
        model.addAttribute("medicos", new Medicos());
        return "nuevomedico"; // Devuelve la vista para crear un médico
    }


    @PostMapping("/guardar")
    public String guardarMedico(@ModelAttribute Medicos medicos, Model model) {
        medicoService.agregarMedico(medicos);
        model.addAttribute("mensaje", "Médico guardado con éxito.");
        return "redirect:/medicos/listar";
    }


    @GetMapping("/editar/{id}")
    public String editarMedico(@PathVariable Integer id, Model model) {
        Medicos medico = medicoService.obtenerMedicoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de médico no válido"));
        model.addAttribute("medico", medico);
        return "editarmedico"; // Devuelve la vista para editar el médico
    }

    @PostMapping("/actualizar")
    public String actualizarPaciente(@ModelAttribute Medicos medicos, Model model) {
        System.out.println("Medicos: " + medicos);

        // Verificar y evitar que citas huérfanas sean eliminadas accidentalmente
        if (medicos.getCitas() != null) {
            for (Citas cita : medicos.getCitas()) {
                cita.setMedico(medicos);  // Asegúrate de que las citas estén asociadas al médico
                System.out.println("Cita asociada: " + cita);
            }
        } else {
            medicos.setCitas(new ArrayList<>()); // Si no hay citas, inicializa la lista vacía
        }

        // Guardar solo los cambios del médico (sin tocar citas, a menos que se modifiquen explícitamente)
        Repo.save(medicos);

        // Redirigir a la lista de médicos después de la actualización
        return "redirect:/medicos/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMedico(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            medicoService.eliminarMedico(id);
            redirectAttributes.addFlashAttribute("mensaje", "Médico eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el médico.");
        }
        return "redirect:/medicos/listar";

    }

    @GetMapping("/detalle/{id}")
    public String verDetalleMedico(@PathVariable("id")Integer id,Model model){
        Optional<Medicos> medicoOpt = medicoService.obtenerMedicoPorId(id);

        if (medicoOpt.isPresent()) {
            model.addAttribute("medico", medicoOpt.get()); // Agrega el paciente al modelo
            return "detallmedico"; // Nombre de la vista de detalles
        } else {
            // Manejo del error si no se encuentra el paciente
            model.addAttribute("error", "Medico no encontrado");
            return "error"; // Si no se encuentra el paciente, redirige a una página de error
        }
    }

}





