package co.edu.eam.Gestionpeliculasbackend.dao;

import co.edu.eam.Gestionpeliculasbackend.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface PeliculaDao extends JpaRepository<Pelicula,Long> {


}
