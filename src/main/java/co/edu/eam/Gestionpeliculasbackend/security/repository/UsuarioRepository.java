package co.edu.eam.Gestionpeliculasbackend.security.repository;

import co.edu.eam.Gestionpeliculasbackend.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario,String email);
    Optional<Usuario> findByTokenPassword(String tokenPassword);

}
