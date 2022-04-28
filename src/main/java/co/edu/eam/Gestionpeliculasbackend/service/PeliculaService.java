package co.edu.eam.Gestionpeliculasbackend.service;

import co.edu.eam.Gestionpeliculasbackend.domain.Pelicula;
import com.google.gson.JsonObject;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

public interface PeliculaService {

    public List<Pelicula> listarPeliculas();

    public List<Pelicula> buscarPorGenero(String genero);

    public Pelicula buscarPorId(long id);

    public void guardarPelicula(Pelicula pelicula);

    public void eliminarPelicula(long id);

    public void actualizarPelicula(long id, Pelicula pelicula);

    


}
