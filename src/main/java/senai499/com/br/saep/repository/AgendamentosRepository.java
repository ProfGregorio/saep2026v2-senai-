
package senai499.com.br.saep.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import senai499.com.br.saep.model.AgendamentosModel;
import senai499.com.br.saep.model.StatusAgendamento;

public interface AgendamentosRepository
        extends JpaRepository<AgendamentosModel, Long> {

    // HISTÓRICO DE AGENDAMENTOS DO PET
    List<AgendamentosModel>
    findByPet_IdOrderByDataHoraDesc(Long petId);

    // TOTAL DE AGENDAMENTOS DO PET
    long countByPet_Id(Long petId);

    // TOTAL POR STATUS
    long countByPet_IdAndStatus(
            Long petId,
            StatusAgendamento status
    );

    // PRÓXIMOS AGENDAMENTOS DO PET
    List<AgendamentosModel>
    findTop5ByPet_IdAndDataHoraGreaterThanEqualOrderByDataHoraAsc(
            Long petId,
            LocalDateTime dataHora
    );

    // VERIFICAR CONFLITO NO HORÁRIO DO VETERINÁRIO
    boolean existsByFuncionario_IdAndDataHoraAndStatusNot(
            Long funcionarioId,
            LocalDateTime dataHora,
            StatusAgendamento status
    );

    // VERIFICAR DUPLICIDADE DE AGENDAMENTO DO PET
    boolean existsByPet_IdAndDataHoraAndStatusNot(
            Long petId,
            LocalDateTime dataHora,
            StatusAgendamento status
    );
}
