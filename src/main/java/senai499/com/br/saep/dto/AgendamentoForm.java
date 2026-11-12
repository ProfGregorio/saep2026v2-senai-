
package senai499.com.br.saep.dto;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoForm {

    private Long petId;

    private Long funcionarioId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHora;

    private String motivo;

    private String observacao;
}
