package co.edu.eam.Gestionpeliculasbackend.security.controlers;

import co.edu.eam.Gestionpeliculasbackend.domain.Mensaje;
import co.edu.eam.Gestionpeliculasbackend.security.domain.Rol;
import co.edu.eam.Gestionpeliculasbackend.security.domain.Usuario;
import co.edu.eam.Gestionpeliculasbackend.security.dto.JwtDto;
import co.edu.eam.Gestionpeliculasbackend.security.dto.LoginUsuario;
import co.edu.eam.Gestionpeliculasbackend.security.dto.NuevoUsuario;
import co.edu.eam.Gestionpeliculasbackend.security.enums.RolNombre;
import co.edu.eam.Gestionpeliculasbackend.security.jwt.JwtProvider;
import co.edu.eam.Gestionpeliculasbackend.security.service.RolService;
import co.edu.eam.Gestionpeliculasbackend.security.service.UsuarioService;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new Mensaje("Campos mal puestos"),HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())){
            return new ResponseEntity(new Mensaje("Ya existe un usuario con este nombre"),HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail())){
            return new ResponseEntity(new Mensaje("Ya existe un usuario con este email"),HttpStatus.BAD_REQUEST);
        }

        Usuario usuario=
                new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getNombreUsuario(),nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()),null);

        Set<Rol> roles= new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

        if(nuevoUsuario.getRoles().contains("admin")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("Creado con exito"),HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),loginUsuario.getPassword()));
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= jwtProvider.generateToken(authentication);
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        Usuario usuario=  usuarioService.getByNombreUsuarioOrEmail(loginUsuario.getNombreUsuario()).get();
        System.out.println(usuario.getNombre_imagen());
        JwtDto jwtDto= new JwtDto(jwt,userDetails.getUsername(),userDetails.getAuthorities(),usuario.getNombre_imagen());
        return new ResponseEntity(jwtDto,HttpStatus.OK);
    }

    @GetMapping("/getUser/{nombreUsuario}")
    public Usuario getUser(@PathVariable String nombreUsuario){
        Usuario usuario= usuarioService.getByNombreUsuario(nombreUsuario).get();
        return usuario;
    }

    @PutMapping("/updateUser/{nombreUsuario}")
    public ResponseEntity<?> updateUser( @Valid @RequestBody NuevoUsuario nuevoUsuario,@PathVariable String nombreUsuario, BindingResult bindingResult){
        Usuario usuario= usuarioService.getByNombreUsuario(nombreUsuario).get();
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new Mensaje("Campos mal puestos"),HttpStatus.BAD_REQUEST);
        }
        if(!usuario.getNombreUsuario().equals(nuevoUsuario.getNombreUsuario())){
            if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())){
                System.out.println(true);
                return new ResponseEntity(new Mensaje("Ya existe un usuario con este nombre"),HttpStatus.BAD_REQUEST);
            }
        }
        if(!usuario.getEmail().equals(nuevoUsuario.getEmail())){
            if(usuarioService.existsByEmail(nuevoUsuario.getEmail())){
                return new ResponseEntity(new Mensaje("Ya existe un usuario con este email"),HttpStatus.BAD_REQUEST);
            }
        }
        usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setEmail(nuevoUsuario.getEmail());
        usuario.setNombre_imagen(nuevoUsuario.getNombre_imagen());
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("Actualizo con exito"),HttpStatus.CREATED);
    }

    @PutMapping("/updateContrasena/{nombreUsuario}")
    public ResponseEntity<?> updateContrasena(@PathVariable String nombreUsuario,@RequestParam("password") String newPassword){
        Usuario usuario= usuarioService.getByNombreUsuario(nombreUsuario).get();
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("Actualizo con exito la contraseña"),HttpStatus.CREATED);
    }


}
