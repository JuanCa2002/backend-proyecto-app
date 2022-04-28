package co.edu.eam.Gestionpeliculasbackend.security.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nombre;

    @NotNull
    @Column(unique = true)
    private String nombreUsuario;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String nombre_imagen;

    private String tokenPassword;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "usuario_rol",joinColumns = @JoinColumn(name= "usuario_id"),
    inverseJoinColumns = @JoinColumn(name="rol_id"))
    private Set<Rol> roles= new HashSet<>();

    public Usuario() {
    }

    public Usuario(String nombre, String nombreUsuario, String email, String password,String nombre_imagen) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.nombre_imagen= nombre_imagen;
    }

}
