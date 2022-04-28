package co.edu.eam.Gestionpeliculasbackend.security.service;

import co.edu.eam.Gestionpeliculasbackend.security.domain.Usuario;
import co.edu.eam.Gestionpeliculasbackend.security.domain.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreOrEmail) throws UsernameNotFoundException {
        Usuario usuario= usuarioService.getByNombreUsuarioOrEmail(nombreOrEmail).get();
        return UsuarioPrincipal.build(usuario);
    }
}
