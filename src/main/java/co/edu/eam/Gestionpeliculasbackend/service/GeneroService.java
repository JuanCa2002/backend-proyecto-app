package co.edu.eam.Gestionpeliculasbackend.service;

import co.edu.eam.Gestionpeliculasbackend.domain.Genero;

import java.util.List;

public interface GeneroService {

    public Genero findById(Long id);

    public List<Genero> listAllGenders();

    public void createGender(Genero genero);

    public void updateGender(Long id, Genero genero);

    public void deleteGender(Long id);
}
