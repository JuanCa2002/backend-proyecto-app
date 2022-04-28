package co.edu.eam.Gestionpeliculasbackend.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name="pelicula")
public class Pelicula implements Serializable {

    private static final long serialVersionUID= 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 2, message = "Ingrese minimo 2 caracteres")
    private String titulo;

    @NotEmpty
    private String imagen;

    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 5, message = "Ingrese minimo 5 caracteres")
    private String director;

    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 4, message = "Ingrese minimo 4 digitos")
    private int fecha_estreno;

    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 5, message = "Ingrese minimo 5 caracteres")
    private String presupuesto;

    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 10, message = "Ingrese minimo 10 caracteres")
    private String descripcion;

    @NotEmpty(message = "Este campo es requerido")
    @Size(min = 3, message = "Ingrese minimo 3 caracteres")
    private String genero;

    @NotEmpty(message = "Este campo es requerido")
    private String urlVideo;

    private String estado;

}
