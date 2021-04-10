package br.com.assistecnologia.GestaoDeLogisitica.Usuario.service;

import br.com.assistecnologia.GestaoDeLogisitica.Usuario.business.UsuarioBusiness;
import br.com.assistecnologia.GestaoDeLogisitica.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioBusiness usuarioBusiness;

    @GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Usuario> listar(){

        return usuarioBusiness.listarUsuario();
    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario mostrar(@PathVariable Long id){

        return usuarioBusiness.findOne(id);
    }

    @PostMapping(path = "/")
    public Usuario criar(@RequestBody Usuario Usuario ){

        return usuarioBusiness.cadastrarUsuario(Usuario);
    }

    @PutMapping("/{id}")
    public Usuario updatePost(@PathVariable Long id, @RequestBody Usuario Usuario) {
        return usuarioBusiness.updateOne(id, Usuario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return usuarioBusiness.deletarUsuario(id);
    }





//    @Autowired
//    private UsuarioBusiness usuarioBusiness;
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public Iterable<Usuario> listarCliente(){
//
//        return usuarioBusiness.listarUsuario();
//    }
//
//    @PostMapping(path = "/")
//    public ResponseEntity<Object> cadastrarUsurio(@RequestBody Usuario usuario ){
//
//       if(!usuarioBusiness.cadastrarUsuario(usuario)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        return null;
//    }
//
//    @PutMapping(path = "/")
//    public void editarUsuario( @RequestBody Usuario usuario ){
//
//        usuarioBusiness.editarUsuario(usuario);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public @ResponseBody void deletarUsuario(@PathVariable(name = "id") long id){
//        usuarioBusiness.deletarUsuario(id);
//    }

}


