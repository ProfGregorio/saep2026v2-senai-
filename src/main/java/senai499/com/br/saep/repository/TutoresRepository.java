
package senai499.com.br.saep.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import senai499.com.br.saep.model.TutoresModel;

public interface TutoresRepository
        extends JpaRepository<TutoresModel, Long> {

    // ==========================================
    // VERIFICAR CPF JÁ CADASTRADO
    // ==========================================

    boolean existsByCpf(String cpf);

    // ==========================================
    // VERIFICAR E-MAIL JÁ CADASTRADO
    // ==========================================

    boolean existsByEmailIgnoreCase(String email);

    // ==========================================
    // BUSCAR TUTOR PELO CPF
    // ==========================================

    Optional<TutoresModel> findByCpf(String cpf);

    // ==========================================
    // PESQUISAR TUTORES PELO NOME
    // ==========================================

    List<TutoresModel> findByNomeContainingIgnoreCase(
            String nome
    );
}
