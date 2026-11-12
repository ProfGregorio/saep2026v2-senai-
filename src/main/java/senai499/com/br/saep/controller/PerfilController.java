package senai499.com.br.saep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.security.core.Authentication;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import senai499.com.br.saep.dto.AlterarSenhaForm;
import senai499.com.br.saep.service.UsuariosService;

@Controller
public class PerfilController {

    private final UsuariosService usuariosService;

    public PerfilController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    // ==========================================
    // EXIBIR FORMULÁRIO
    // ==========================================

    @GetMapping("/perfil/alterar-senha")
    public String exibirAlterarSenha(Model model) {

        model.addAttribute(
                "form",
                new AlterarSenhaForm()
        );

        return "admin/perfil/alterar-senha";
    }

    // ==========================================
    // PROCESSAR ALTERAÇÃO DE SENHA
    // ==========================================

    @PostMapping("/perfil/alterar-senha")
    public String alterarSenha(
            @ModelAttribute("form") AlterarSenhaForm form,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {

            String email = authentication.getName();

            usuariosService.alterarSenha(email, form);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Senha alterada com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/dashboard";
    }
}