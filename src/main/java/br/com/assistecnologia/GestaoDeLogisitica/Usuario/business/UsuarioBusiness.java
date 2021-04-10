package br.com.assistecnologia.GestaoDeLogisitica.Usuario.business;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Usuario.domain.Usuario;
import br.com.assistecnologia.GestaoDeLogisitica.Usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class UsuarioBusiness {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Iterable<Usuario> listarUsuario()
    {
        return usuarioRepository.findAll();
    }

    public Usuario cadastrarUsuario(Usuario usuario ) {
        if(!isValidEmailAddress(usuario.getEmail())) {
            throw new ResourceNotFoundException("The email is invalid!");
        }
        return usuarioRepository.save(usuario);
    }
    public Usuario findOne(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario " + id + "not found"));
    }
    public Usuario updateOne(Long id, Usuario usuarioToupdate){
        if(!isValidEmailAddress(usuarioToupdate.getEmail())) {
            throw new ResourceNotFoundException("The email is invalid!");
        }
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsername(usuarioToupdate.getUsername());
            usuario.setName(usuarioToupdate.getName());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new ResourceNotFoundException("Usuario " + id + " not found"));
    }

    public ResponseEntity<?> deletarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Usuario " + id + " not found"));
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            System.err.println(ex.getMessage());
            result = false;
        }
        return result;
    }
}
