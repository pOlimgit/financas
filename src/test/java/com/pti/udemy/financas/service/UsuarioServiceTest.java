package com.pti.udemy.financas.service;

import com.pti.udemy.financas.exceptions.AutenticacaoException;
import com.pti.udemy.financas.exceptions.RegraNegocioException;
import com.pti.udemy.financas.model.entity.Usuario;
import com.pti.udemy.financas.model.repository.UsuarioRepository;
import com.pti.udemy.financas.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    //@MockBean
    UsuarioRepository usuarioRepositoryMockado = Mockito.mock(UsuarioRepository.class);
    UsuarioService usuarioService = new UsuarioServiceImpl(usuarioRepositoryMockado);

    public Usuario criarUsuario(){
        Usuario user = new Usuario();
        user.setEmail("email@email.com.br");
        user.setSenha("senha");
        return user;
    }

    @Test
    public void deveAutenticarUsuarioComSucesso(){
        Assertions.assertDoesNotThrow(() -> {
            //Cenario
            Usuario user = criarUsuario();
            Mockito.when(usuarioRepositoryMockado
                    .findByEmail(user.getEmail()))
                    .thenReturn(Optional.of(user));
            //Acao
            Usuario userAutenticated = usuarioService.autenticar(user.getEmail(), user.getSenha());
            System.out.println(userAutenticated);
            Assertions.assertNotNull(userAutenticated);
        });
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarEmailUsuarioInformado(){
        Assertions.assertThrows(AutenticacaoException.class, () ->{
            //Cenario
            Usuario user = criarUsuario();
            Mockito.when(usuarioRepositoryMockado.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(null));
            //Acao
            usuarioService.autenticar(user.getEmail(), user.getSenha());
        });
    }

    @Test
    public void deveLancarErroAoEcontrarEmailMasComSenhaDiferente(){
        Assertions.assertThrows(AutenticacaoException.class, () -> {
        //Cenario
        Usuario user = criarUsuario();
        Mockito.when(usuarioRepositoryMockado.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        //Acao
        usuarioService.autenticar(user.getEmail(), "password");
        });
    }

    @Test
    public void nãoDeveRetornarExcecaoPorAusênciaDeEmailDeUsuarioNoBanco() {
        Assertions.assertDoesNotThrow(() -> {
            //Cenário
            Mockito.when(usuarioRepositoryMockado.existsByEmail(Mockito.anyString())).thenReturn(false);
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
            Mockito.when(usuarioRepositoryMockado.existsByEmail(Mockito.anyString())).thenReturn(true);
            //Acao
            usuarioService.validarEmail("user@email.com");
            //Verificacao
            //Verificacao feita atavés do método assertThrows
        });
    }

}
