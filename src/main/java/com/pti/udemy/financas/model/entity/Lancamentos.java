package com.pti.udemy.financas.model.entity;

import com.pti.udemy.financas.model.enums.StatusLancamento;
import com.pti.udemy.financas.model.enums.TipoLancamento;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "lancamentos", schema = "finances")
public class Lancamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario Usuario;

    @Override
    public String toString() {
        return "Lancamentos{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", mes=" + mes +
                ", ano=" + ano +
                ", valor=" + valor +
                ", status=" + status +
                ", tipo=" + tipo +
                ", dataCadastro=" + dataCadastro +
                ", idUsuario=" + Usuario +
                '}';
    }
}
