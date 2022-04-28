package co.edu.eam.Gestionpeliculasbackend;

import co.edu.eam.Gestionpeliculasbackend.dao.PeliculaDao;
import co.edu.eam.Gestionpeliculasbackend.domain.Pelicula;
import co.edu.eam.Gestionpeliculasbackend.exceptions.MovieNotFoundException;
import co.edu.eam.Gestionpeliculasbackend.service.PeliculaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")

public class ControladorPelicula implements PeliculaService {

    @Autowired
    EntityManager entityManager;
    ServletContext context;

    @Autowired
    private PeliculaDao peliculaDao;



    @Override
    @GetMapping("/peliculas")
    public List<Pelicula> listarPeliculas() {
        return peliculaDao.findAll();
    }

    @Override
    @GetMapping("/peliculas/genero/{genero}")
    public List<Pelicula> buscarPorGenero(@PathVariable String genero) {
      Query query= entityManager.createQuery("SELECT pe FROM Pelicula pe WHERE pe.genero=:genero");
      query.setParameter("genero",genero);
      return query.getResultList();
    }


    @GetMapping("/peliculas/estado/{estado}")
    public List<Pelicula> buscarPorEstado(@PathVariable String estado) {
        Query query= entityManager.createQuery("SELECT pe FROM Pelicula pe WHERE pe.estado=:estado");
        query.setParameter("estado",estado);
        return query.getResultList();
    }

    @Override
    @GetMapping("/peliculas/{id}")
    public Pelicula buscarPorId(@PathVariable long id) {
        Pelicula pelicula= peliculaDao.findById(id).orElseThrow(()-> new MovieNotFoundException("BUSCA BIEN, IMBECIL, ESO NO EXISTE!!!!!!"));
        return pelicula;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/peliculas")
    public void guardarPelicula(@RequestBody Pelicula pelicula) {
        peliculaDao.save(pelicula);

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/peliculas/{id}")
    public void eliminarPelicula(@PathVariable long id) {
        Pelicula pelicula= peliculaDao.findById(id).orElseThrow(()-> new MovieNotFoundException("BUSCA BIEN, IMBECIL, ESO NO EXISTE!!!!!!"));
        peliculaDao.delete(pelicula);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/peliculas/{id}")
    public void actualizarPelicula(@PathVariable long id, @RequestBody  Pelicula peliculaNueva) {
        Pelicula pelicula= peliculaDao.findById(id).orElseThrow(()-> new MovieNotFoundException("BUSCA BIEN, IMBECIL, ESO NO EXISTE!!!!!!"));
        pelicula.setTitulo(peliculaNueva.getTitulo());
        pelicula.setDirector(peliculaNueva.getDirector());
        pelicula.setFecha_estreno(peliculaNueva.getFecha_estreno());
        pelicula.setPresupuesto(peliculaNueva.getPresupuesto());
        pelicula.setDescripcion(peliculaNueva.getDescripcion());
        pelicula.setGenero(peliculaNueva.getGenero());
        pelicula.setUrlVideo(peliculaNueva.getUrlVideo());
        pelicula.setEstado(peliculaNueva.getEstado());
        pelicula.setImagen(peliculaNueva.getImagen());


        peliculaDao.save(pelicula);
    }

}
