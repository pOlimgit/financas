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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    //@MockBean
    UsuarioRepository usuarioRepositoryMockado = Mockito.mock(UsuarioRepository.class);
    @SpyBean//Similar ao Mock, porém, permite escolher quais métodos simular;
    UsuarioService usuarioService;

    public Usuario criarUsuario(){
        Usuario user = new Usuario();
        user.setId(1l);
        user.setNome("Paul");
        user.setEmail("email@email.com.br");
        user.setSenha("senha");
        return user;
    }

    @Test
    public void deveSalvarUsuarioNoBancoSemErro(){
        Assertions.assertDoesNotThrow(() -> {

        //Cenario
        Usuario user = criarUsuario();
        Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
        Mockito.when(usuarioRepositoryMockado.save(user)).thenReturn(user);
        //Acao
        Usuario savedUser = usuarioService.salvar(user);
        //Verificacao
            org.assertj.core.api.Assertions.assertThat(savedUser).isNotNull();
            org.assertj.core.api.Assertions.assertThat(savedUser.getId()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(savedUser.getEmail()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(savedUser.getSenha()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(savedUser.getNome()).isNotNull();
        });
    }

    @Test
    public void deveLancarErroAoTentarSalvarUsuario(){
        Assertions.assertThrows(RegraNegocioException.class, () -> {
           //Cenario
           Usuario user = criarUsuario();
           Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(user.getEmail());
           //Acao
            usuarioService.salvar(user);
        });
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
            Mockito.when(usuarioRepositoryMockado.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
            //Acao
            usuarioService.autenticar("email@email.com", "senha");
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
            Usuario user = criarUsuario();
            Mockito.when(usuarioRepositoryMockado.existsByEmail(user.getEmail())).thenReturn(true);
            //Acao
            usuarioService.validarEmail(user.getEmail());
            //Verificacao
            //Verificacao feita atavés do método assertThrows
        });
    }

}
