package co.edu.eam.Gestionpeliculasbackend.security.jwt;

import co.edu.eam.Gestionpeliculasbackend.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final static Logger logger= LoggerFactory.getLogger(JwtTokenFilter.class);

    List<String> skipUrls = Arrays.asList("/api/v1/");
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try{
          String token = getToken(req);
            System.out.println(token);
          if(token!= null && jwtProvider.validateToken(token)){
              String nombreUsuario= jwtProvider.getNombreUsuarioFromToken(token);
              UserDetails userDetails= userDetailsService.loadUserByUsername(nombreUsuario);
              UsernamePasswordAuthenticationToken auth =
                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              SecurityContextHolder.getContext().setAuthentication(auth);
          }
        }catch (Exception e){
           logger.error("Fail en el metodo doFilter");
        }
        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    private String getToken(HttpServletRequest request){
       String header= request.getHeader("Authorization");
       if(header != null && header.startsWith("Bearer")){
             return header.replace("Bearer","");
       }
       return null;
    }
}
