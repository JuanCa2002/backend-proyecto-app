package co.edu.eam.Gestionpeliculasbackend;

import co.edu.eam.Gestionpeliculasbackend.dao.ImagenDao;
import co.edu.eam.Gestionpeliculasbackend.domain.ImagenModel;
import co.edu.eam.Gestionpeliculasbackend.service.ImagenModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class ControladorImagenModel implements ImagenModelService {

    @Autowired
    private ImagenDao imagenDao;


    @Override
    @GetMapping("/getImage/{name}")
    public ImagenModel getImage(@PathVariable String name)  {
        final Optional<ImagenModel> retrievedImage = imagenDao.findByName(name);
        ImagenModel img=new ImagenModel();
        img.setName(retrievedImage.get().getName());
        img.setType(retrievedImage.get().getType());
        img.setPicByte(decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }

    @Override
    @PostMapping("/image/upload")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImagenModel img = new ImagenModel();
            System.out.println(file.getOriginalFilename());
            img.setName(file.getOriginalFilename());
            img.setType(file.getContentType());
            img.setPicByte(compressBytes(file.getBytes()));
            imagenDao.save(img);


        return ResponseEntity.status(HttpStatus.OK);

    }

    @Override
    @DeleteMapping("/image/{name}")
    public void deleteImage(@PathVariable String name) {
       final Optional<ImagenModel> imagen= imagenDao.findByName(name);
        ImagenModel img=new ImagenModel();
        img.setId(imagen.get().getId());
        img.setName(imagen.get().getName());
        img.setType(imagen.get().getType());
        img.setPicByte(decompressBytes(imagen.get().getPicByte()));
       imagenDao.delete(img);
    }

    @Override
    @PutMapping("/image/{name}")
    public void updateImage(@RequestParam("File") MultipartFile file,@PathVariable String name) {
        System.out.println(file.getOriginalFilename());
        final Optional<ImagenModel> imagen= imagenDao.findByName(name);
        ImagenModel img=new ImagenModel();
        img.setId(imagen.get().getId());
        img.setName(file.getOriginalFilename());
        System.out.println(img.getName());
        img.setType(file.getContentType());
        try {
            img.setPicByte(compressBytes(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagenDao.save(img);
    }


    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}
