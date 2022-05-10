package com.pti.udemy.financas.service;

import com.pti.udemy.financas.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvar(Usuario usuario);

    void validarEmail(String email);

}
