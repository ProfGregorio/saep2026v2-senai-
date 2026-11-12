package senai499.com.br.saep.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import senai499.com.br.saep.dto.AlterarSenhaForm;
import senai499.com.br.saep.model.UsuariosModel;
import senai499.com.br.saep.repository.UsuariosRepository;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(
            UsuariosRepository usuariosRepository,
            PasswordEncoder passwordEncoder) {

        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================================
    // CADASTRAR USUÁRIO
    // ==========================================

    public UsuariosModel cadastrar(UsuariosModel usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return usuariosRepository.save(usuario);
    }

    // ==========================================
    // ALTERAR SENHA
    // ==========================================

    @Transactional
    public void alterarSenha(
            String email,
            AlterarSenhaForm form) {

        // 1. Localizar usuário autenticado
        UsuariosModel usuario = usuariosRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado."
                        )
                );

        // 2. Conferir senha atual
        if (form.getSenhaAtual() == null ||
                !passwordEncoder.matches(
                        form.getSenhaAtual(),
                        usuario.getSenha()
                )) {

            throw new IllegalArgumentException(
                    "A senha atual está incorreta."
            );
        }

        // 3. Validar nova senha
        if (form.getNovaSenha() == null ||
                form.getNovaSenha().length() < 8) {

            throw new IllegalArgumentException(
                    "A nova senha deve ter pelo menos 8 caracteres."
            );
        }

        // 4. Confirmar nova senha
        if (!form.getNovaSenha().equals(
                form.getConfirmarSenha())) {

            throw new IllegalArgumentException(
                    "A confirmação da senha não corresponde."
            );
        }

        // 5. Impedir reutilização da senha atual
        if (passwordEncoder.matches(
                form.getNovaSenha(),
                usuario.getSenha())) {

            throw new IllegalArgumentException(
                    "A nova senha deve ser diferente da atual."
            );
        }

        // 6. Gerar novo hash BCrypt
        usuario.setSenha(
                passwordEncoder.encode(form.getNovaSenha())
        );

        // 7. Atualizar banco de dados
        usuariosRepository.save(usuario);
    }
}