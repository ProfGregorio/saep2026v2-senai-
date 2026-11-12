
package senai499.com.br.saep.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import senai499.com.br.saep.dto.CadastroTutorForm;
import senai499.com.br.saep.model.TutoresModel;
import senai499.com.br.saep.repository.TutoresRepository;

@Service
public class TutoresService {

    private final TutoresRepository tutoresRepository;

    // ==========================================
    // CONSTRUTOR - INJEÇÃO DE DEPENDÊNCIA
    // ==========================================

    public TutoresService(
            TutoresRepository tutoresRepository) {

        this.tutoresRepository = tutoresRepository;
    }

    // ==========================================
    // CADASTRAR TUTOR
    // ==========================================

    @Transactional
    public void cadastrar(CadastroTutorForm form) {

        // Validar os dados recebidos
        validarDados(form);

        String cpf = somenteNumeros(form.getCpf());
        String email = texto(form.getEmail())
                .toLowerCase(Locale.ROOT);

        // Verificar CPF duplicado
        if (tutoresRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException(
                    "Este CPF já está cadastrado."
            );
        }

        // Verificar e-mail duplicado
        if (tutoresRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        // Criar tutor
        TutoresModel tutor = new TutoresModel();

        // Preencher os dados
        preencherTutor(tutor, form);

        // Salvar no banco
        tutoresRepository.save(tutor);
    }

    // ==========================================
    // LISTAR TODOS OS TUTORES
    // ==========================================

    @Transactional(readOnly = true)
    public List<TutoresModel> listarTodos() {

        return tutoresRepository.findAll();
    }

    // ==========================================
    // BUSCAR TUTOR POR ID
    // ==========================================

    @Transactional(readOnly = true)
    public TutoresModel buscarPorId(Long tutorId) {

        return tutoresRepository.findById(tutorId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Tutor não encontrado."
                        )
                );
    }

    // ==========================================
    // CARREGAR DADOS PARA EDIÇÃO
    // ==========================================

    @Transactional(readOnly = true)
    public CadastroTutorForm carregarFormulario(Long tutorId) {

        TutoresModel tutor = buscarPorId(tutorId);

        CadastroTutorForm form = new CadastroTutorForm();

        form.setNome(tutor.getNome());
        form.setCpf(tutor.getCpf());
        form.setDataNasc(tutor.getDataNasc());
        form.setEmail(tutor.getEmail());

        form.setLogradouro(tutor.getLogradouro());
        form.setNumero(tutor.getNumero());
        form.setComplemento(tutor.getComplemento());
        form.setBairro(tutor.getBairro());
        form.setCidade(tutor.getCidade());
        form.setEstado(tutor.getEstado());
        form.setCep(tutor.getCep());

        return form;
    }

    // ==========================================
    // ATUALIZAR DADOS DO TUTOR
    // ==========================================

    @Transactional
    public TutoresModel atualizar(
            Long tutorId,
            CadastroTutorForm form) {

        // Localizar tutor
        TutoresModel tutor = buscarPorId(tutorId);

        // Validar dados recebidos
        validarDados(form);

        String cpf = somenteNumeros(form.getCpf());

        String email = texto(form.getEmail())
                .toLowerCase(Locale.ROOT);

        // Verificar se o CPF pertence a outro tutor
        if (!tutor.getCpf().equals(cpf)
                && tutoresRepository.existsByCpf(cpf)) {

            throw new IllegalArgumentException(
                    "Este CPF já está cadastrado."
            );
        }

        // Verificar se o e-mail pertence a outro tutor
        if (!tutor.getEmail().equalsIgnoreCase(email)
                && tutoresRepository.existsByEmailIgnoreCase(email)) {

            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        // Atualizar dados
        preencherTutor(tutor, form);

        // Persistir alterações
        return tutoresRepository.save(tutor);
    }

    // ==========================================
    // PREENCHER DADOS DO TUTOR
    // ==========================================

    private void preencherTutor(
            TutoresModel tutor,
            CadastroTutorForm form) {

        tutor.setNome(texto(form.getNome()));
        tutor.setCpf(somenteNumeros(form.getCpf()));
        tutor.setDataNasc(form.getDataNasc());

        tutor.setEmail(
                texto(form.getEmail())
                        .toLowerCase(Locale.ROOT)
        );

        tutor.setLogradouro(texto(form.getLogradouro()));
        tutor.setNumero(texto(form.getNumero()));
        tutor.setComplemento(texto(form.getComplemento()));
        tutor.setBairro(texto(form.getBairro()));
        tutor.setCidade(texto(form.getCidade()));

        tutor.setEstado(
                texto(form.getEstado())
                        .toUpperCase(Locale.ROOT)
        );

        tutor.setCep(somenteNumeros(form.getCep()));
    }

    // ==========================================
    // VALIDAR DADOS DO FORMULÁRIO
    // ==========================================

    private void validarDados(CadastroTutorForm form) {

        String nome = texto(form.getNome());

        String email = texto(form.getEmail());

        String cpf = somenteNumeros(form.getCpf());

        String cep = somenteNumeros(form.getCep());

        if (nome.isBlank() || nome.length() > 150) {
            throw new IllegalArgumentException(
                    "Informe um nome válido."
            );
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
                || email.length() > 150) {

            throw new IllegalArgumentException(
                    "Informe um e-mail válido."
            );
        }

        if (cpf.length() != 11) {
            throw new IllegalArgumentException(
                    "O CPF deve conter 11 dígitos."
            );
        }

        if (cep.length() != 8) {
            throw new IllegalArgumentException(
                    "O CEP deve conter 8 dígitos."
            );
        }

        if (form.getDataNasc() == null
                || form.getDataNasc().isAfter(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "Informe uma data de nascimento válida."
            );
        }

        if (texto(form.getLogradouro()).isBlank()
                || texto(form.getNumero()).isBlank()
                || texto(form.getBairro()).isBlank()
                || texto(form.getCidade()).isBlank()
                || !texto(form.getEstado()).matches("[A-Za-z]{2}")) {

            throw new IllegalArgumentException(
                    "Preencha corretamente o endereço."
            );
        }

        if (texto(form.getLogradouro()).length() > 150
                || texto(form.getNumero()).length() > 10
                || texto(form.getComplemento()).length() > 100
                || texto(form.getBairro()).length() > 100
                || texto(form.getCidade()).length() > 100) {

            throw new IllegalArgumentException(
                    "Um dos campos do endereço excede o tamanho permitido."
            );
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

    private String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private String somenteNumeros(String valor) {
        return texto(valor).replaceAll("\\D", "");
    }
}
