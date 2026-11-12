
package senai499.com.br.saep.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
public class FuncionariosModel {

    // ==========================================
    // IDENTIFICADOR
    // ==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // DADOS DO FUNCIONÁRIO
    // ==========================================

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 20, unique = true)
    private String crmv;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_cadastro", nullable = false,
            updatable = false)
    private LocalDateTime dataCadastro;

    // ==========================================
    // RELACIONAMENTO COM USUÁRIOS (1:1)
    // ==========================================

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            unique = true
    )
    private UsuariosModel usuario;

    // ==========================================
    // CONSTRUTOR PADRÃO
    // ==========================================

    public FuncionariosModel() {
    }

    // ==========================================
    // DATA AUTOMÁTICA DE CADASTRO
    // ==========================================

    @PrePersist
    public void antesDeSalvar() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
    }
}
