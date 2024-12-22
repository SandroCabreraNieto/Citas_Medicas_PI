package pe.cibertec.edu.pe.citas.medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.edu.pe.citas.medicas.models.Usuario;
import pe.cibertec.edu.pe.citas.medicas.service.UsuarioService;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (usuarioService.validarUsuario(username, password)) {
            // Redirigir al home si las credenciales son válidas
            return "redirect:/home/listar";
        } else {
            // Mostrar mensaje de error si las credenciales son inválidas
            model.addAttribute("errorMessage", "Credenciales inválidas. Por favor, intente nuevamente.");
            return "login";
        }
    }

    @GetMapping("/registro")
    public String showRegistroUser() {
        return "registroUsuario";
    }

    @PostMapping("/registro")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            Model model) {

        Usuario newUser = new Usuario();
        newUser.setUsername(username);
        newUser.setPassword(password);

        if (usuarioService.existeUsuario(newUser.getUsername())) {
            model.addAttribute("errorMessage", "El nombre de usuario ya está en uso");
            return "registroUsuario"; // Permanecer en la página de registro si hay error
        }

        usuarioService.crearUsuario(newUser);

        // Agregar mensaje de éxito a los atributos de redirección
        redirectAttributes.addFlashAttribute("successMessage", "Usuario creado exitosamente!");

        // Redirigir al login después de un registro exitoso
        return "redirect:/api/login";
    }

    // Método para mostrar la vista de home
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
