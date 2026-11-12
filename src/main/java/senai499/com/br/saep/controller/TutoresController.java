
package senai499.com.br.saep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import senai499.com.br.saep.dto.CadastroTutorForm;
import senai499.com.br.saep.service.TutoresService;

@Controller
@RequestMapping("/tutores")
public class TutoresController {

    private final TutoresService tutoresService;

    // ==========================================
    // CONSTRUTOR - INJEÇÃO DE DEPENDÊNCIAS
    // ==========================================

    public TutoresController(
            TutoresService tutoresService) {

        this.tutoresService = tutoresService;
    }

    // ==========================================
    // EXIBIR FORMULÁRIO DE CADASTRO
    // ==========================================

    @GetMapping("/cadastrar")
    public String formulario(Model model) {

        model.addAttribute(
                "form",
                new CadastroTutorForm()
        );

        return "tutores/cadastro";
    }

    // ==========================================
    // PROCESSAR CADASTRO DO TUTOR
    // ==========================================

    @PostMapping("/cadastrar")
    public String cadastrar(
            @ModelAttribute("form") CadastroTutorForm form,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {

            // Cadastrar tutor
            tutoresService.cadastrar(form);

            // Mensagem de sucesso
            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Tutor cadastrado com sucesso!"
            );

            // Retornar para a listagem
            return "redirect:/tutores";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "erro",
                    e.getMessage()
            );

            return "tutores/cadastro";
        }
    }
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "tutores",
                tutoresService.listarTodos()
        );

        return "tutores/listar";
    }
}
