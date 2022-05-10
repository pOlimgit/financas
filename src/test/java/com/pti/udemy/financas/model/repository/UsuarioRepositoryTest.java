package com.pti.udemy.financas.model.repository;

import com.pti.udemy.financas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void deveVerificarExistenciaDeEmail(){
        //cenario
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario");
        usuario.setEmail("usuario2@email.com");
        usuarioRepository.save(usuario);
        //acao/execucao
        boolean result = usuarioRepository.existsByEmail("usuario2@email.com");
        //verificacao
        Assertions.assertThat(result).isTrue();
    }
    @Test
    public void deveRetornarFalsoQuandoNaoEncontrarUsuarioComEmail(){
        //cenario
        usuarioRepository.deleteAll();
        //acao
        boolean result = usuarioRepository.existsByEmail("usuario2@email.com");
        //verificacao
        Assertions.assertThat(result).isFalse();
    }

}
