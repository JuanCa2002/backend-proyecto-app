package co.edu.eam.Gestionpeliculasbackend.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="imagen")
public class ImagenModel {

    private static final long serialVersionUID= 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    @NotEmpty
    @Column(name="picByte",length = 65555)
    private byte[] picByte;
}
