package com.pti.udemy.financas.model.repository;

import com.pti.udemy.financas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest//Cria uma instância no H2 e após o teste dá rollback, inclusive em @Transactions(Salvamentos e Commits);
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager testEntityManager;//Possui instância de UsuarioRepository, ideal para transações no DB.

    public static Usuario criarUsuario(){
        Usuario user = new Usuario();
        user.setNome("Paulo");
        user.setEmail("email@email.com");
        user.setSenha("12345");
        return user;
    }

    //Testes de Validação de E-mail

    @Test
    public void deveVerificarExistenciaDeEmail(){
        //cenario
                testEntityManager.persist(criarUsuario());
        //acao/execucao
        boolean result = usuarioRepository.existsByEmail("email@email.com");
        //verificacao
        Assertions.assertThat(result).isTrue();
    }
    @Test
    public void deveRetornarFalsoQuandoNaoEncontrarUsuarioComEmail(){
        //cenario
        //Banco limpo pela annotation @DataJpaTest
        //acao
        boolean result = usuarioRepository.existsByEmail("email@email.com");
        //verificacao
        Assertions.assertThat(result).isFalse();
    }

    //Testes de Persistência de Usuário

    @Test
    public void devePersistirUsuarioNaBaseDeDados(){
        //Cenario
        //Já foi preparado com a passagem do método criarUsuario() como argumento na Acao;
        //Acao
        Usuario savedUser = usuarioRepository.save(criarUsuario());
        //Verificacao
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    //Testes de Autenticacao de Usuario

    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioInexistenteNaBasePorEmail(){
        //Cenario
        //Base Limpa
        //Acao - Verificacao
        Assertions.assertThat(usuarioRepository.findByEmail("email@email.com").isPresent()).isFalse();
    }

}
