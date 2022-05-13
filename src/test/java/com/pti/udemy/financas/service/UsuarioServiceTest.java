package com.pti.udemy.financas.service;

import com.pti.udemy.financas.exceptions.RegraNegocioException;
import com.pti.udemy.financas.model.entity.Usuario;
import com.pti.udemy.financas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void nãoDeveRetornarExcecaoPorAusênciaDeEmailDeUsuarioNoBanco() {
        Assertions.assertDoesNotThrow(() -> {
            //Cenário
            usuarioRepository.deleteAll();
            //Acão
            usuarioService.validarEmail("user@email.com");
            //Verificacao};
            //Verificação promovida pelo método assertDoesNotThrow
        });

    }
    @Test
    public void retornarExceptionAoConfirmarExistenciaDeEmailDeUsuarioNoBanco(){
        Assertions.assertThrows(RegraNegocioException.class, () -> {
            //Cenario
            Usuario user = new Usuario();
            user.setEmail("lombok@email.com");
            usuarioRepository.save(user);
            //Acao
            usuarioService.validarEmail(user.getEmail());
            //Verificacao
            //Verificacao feita atavés do método assertThrows
        });
    }

}
