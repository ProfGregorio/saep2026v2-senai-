
package senai499.com.br.saep.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class PetsModel {

    // ==========================================
    // IDENTIFICADOR
    // ==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // DADOS DO PET
    // ==========================================

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String especie;

    @Column(length = 100)
    private String raca;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    // ==========================================
    // SITUAÇÃO DO PET
    // ==========================================

    @Column(nullable = false)
    private boolean vivo = true;

    @Column(name = "data_falecimento")
    private LocalDate dataFalecimento;

    // ==========================================
    // RELACIONAMENTO COM TUTORES (N:1)
    // ==========================================

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "tutor_id",
            nullable = false
    )
    private TutoresModel tutor;

    // ==========================================
    // CONSTRUTOR PADRÃO
    // ==========================================

    public PetsModel() {
    }
}
