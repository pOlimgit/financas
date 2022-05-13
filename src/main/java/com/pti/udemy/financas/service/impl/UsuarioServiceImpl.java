package com.pti.udemy.financas.service.impl;

import com.pti.udemy.financas.exceptions.AutenticacaoException;
import com.pti.udemy.financas.exceptions.RegraNegocioException;
import com.pti.udemy.financas.model.entity.Usuario;
import com.pti.udemy.financas.model.repository.UsuarioRepository;
import com.pti.udemy.financas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service//Grencie esta Instância e disponibilize um container para injeção em outras classe.
public class UsuarioServiceImpl implements UsuarioService {

    //Se não for através do @Autowired deve-se implementar um construtor passando
    //uma instância de UsuarioRepository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);
        if (!user.isPresent()){
            throw new AutenticacaoException("Email não cadastrado.");
        }
        if (!user.get().getSenha().equals(senha)){
            throw new AutenticacaoException("Senha iconrreta");
        }
        return user.get();
    }

    @Override
    @Transactional
    public Usuario salvar(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email){
        boolean existe = usuarioRepository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Usuário já cadastrado");
        }
    }
}
