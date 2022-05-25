package com.pti.udemy.financas.api.controller;

import com.pti.udemy.financas.api.dto.UsuarioDto;
import com.pti.udemy.financas.api.dto.UsuarioDtoEmail;
import com.pti.udemy.financas.exceptions.AutenticacaoException;
import com.pti.udemy.financas.exceptions.RegraNegocioException;
import com.pti.udemy.financas.model.entity.Usuario;
import com.pti.udemy.financas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/test")
    public String test(){
         return "Hello Fucking World!!";
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UsuarioDtoEmail usuarioDtoEmail){
        try {
            Usuario userAuth = usuarioService.autenticar(usuarioDtoEmail.getEmail(), usuarioDtoEmail.getSenha());
            return ResponseEntity.ok(userAuth);
        }catch (AutenticacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody UsuarioDto usuarioDto){

        Usuario user = new Usuario();
        user.setNome(usuarioDto.getNome());
        user.setEmail(usuarioDto.getEmail());
        user.setSenha(usuarioDto.getSenha());

        try {
            Usuario savedUser = usuarioService.salvar(user);
            return new ResponseEntity(savedUser, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
