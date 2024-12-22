package pe.cibertec.edu.pe.citas.medicas.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/listar")
    public String home() {
        return "home";
    }

    // Ruta para cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidar la sesión
        HttpSession session = request.getSession(false); // Evita crear una nueva sesión si no existe
        if (session != null) {
            session.invalidate(); // Invalida la sesión actual
        }
        // Redirigir al login después de cerrar sesión
        return "redirect:/api/login";
    }
}
