package com.pti.udemy.financas.service.impl;

import com.pti.udemy.financas.exceptions.RegraNegocioException;
import com.pti.udemy.financas.model.entity.Usuario;
import com.pti.udemy.financas.model.repository.UsuarioRepository;
import com.pti.udemy.financas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//Grencie esta Instância e disponibilize um container para injeção em outras classe.
public class UsuarioServiceImpl implements UsuarioService {

    //Se não for através do @Autowired deve-se implementar um construtor passando
    //uma instância de UsuarioRepository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email){
        boolean existe = usuarioRepository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Usuário já cadastrado");
        }
    }
}
