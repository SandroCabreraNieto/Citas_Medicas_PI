package pe.cibertec.edu.pe.citas.medicas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.edu.pe.citas.medicas.interfaces.UsuarioRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Usuario;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarUsuario(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .map(usuario -> usuario.getPassword().equals(password))
                .orElse(false);
    }

    // Verificar si un usuario existe
    public boolean existeUsuario(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    // Crear un nuevo usuario
    public Usuario crearUsuario(Usuario newUser) {
        return usuarioRepository.save(newUser);
    }
}

