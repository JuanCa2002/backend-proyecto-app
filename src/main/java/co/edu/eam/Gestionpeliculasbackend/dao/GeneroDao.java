package co.edu.eam.Gestionpeliculasbackend.dao;

import co.edu.eam.Gestionpeliculasbackend.domain.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroDao extends JpaRepository<Genero, Long> {
}
