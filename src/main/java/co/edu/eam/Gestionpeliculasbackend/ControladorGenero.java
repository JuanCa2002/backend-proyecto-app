package co.edu.eam.Gestionpeliculasbackend;

import co.edu.eam.Gestionpeliculasbackend.dao.GeneroDao;
import co.edu.eam.Gestionpeliculasbackend.dao.PeliculaDao;
import co.edu.eam.Gestionpeliculasbackend.domain.Genero;
import co.edu.eam.Gestionpeliculasbackend.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generos")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")

public class ControladorGenero implements GeneroService {

    @Autowired
    GeneroDao generoDao;


    @Override
    @GetMapping("/{id}")
    public Genero findById(@PathVariable Long id) {
        return generoDao.findById(id).orElse(null);
    }

    @Override
    @GetMapping
    public List<Genero> listAllGenders() {
        return generoDao.findAll();
    }

    @Override
    @PostMapping
    public void createGender(@RequestBody Genero genero) {
       generoDao.save(genero);
    }

    @Override
    @PutMapping("/{id}")
    public void updateGender(@PathVariable Long id,@RequestBody Genero genero) {
        Genero generoUpdate= generoDao.findById(id).orElse(null);
        generoUpdate.setNombreGenero(genero.getNombreGenero());
        generoDao.save(generoUpdate);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteGender(@PathVariable Long id) {
        Genero generoDeleted= generoDao.findById(id).get();
        if(generoDeleted!= null){
            generoDao.delete(generoDeleted);
        }
    }
}
