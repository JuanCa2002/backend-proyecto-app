package co.edu.eam.Gestionpeliculasbackend.service;

import co.edu.eam.Gestionpeliculasbackend.domain.ImagenModel;
import co.edu.eam.Gestionpeliculasbackend.domain.Pelicula;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenModelService{

    public ImagenModel getImage(String name);

    public ResponseEntity.BodyBuilder  uploadImage(MultipartFile file) throws IOException;

    public void deleteImage(String name);

    public void updateImage(MultipartFile file,String name);


}
