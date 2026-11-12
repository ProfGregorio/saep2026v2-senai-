
package senai499.com.br.saep.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
public class AgendamentosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PET QUE RECEBERÁ O ATENDIMENTO
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetsModel pet;

    // VETERINÁRIO RESPONSÁVEL
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private FuncionariosModel funcionario;

    // DATA E HORÁRIO
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    // MOTIVO DA CONSULTA
    @Column(nullable = false, length = 120)
    private String motivo;

    // OBSERVAÇÕES
    @Column(length = 500)
    private String observacao;

    // STATUS DO AGENDAMENTO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusAgendamento status =
            StatusAgendamento.PENDENTE;

    public AgendamentosModel() {
    }
}
