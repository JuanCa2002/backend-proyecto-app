package co.edu.eam.Gestionpeliculasbackend.dao;

import co.edu.eam.Gestionpeliculasbackend.domain.ImagenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagenDao extends JpaRepository<ImagenModel,Long> {
    Optional<ImagenModel> findByName(String name);
}
