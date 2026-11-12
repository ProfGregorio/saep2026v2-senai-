
package senai499.com.br.saep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoreController {

    // ==========================================
    // PÁGINA INICIAL - ACESSO PÚBLICO
    // ==========================================

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    // ==========================================
    // LOGIN - ACESSO PÚBLICO
    // ==========================================

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ==========================================
    // PAINEL ADMINISTRATIVO
    // Acesso exclusivo: ADMIN
    // ==========================================

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // ==========================================
    // PAINEL DO FUNCIONÁRIO
    // Acesso: ADMIN e FUNCIONARIO
    // ==========================================

    @GetMapping("/funcionario")
    public String funcionario() {
        return "admin/funcionarios/index";
    }

    // ==========================================
    // ACESSO NEGADO
    // ==========================================

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}
